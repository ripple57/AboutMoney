package com.ripple.lendmoney.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONArray;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.event.NewOrderEvent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.model.ContactsBean;
import com.ripple.lendmoney.present.GuidePresent;
import com.ripple.lendmoney.utils.BitmapPhotoUtil;
import com.ripple.lendmoney.utils.DialogUtil;
import com.ripple.lendmoney.utils.LogUtils;
import com.ripple.lendmoney.utils.ToastUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.BusFactory;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GuideActivity extends BaseActivity<GuidePresent> {


    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;
    @BindView(R.id.button10)
    Button button10;
    @BindView(R.id.button11)
    Button button11;
    @BindView(R.id.button12)
    Button button12;
    private Uri videoUri;
    private int TAKE_VIDEO;
    private QMUILoadingView loadingView;
    private QMUITipDialog qmuiTipDialog;


    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6
            , R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                qmuiTipDialog = DialogUtil.showDialog(this, "加载中...");
                break;
            case R.id.button2:
                qmuiTipDialog.dismiss();
                break;
            case R.id.button3:
                testContacts();
                break;
            case R.id.button4:
                WebActivity.launch(this, URLConfig.REGIST_AGREEMENT, "用户注册协议");
                break;
            case R.id.button5:
                FaceRecognitionActivity.launch(this);
                break;
            case R.id.button6:
                AuthenticateActivity.launch(this);
                break;
            case R.id.button7:
                AuthenticateActivity.launch(this, Constant.TYPE_FAMILYFRAG);
                break;
            case R.id.button8:
                BusProvider.getBus().post(new NewOrderEvent());
                break;
            case R.id.button9:
                BusFactory.getBus().post(new NewOrderEvent());
                break;
            case R.id.button10:
                MakeIOUActivity.launch(this);
                break;
            case R.id.button11:
                AssessActivity.launch(this, "");
                break;
            case R.id.button12:
                AuthenticateInfoActivity.launch(this);
                break;
        }
    }

    private void testContacts() {
        QMUITipDialog qmuiTipDialog = DialogUtil.showDialog(this, "");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("This msg from work thread :" + Thread.currentThread().getName());
                ArrayList<ContactsBean> contacts = new ArrayList<>();
                LogUtils.e(Thread.currentThread().getName() + "开始时间" + System.currentTimeMillis() );
                for (int i = 0; i < 1000; i++) {
                    ArrayList<String> nums = new ArrayList<String>();
                    nums.add((10000000000l + i) + "");
                    Thread.sleep(10);
                    contacts.add(new ContactsBean("测试通讯录", nums));
                }
                String contactsJson = JSONArray.toJSONString(contacts);
                e.onNext(contactsJson);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.e(Thread.currentThread().getName() + "结束时间" + System.currentTimeMillis());
                        LogUtils.e(s);
                        qmuiTipDialog.dismiss();
                    }
                });

