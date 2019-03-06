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
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONArray;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.model.ContactsBean;
import com.ripple.lendmoney.present.GuidePresent;
import com.ripple.lendmoney.utils.BitmapPhotoUtil;
import com.ripple.lendmoney.utils.LogUtils;
import com.ripple.lendmoney.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.functions.Consumer;

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


    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6
            , R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12})
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
                FaceRecognitionActivity.launch(this);
                break;
            case R.id.button6:
                AuthenticateActivity.launch(this);
                break;
            case R.id.button7:
                AuthenticateActivity.launch(this, Constant.TYPE_IDCARDFRAG);
                break;
            case R.id.button8:
                
                break;
            case R.id.button9:
                break;
            case R.id.button10:
                break;
            case R.id.button11:
                break;
            case R.id.button12:
                break;
        }
    }

    //去获取通讯录列表
    private void toGetContacts() {
        getRxPermissions()
                .request(Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            List<ContactsBean> contacts = getContacts();
                            String jsonString = JSONArray.toJSONString(contacts);
                            LogUtils.e(jsonString);
                        } else {
                            getvDelegate().toastShort("亲，同意了权限才能更好的使用软件哦");
                        }
                    }
                });
    }

    //获取通讯录列表
    private List<ContactsBean> getContacts() {
        ArrayList<ContactsBean> arrayList = new ArrayList<>();

        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};

                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = this.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null, null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
                ArrayList<String> nums = new ArrayList<String>();
                if (phonesCusor != null && phonesCusor.moveToFirst()) {
                    do {
                        String num = phonesCusor.getString(0);
                        nums.add(num);
                    } while (phonesCusor.moveToNext());
                }
                arrayList.add(new ContactsBean(name, nums));
            } while (cursor.moveToNext());
        }
        return arrayList;
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
