package com.ripple.lendmoney.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 内部存储工具类  sp
 * Created by Ripple on 2015/12/26.
 * 保存:
 * void save(String key, Object value)
 * 读取:
 * <T> T </T>getVlaue(String key, Object defValue)
 * 移除
 * void remove(String key)
 */
public class SPUtils {
    private static SPUtils insrance;
    private static SharedPreferences sp;

    private SPUtils() {
    }

    public static String getString(Context context, String spName, String name) {
        try {
            return context.getSharedPreferences(spName, Context.MODE_PRIVATE).getString(name, null);
        } catch (Exception e) {
            return "";
        }
    }

    public static void setString(Context context, String spName, String name, String value) {
        try {
            context.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putString(name, value).commit();
        } catch (Exception e) {

        }

    }

    public static SPUtils getInstance(Context context) {
        if (insrance == null) {
            sp = context.getSharedPreferences("lendmoney_sp", Context.MODE_PRIVATE);
            insrance = new SPUtils();
        }
        return insrance;
    }

    //保存;
    public void save(String key, Object value) {
        if (value instanceof String) {
            sp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Integer) {
            sp.edit().putInt(key, (Integer) value).commit();
        } else if (value instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Float) {
            sp.edit().putFloat(key, (Float) value).commit();

        }
    }

    //读取:
    public <T> T getValue(String key, Object defaultValue) {
        T t = null;
        if (defaultValue == null || defaultValue instanceof String) {
            t = (T) sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            Integer value = sp.getInt(key, (Integer) defaultValue);
            t = (T) value;
        } else if (defaultValue instanceof Boolean) {
            Boolean value = sp.getBoolean(key, (Boolean) defaultValue);
            t = (T) value;
        }else if (defaultValue instanceof Float) {
            Float value = sp.getFloat(key, (Float) defaultValue);
            t = (T) value;
        }
        return t;
    }

    //移除
    public void remove(String key) {
        sp.edit().remove(key).commit();
    }
}