//        new ContactsFragPresent().uploadContacts(this,contactsJson);
    }

    private void createFile() {//         /storage/emulated/0/FaceRecord/face.mp4
        String path = Environment.getExternalStorageDirectory().getPath();
//        String path = getExternalCacheDir().getPath();
        File file = null;
        try {
            File dir = new File(path + "/FaceRecord/");
            if (!dir.exists()) {
                boolean mkdirs = dir.mkdirs();
                Log.e("Ripple", dir.getAbsolutePath() + mkdirs + "======文件夹是否创建?" + mkdirs);
            }
            Log.e("Ripple", dir.getAbsolutePath() + "======文件夹是否创建?" + dir.exists());
            String name = "face.txt";
            file = new File(dir, name);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Ripple", e.toString());
        }
        Log.e("Ripple", file.getAbsolutePath() + "======文件是否创建?" + file.exists());
//        print("测试一个扥二分二位",file.getAbsolutePath());
    }

    //向已创建的文件中写入数据
    public void print(String str, String file) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        String datetime = "";
        try {
            SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " "
                    + "hh:mm:ss");
            datetime = tempDate.format(new java.util.Date()).toString();
            fw = new FileWriter(file, true);//
            // 创建FileWriter对象，用来写入字符流
            bw = new BufferedWriter(fw); // 将缓冲对文件的输出
            String myreadline = datetime + "[]" + str;

            bw.write(myreadline + "\n"); // 写入文件
            bw.newLine();
            bw.flush(); // 刷新该流的缓冲
            bw.close();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                bw.close();
                fw.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
            }
        }
    }

    /**
     * 将asset文件写入缓存
     */
    private boolean copyAssetAndWrite(String fileName) {
        try {
            File cacheDir = getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            File outFile = new File(cacheDir, fileName);
            if (!outFile.exists()) {
                boolean res = outFile.createNewFile();
                if (!res) {
                    return false;
                }
            } else {
                if (outFile.length() > 10) {//表示已经写入一次
                    return true;
                }
            }
            InputStream is = getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void upLoadVideo() {
        copyAssetAndWrite("test.mp4");
        File dataFile = new File(getCacheDir(), "test.mp4");
        HttpUtils.upload(this, URLConfig.addFaceVideo, null, dataFile, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                LogUtils.e("上传成功了");
            }
        });

    }

    private void doPickPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(intent, USERICON_DATA);

    }


    private void testNetMethond() {
        HttpUtils.post(this, "inter/appmessage/getUnReadNum.do", null, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                LogUtils.e(message.toString());
                ToastUtil.showToast(message.toString());
            }
        });

    }

    //去拍照上传图片
    private void getCameraPermission() {
        getRxPermissions().request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            doTakePhoto();
                        } else {
                            getCameraPermission();
                            getvDelegate().toastShort("亲，同意了权限才能更好的使用软件哦");
                        }
                    }
                });
    }

    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private File mCurrentPhotoFile;// 照相机拍照得到的图片
    private static final int USERICON_DATA = 350;

    protected void doTakePhoto() {
        try {
            if (!PHOTO_DIR.exists())
                PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, BitmapPhotoUtil.getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = BitmapPhotoUtil.getTakePickIntent(context, mCurrentPhotoFile);
            GuideActivity.this.startActivityForResult(intent, USERICON_DATA);
        } catch (ActivityNotFoundException e) {
        }
    }

    /**
     * 录像
     *
     * @param v
     */
    public void takeVideo(View v) {
        // 创建File对象，用于存储录像,getExternalFilesDir()表示保存到SD卡
        File videoFile = new File(getExternalFilesDir(null), "video.3gp");

        try {
            // 如果存在同名文件则进行删除，再创建新的文件
            if (videoFile.exists()) {
                videoFile.delete();
            }
            videoFile.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 保存录像存储的路径
        videoUri = Uri.fromFile(videoFile);
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);

        // TAKE_VIDEO请求码
        startActivityForResult(intent, TAKE_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Uri uri = data.getData();
            if (null == uri) {
                return;
            } else {
                if (uri.toString().contains("MIUI/Gallery/cloud")) {
                    ToastUtil.showShort(context, "请选择本地相册的图片");
                    return;
                }
            }
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            mCurrentPhotoFile = new File(picturePath);
            cursor.close();
        }

        Bitmap photo = null;
        if (mCurrentPhotoFile != null) {
            photo = BitmapPhotoUtil.getSmallBitmap(mCurrentPhotoFile.getPath());
        }
        if (photo != null) {
            File f = BitmapPhotoUtil.replaceMyBitmap(photo, mCurrentPhotoFile);
            if (f != null) {
                if (requestCode == USERICON_DATA) {
                    files.add(f);
                }
            }

        }

    }

    private List<File> files = new ArrayList<>();

    private void upLoadPictures(List<File> files) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("realName", "波波");
        map.put("idNumber", "140825199802215555");
        HttpUtils.upload(context, URLConfig.addIDCard, map, files, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                LogUtils.e("--------------------------------------");
            }
        }, true);
    }

    private void upLoadUserIcon(File file) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("realName", "波波");
        map.put("idNumber", "140825199802215555");
        HttpUtils.upload(context, URLConfig.addIDCard, map, file, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                LogUtils.e("--------------------------------------");
            }
        });
    }

    @Override
    public void getNetData() {

    }


    @Override
    public void initData(Bundle savedInstanceState) {
    }


    @Override
    protected String topBarTitle() {
        return "测试";
    }

    @Override
    protected boolean topBarIsShowBack() {
        return false;
    }

    @Override
    protected boolean topBarIsTransparent() {
        return false;
    }

    @Override
    protected boolean topBarIsShow() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public GuidePresent newP() {
        return new GuidePresent();
    }

    public static void launch(Activity activity) {

        Router.newIntent(activity)
                .to(GuideActivity.class)
                .launch();
    }

    /**
     * 读写权限 自己可以添加需要判断的权限
     */
    public static String[] permissionsREAD = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限 false-表示权限已开启
     */
    public static boolean lacksPermissions(Context mContexts, String[] permissions) {
        for (String permission : permissions) {
            if (lacksPermission(mContexts, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
