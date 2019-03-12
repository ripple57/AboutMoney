package com.ripple.lendmoney.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.security.MessageDigest;

/**
 * 拿到手机的信息拼凑成唯一的id
 */
public class PhoneInfoUtil {

    public static final String phoneModel = Build.MODEL;// machineType 机型
    public static final String phoneSysVersion = "android" + Build.VERSION.RELEASE;// versionType 版本类型


    public static String getUniqueNum(Context context) {//唯一码 由设备id 和 mac地址生成
        return getSessionId(context, "");
    }

    public static String getSessionId(Context context, String phone) {
        String m_szLongID = "";
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String m_szImei = TelephonyMgr.getDeviceId();
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
            m_szLongID = m_szImei + m_szWLANMAC + phone;
            return md5(m_szLongID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5(TextUtils.isEmpty(phone) ? "18888888888" : phone);//如果无法获取手机id则生成自定义的唯一id;

    }

    @NonNull
    private static String md5(String m_szLongID) {
        if (TextUtils.isEmpty(m_szLongID)) {
            return "";
        }
        // compute md5
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");

            m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
            // get md5 bytes
            byte p_md5Data[] = m.digest();
            // create a hex string
            String m_szUniqueID = new String();
            for (int i = 0; i < p_md5Data.length; i++) {
                int b = (0xFF & p_md5Data[i]);
                // if it is a single digit, make sure it have 0 in front (proper padding)
                if (b <= 0xF)
                    m_szUniqueID += "0";
                // add number to string
                m_szUniqueID += Integer.toHexString(b);
            } // hex string to uppercase
            m_szUniqueID = m_szUniqueID.toUpperCase();
            return m_szUniqueID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getVeision(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String versionName = info.versionName;
            return versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = null;

            device_id = tm.getDeviceId();


            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();

            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }


            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
