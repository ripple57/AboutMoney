package com.ripple.lendmoney.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.present.SplashPresent;
import com.ripple.lendmoney.utils.AppManager;
import com.ripple.lendmoney.utils.SPUtils;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        isFirstEnter();//是否开启引导页功能
        isLogin();
    }

    private void isLogin() {
        ToActivity(MainActivity.class);
//        ToActivity(LoginActivity.class);
    }

    private void isFirstEnter() {
        if (SPUtils.getInstance(this).getValue("isFirstEnter", true)) {
            rl_splash.setVisibility(View.GONE);
            rl_guide.setVisibility(View.VISIBLE);
            initViewPager();
            SPUtils.getInstance(this).save("isFirstEnter", false);
        } else {
            ToActivity(MainActivity.class);
        }
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


    private void ToActivity(final Class clazz) {
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
                        Router.newIntent(context).to(clazz).launch();
                        AppManager.getAppManager().finishActivity();
                    }
                })
                .subscribe();
    }

    private void initViewPager() {
        mViews = new ArrayList<View>();
        ImageView view1 = new ImageView(this);
        view1.setScaleType(ImageView.ScaleType.FIT_XY);
        view1.setImageResource(R.mipmap.splash_bg);

        ImageView view2 = new ImageView(this);
        view2.setImageResource(R.mipmap.splash_bg);
        view2.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView view3 = new ImageView(this);
        view3.setImageResource(R.mipmap.splash_bg);
        view3.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView view4 = new ImageView(this);
        view4.setImageResource(R.mipmap.splash_bg);
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
}
