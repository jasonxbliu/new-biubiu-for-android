package com.jaf.jcore;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jarrah 
 * 
 * 指定view id ,并且绑定, 只能用于当前声明该变量的class 
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindView {

	public static final int NO_ID = -1;
	public static final String NO_CLICK = "_no_click";

	/**
	 * define view click method name it must be the format as layout resource
	 * attribute " android:onClick="" "
	 * 
	 * @return
	 */
	String onClick() default NO_CLICK;

	/**
	 * the view id
	 * 
	 * @return
	 */
	int id() default NO_ID;
}
