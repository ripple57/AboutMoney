package us.pinguo.svideo.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import us.pinguo.svideo.interfaces.IVideoPathGenerator;

/**
 * Created by huangwei on 2016/4/23.
 */
public class DateVideoNameGenerator implements IVideoPathGenerator {
    @Override
    public String generate() {
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = null;
        try {
            File dir = new File(path+"/FaceRecord/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String name = "face.mp4";
            file = new File(dir, name);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("Ripple", file.getAbsolutePath()+"======文件是否创建?" + file.exists());
        return file.getAbsolutePath();
    }
}
