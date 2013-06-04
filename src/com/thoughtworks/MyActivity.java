package com.thoughtworks;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyActivity extends Activity {

    private Button switchBtn;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void switchAP(View view) {
        try {
            setAP();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void setAP() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        Method method2 = wifi.getClass().getMethod("getWifiApState");
        int state = (Integer) method2.invoke(wifi);

        WifiConfiguration apConfig = new WifiConfiguration();
        //配置热点的名称
        apConfig.SSID = "bowen";
        apConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        apConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        apConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        //配置热点的密码
        apConfig.preSharedKey="huangbowen";

        //通过反射调用设置热点
        Method method = wifi.getClass().getMethod(
                "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
        //返回热点打开状态

        boolean enabled = state == 13 ? false : true;

        method.invoke(wifi, apConfig, enabled);
    }

}
