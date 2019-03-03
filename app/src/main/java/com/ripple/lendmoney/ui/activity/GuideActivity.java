package com.ripple.lendmoney.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.model.CheckBean;
import com.ripple.lendmoney.present.GuidePresent;
import com.ripple.lendmoney.utils.BitmapPhotoUtil;
import com.ripple.lendmoney.utils.LogUtils;
import com.ripple.lendmoney.utils.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.functions.Consumer;

public class GuideActivity extends BaseActivity<GuidePresent> {

    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                testNetMethond();
                break;
            case R.id.button2:
                Router.newIntent(this).to(MainActivity.class).launch();
                break;
            case R.id.button3:
                Router.newIntent(this).to(LoginActivity.class).launch();
                break;
            case R.id.button4:
                LogUtils.e("++++++++++点击了测试网页");
//                Router.newIntent(this).to(WebActivity.class).putString("url","https//www.baidu.com").putString("title","标题").launch();
                WebActivity.launch(this, "https://www.baidu.com", "正在加载...");
                break;
            case R.id.button5:
                HttpUtils.postDialog(this, "inter/index/checkVersion.do", null, new MyCallBack<CheckBean>() {
                    @Override
                    public void onMySuccess(CheckBean bean, MyMessage message) {
                        LogUtils.e(bean.toString());
                    }
                });
                break;
            case R.id.button6:
                HttpUtils.post(this, "inter/index/checkVersion.do", null, new MyCallBack<Map<String, Object>>() {
                    @Override
                    public void onMySuccess(Map<String, Object> bean, MyMessage message) {
                        LogUtils.printMap(bean);
                    }

                });
                break;
        }
    }

    private void testNetMethond() {
        HttpUtils.post(this, "inter/appmessage/getUnReadNum.do", null, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                LogUtils.e(message.toString());
            }
        });

    }

    //去拍照上传图片
    private void getCameraPermission() {
        getRxPermissions()
                .request(Manifest.permission.CAMERA)
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

            photo = BitmapPhotoUtil.getSmallBitmap(mCurrentPhotoFile.getPath());// getimage(mCurrentPhotoFile.getPath());//BitmapFactory.decodeFile(picturePath);

        }
        // int count = photo.getByteCount();
        if (photo != null) {
            // GetBitmapPhoto.replaceMyBitmap(photo, mCurrentPhotoFile);
            // String path = mCurrentPhotoFile.getPath().substring(0,
            // mCurrentPhotoFile.getPath().lastIndexOf("."));
            File f = BitmapPhotoUtil.replaceMyBitmap(photo, mCurrentPhotoFile);
            //System.out.println(f.getPath());
            if (f != null) {
                if (requestCode == USERICON_DATA) {
                    upLoadUserIcon(f);
                }
            }

        }

    }

    private void upLoadUserIcon(File file) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", "CBD3524CFBBD99AAB549A8809F8AAA2B");
        map.put("sessionId", "CBD3524CFBBD99AAB549A8809F8AAA2B");
        HttpUtils.upload(context, "inter/appuser/uploadHeadIcon.do", map, file, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {

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
}
