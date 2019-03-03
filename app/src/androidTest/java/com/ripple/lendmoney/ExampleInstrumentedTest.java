package com.ripple.lendmoney;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();

        assertEquals("com.ripple.lendmoney", context.getPackageName());
        HashMap<String, Object> map = new HashMap<>();
        map.put("versionNo", 120);
        map.put("versionType", "android");
        HttpUtils.post(context, "checkVersion.do", map, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {

            }
        });
    }
}
