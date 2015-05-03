package com.jaf.biubiu;

import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.jaf.jcore.Application;
import com.jaf.jcore.BaseActionBarActivity;

/**
 * Created by jarrahwu on 15/4/18.
 */
public class LocationTest extends BaseActionBarActivity {
    private PoiNearbySearchOption op;
    private PoiSearch sug;

    @Override
	protected int onLoadViewResource() {
		return R.layout.loc_test;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(Application.getInstance().getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
	protected void onViewDidLoad(Bundle savedInstanceState) {

        LocationManager.getInstance().requestLocation(
                new LocationManager.JLsn() {
                    @Override
                    public void onResult(double latitude, double longitude,
                                         BDLocation location) {
                        super.onResult(latitude, longitude, location);
                        id(R.id.locText, TextView.class).setText(
                                String.format("lat:%f\n lon:%f\n city%s",
                                        location.getLatitude(),
                                        location.getLongitude(),
                                        location.getCity()));
                    }
                });


//        PoiSearch ps = PoiSearch.newInstance();
//        PoiBoundSearchOption bound = new PoiBoundSearchOption();
//        bound.keyword("");
//        bound.pageCapacity(10);
//        bound.pageNum(1);
//        ps.searchInBound(bound);
//        ps.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
//            @Override
//            public void onGetPoiResult(PoiResult poiResult) {
//                for (PoiInfo info : poiResult.getAllPoi()) {
//                    L.dbg("poi info >" + info.name);
//                }
//            }
//
//            @Override
//            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
//
//            }
//        });

	}
}
