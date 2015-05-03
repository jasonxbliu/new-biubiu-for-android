package com.jaf.biubiu;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jaf.jcore.AdapterWrapper;
import com.jaf.jcore.ArrayAdapterCompat;
import com.jaf.jcore.BaseActionBarActivity;
import com.jaf.jcore.BindView;


//public class ActivityTest extends BaseActionBarActivity {

//    @BindView(id = R.id.testList)
//    ListView mListView;
//
//    FragmentMe.Adapter mAdapter;
//
//    @Override
//    protected int onLoadViewResource() {
//        return R.layout.activity_test;
//    }
//
//    @Override
//    protected void onViewDidLoad(Bundle savedInstanceState) {
//        mAdapter = new FragmentMe.Adapter(this);
//        mListView.setAdapter(mAdapter);
//
//        for (int i = 0; i < 100; i++) {
//            String item = "I M A ITEM AT :" + i;
//            mAdapter.add(item);
//        }
//    }
//
//    public static class Adapter extends AdapterWrapper<String, TextView> {
//
//        public Adapter(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected void onBindView(final int position, final String item, TextView view) {
//            view.setText(item);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    remove(getItem(position));
//                }
//            });
//        }
//
//        @Override
//        public TextView newView(int position, LayoutInflater lf, View convertView, ViewGroup parent) {
//            return new TextView(getContext());
//        }
//    }
//}
