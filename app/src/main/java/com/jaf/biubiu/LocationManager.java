package com.jaf.biubiu;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.jaf.jcore.Application;

/**
 * Created by jarrah on 2015/4/14.
 */
public class LocationManager {

    public static LocationManager sLocationManager;

    LocationClient mLocationClient;

    public LocationManager() {
        SDKInitializer.initialize(Application.getInstance().getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);                                //是否打开GPS
        option.setCoorType("bd09ll");                           //设置返回值的坐标类型。
        option.setProdName("LocationDemo");                     //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(1000);
        mLocationClient = new LocationClient(Application.getInstance().getApplicationContext());
        mLocationClient.setLocOption(option);
    }

    public synchronized static LocationManager getInstance() {
        if (sLocationManager == null) {
            sLocationManager = new LocationManager();
        }
        return sLocationManager;
    }

    public void requestLocation(JLsn l) {
        mLocationClient.registerLocationListener(l);
        mLocationClient.start();
        mLocationClient.requestLocation();
    }


    public static class JLsn implements  BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation == null) {
                L.dbg("baidu sdk no location info ");
                onNoResult(bdLocation);
                return;
            }
            L.dbg("baidu sdk addr:%s city:%s", bdLocation.getAddrStr(), bdLocation.getCity());
            onResult(bdLocation.getLatitude(), bdLocation.getLongitude(), bdLocation);
            Application.getInstance().getAppExtraInfo().lat = bdLocation.getLatitude();
            Application.getInstance().getAppExtraInfo().lon = bdLocation.getLongitude();
            Application.getInstance().getAppExtraInfo().addrStr = bdLocation.getAddrStr();
            LocationManager.getInstance().mLocationClient.stop();
            LocationManager.getInstance().mLocationClient.unRegisterLocationListener(this);
        }

        public void onNoResult(BDLocation bdLocation) {
        }

        public void onResult(double latitude, double longitude, BDLocation location) {

        }
    }


}
