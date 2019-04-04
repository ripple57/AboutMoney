package com.ripple.lendmoney.ui.fragment;

import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.event.RefreshUserInfoEvent;
import com.ripple.lendmoney.model.ContactsBean;
import com.ripple.lendmoney.present.ContactsFragPresent;
import com.ripple.lendmoney.ui.activity.MyInfoActivity;
import com.ripple.lendmoney.utils.DialogUtil;
import com.ripple.lendmoney.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.BusFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:21.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class ContactsFragment extends BaseLazyFragment<ContactsFragPresent> {
    @BindView(R.id.iv_contactsFrag_head)
    ImageView ivContactsFragHead;
    @BindView(R.id.tv_contactsFrag_contactState)
    TextView tvContactsFragContactState;
    @BindView(R.id.btn_contactsFrag_commit)
    Button btnContactsFragCommit;
    private String contactsJson;
    private boolean contactState = false;

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        showTips();
    }

    private void showTips() {
        new QMUIDialog.MessageDialogBuilder(context)
                .setMessage("为了更好的为您服务,请允许我们读取您的通讯录")
                .addAction("拒绝", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        contactState = false;
                    }
                })
                .addAction("允许", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        toGetContacts();

                    }
                })
                .setCancelable(false).setCanceledOnTouchOutside(false).show();
    }

    private void initView() {
        btnContactsFragCommit.setText(GlobleParms.AuthenticateCanNext ? "下一步" : "提交");
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_contacts_layout;
    }

    @Override
    public ContactsFragPresent newP() {
        return new ContactsFragPresent();
    }


    @OnClick(R.id.btn_contactsFrag_commit)
    public void onViewClicked() {
        if (contactState) {
            getP().uploadContacts(context, contactsJson);
        } else {
            showTips();
        }
    }

    //去获取通讯录列表
    private void toGetContacts() {
        getRxPermissions().request(Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            getContactsWithDialog();
                        } else {
                            getvDelegate().toastShort("亲，同意了权限才能更好的使用软件哦");
                        }
                    }
                });
    }

    private void getContactsWithDialog() {
        QMUITipDialog qmuiTipDialog = DialogUtil.showDialog(context, "");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                List<ContactsBean> contacts = getContacts();
                contactsJson = JSONArray.toJSONString(contacts);
                String contactsJson = JSONArray.toJSONString(contacts);
                e.onNext(contactsJson);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        qmuiTipDialog.dismiss();
                        contactState = true;
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
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};

                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = context.getContentResolver().query(
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

    public void uploadSuccess() {
        BusFactory.getBus().post(new RefreshUserInfoEvent());
        ToastUtil.showToast("上传成功");
        if (GlobleParms.AuthenticateCanNext) {
            MyInfoActivity.launch(context);
        } else {
            context.finish();
        }
    }
}
