package com.thoughtworks;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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

        Boolean state = getApState(wifi);

        WifiConfiguration apConfig = getApConfiguration();

        Method method = wifi.getClass().getMethod(
                "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);

        boolean enabled = !state;

        if (enabled) {
            wifi.setWifiEnabled(!enabled);
            method.invoke(wifi, apConfig, enabled);
        } else {
            method.invoke(wifi, apConfig, enabled);
            wifi.setWifiEnabled(!enabled);
        }


    }

    private WifiConfiguration getApConfiguration() {
        WifiConfiguration apConfig = new WifiConfiguration();
        //配置热点的名称
        apConfig.SSID = "yourId";
        apConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        apConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        apConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        //配置热点的密码
        apConfig.preSharedKey = "yourPassword";
        return apConfig;
    }

    private Boolean getApState(WifiManager wifi) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = wifi.getClass().getMethod("isWifiApEnabled");
        return (Boolean) method.invoke(wifi);
    }

}
