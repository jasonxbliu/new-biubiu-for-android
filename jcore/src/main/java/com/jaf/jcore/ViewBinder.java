package com.jaf.jcore;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ViewBinder<T extends Bindable<?>> {

	private static final boolean DEBUG = true;

	private T mInjectable;

	public ViewBinder(T injectable) {
		mInjectable = injectable;
	}

	public Field[] getViewBindFields() {

		if (getClassOwner() == null) {
			throw new IllegalAccessError("View owner did not set.");
		}
		Field[] fields = getClassOwner().getClass().getDeclaredFields();
		
		java.util.List<Field> flist = new ArrayList<Field>();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field.isAnnotationPresent(BindView.class)) {
				flist.add(field);
			}
		}
		Field[] ret = new Field[flist.size()];
		return flist.toArray(ret);
	}

	/**
	 * 
	 * @param v
	 * @param methodName
	 */
	protected void bindClickEvent(final View v, final String methodName) {
		v.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					Method method = getClassOwner().getClass().getMethod(
							methodName, new Class[] { View.class });
					method.invoke(getClassOwner(), v);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		});
	}

	protected Object getClassOwner() {
		return mInjectable.getClassOwner();
	}

	public void bindClicks() {
		for (Field field : getViewBindFields()) {
			String methodName = field.getAnnotation(BindView.class).onClick();
			int id = field.getAnnotation(BindView.class).id();
			if (methodName != BindView.NO_CLICK && id != BindView.NO_ID) {
				if (DEBUG) {
					Log.e("binded", field.getName());
				}
				bindClickEvent(mInjectable.id(id), methodName);
			}
		}
	}

	public void initViews() {
//		Util.e("declared bindField length", getViewBindFields().length);
		for (Field f : getViewBindFields()) {
			int id = f.getAnnotation(BindView.class).id();
			if (id != BindView.NO_ID) {
				f.setAccessible(true);
				try {
					f.set(getClassOwner(), mInjectable.id(id));
				} catch (IllegalAccessException e) {
					log(f, e);
				} catch (IllegalArgumentException e) {
					log(f, e);
				}
			}
		}
	}

	private void log(Field f, Exception e) {
		if (DEBUG) {
			Log.w("bindFail:",
					e.toString() + "@" + getClassOwner() + "$" + f.getName());
		}
	}

}
