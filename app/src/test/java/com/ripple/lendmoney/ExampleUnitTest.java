package com.ripple.lendmoney;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String reg = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        String id = "140824199102230015";
        boolean matches = !id.matches(reg);
        System.out.print(matches);

    }

    @Test
    public void test() {
        final String videoFileName = "/storage/emulated/0/DCIM/Camera/VID_20190324_144050.mp4";
        File file = new File(videoFileName);
        File dir = new File(file.getParent());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("议程了报错来来来" + e.toString());
        }
        System.out.println("成功创建了file" + file.getAbsolutePath());
    }

    @Test
    public void test1() {
        File dir = new File("sdcard/FaceRecord/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = "VID.mp4";
        File file = new File(dir, name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(file.exists());
        String na = file.getAbsolutePath();
        System.out.println(na);
    }

    @Test
    public void createFile() {//         /storage/emulated/0/FaceRecord/face.mp4
//        String path = Environment.getExternalStorageDirectory().getPath();
        String path = "/storage/emulated/0";
        File file = null;
        try {
            File dir = new File(path + "/FaceRecord/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            System.out.println(dir.getAbsolutePath() + "======文件夹是否创建?" + dir.exists());
            String name = "face.mp4";
            file = new File(dir, name);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        System.out.println(file.getAbsolutePath() + "======文件是否创建?" + file.exists());
    }
}