package com.ripple.lendmoney.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.present.SplashPresent;
import com.ripple.lendmoney.utils.AppManager;
import com.ripple.lendmoney.utils.SPUtils;
import com.ripple.lendmoney.utils.ToastUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity<SplashPresent> {
    @BindView(R.id.tv_splash_second)
    TextView tvSplashSecond;
    @BindView(R.id.rl_splash)
    RelativeLayout rl_splash;
    @BindView(R.id.rl_guide)
    RelativeLayout rl_guide;
    @BindView(R.id.vp_guide)
    ViewPager vp_guide;
    @BindView(R.id.tv_start)
    TextView tv_start;
    @BindView(R.id.ll_points)
    LinearLayout ll_points;
    @BindView(R.id.iv_point1)
    ImageView iv_point1;
    @BindView(R.id.iv_point2)
    ImageView iv_point2;
    @BindView(R.id.iv_point3)
    ImageView iv_point3;
    @BindView(R.id.iv_point4)
    ImageView iv_point4;
    private ArrayList<View> mViews;
    private PagerAdapter mAdapter;
    private Disposable mdDisposable;

    @Override
    protected String topBarTitle() {
        return null;
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mdDisposable = Flowable.intervalRange(0, 2, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        tvSplashSecond.setText("跳过" + (3 - aLong) + "s");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        getLoginPermission();
                    }
                })
                .subscribe();


    }

    private void getLoginPermission() {
        getRxPermissions().request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) {// 用户已经同意该权限-
                        hadLogin();
                    } else {// 用户拒绝了该权限，并且选中『不再询问』
                        ToastUtil.showToast("为了更好地为您服务,请自行前往权限管理打开相应权限");
                        SplashActivity.this.finish();
                    }
                });
    }

    private void hadLogin() {//读取缓存来判断是否已经登录
        if (GlobleParms.familyTest) {
            MainActivity.launch(this);
            return;
        }
        GlobleParms.sessionId = SPUtils.getInstance(this).getValue(Constant.SESSIONID, "");
        GlobleParms.userName = SPUtils.getInstance(this).getValue(Constant.USERNAME, "");

        if (TextUtils.isEmpty(GlobleParms.sessionId)) {
            LoginActivity.launch(this);
        } else if (!TextUtils.isEmpty(GlobleParms.userName)) {
            GlobleParms.anonymity = GlobleParms.userName.substring(0, 3) + "****" + GlobleParms.userName.substring(7);
            GlobleParms.userId = SPUtils.getInstance(this).getValue(Constant.USERID, "");
            MainActivity.launch(this);
        } else {
            SPUtils.getInstance(this).remove(Constant.SESSIONID);
        }
        finish();
    }

    private void isFirstEnter() {
        if (SPUtils.getInstance(this).getValue("isFirstEnter", true)) {
            rl_splash.setVisibility(View.GONE);
            rl_guide.setVisibility(View.VISIBLE);
            initViewPager();
//            SPUtils.getInstance(this).save("isFirstEnter", false);
        } else {
            MainActivity.launch(this);
            finish();
        }
    }


    @Override
    protected boolean topBarIsShow() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashPresent newP() {
        return new SplashPresent();
    }

    @OnClick({R.id.tv_splash_second, R.id.tv_start})
    public void onClick(View view) {
        Router.newIntent(context).to(MainActivity.class).launch();
        AppManager.getAppManager().finishActivity();
    }


    private void initViewPager() {
        mViews = new ArrayList<View>();
        ImageView view1 = new ImageView(this);
        view1.setScaleType(ImageView.ScaleType.FIT_XY);
        view1.setImageResource(R.drawable.splash_bg);

        ImageView view2 = new ImageView(this);
        view2.setImageResource(R.drawable.splash_bg);
        view2.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView view3 = new ImageView(this);
        view3.setImageResource(R.drawable.splash_bg);
        view3.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView view4 = new ImageView(this);
        view4.setImageResource(R.drawable.splash_bg);
        view4.setScaleType(ImageView.ScaleType.FIT_XY);

        mViews.add(view1);
        mViews.add(view2);
        mViews.add(view3);
        mViews.add(view4);

        mAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mViews.size();
            }
        };

        vp_guide.setAdapter(mAdapter);

        vp_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                tv_start.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
                switch (position) {
                    case 0:
                        setPointImageRes(iv_point1);
                        break;
                    case 1:
                        setPointImageRes(iv_point2);
                        break;
                    case 2:
                        setPointImageRes(iv_point3);
                        break;
                    case 3:
                        setPointImageRes(iv_point4);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    private void setPointImageRes(ImageView imageView) {
        setPointImageRes();
        imageView.setImageResource(R.drawable.guideview_point);
    }

    private void setPointImageRes() {
        iv_point1.setImageResource(R.drawable.point_tran);
        iv_point2.setImageResource(R.drawable.point_tran);
        iv_point3.setImageResource(R.drawable.point_tran);
        iv_point4.setImageResource(R.drawable.point_tran);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(SplashActivity.class)
                .launch();
    }
}
