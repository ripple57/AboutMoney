package com.ripple.lendmoney;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
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
    @Test
    public void createFile() {//         /storage/emulated/0/FaceRecord/face.mp4
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = null;
        try {
            File dir = new File(path + "/FaceRecord/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.e("Ripple", dir.getAbsolutePath() + "======文件夹是否创建?" + dir.exists());
            String name = "face.mp4";
            file = new File(dir, name);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Ripple", e.toString());
        }
        Log.e("Ripple", file.getAbsolutePath() + "======文件是否创建?" + file.exists());
    }
}
