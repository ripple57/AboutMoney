package com.ripple.lendmoney.widget;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AutoVerticalScrollTextViewUtil {
    private static final int MESSAGE_CODE = 200;
    private long duration = 1000L;
    private AutoVerticalScrollTextViewUtil.MyHandler handler = new AutoVerticalScrollTextViewUtil.MyHandler(this);
    private AutoVerticalScrollTextView textView;
    private ExecutorService executorService;
    private boolean isRunning;
    private int number = 0;
    private int currentPos = 0;
    private ArrayList<String> mDatas = new ArrayList();
    private CharSequence title;
//    private AutoVerticalScrollTextViewUtil.OnMyClickListener onMyClickListener;

    public AutoVerticalScrollTextViewUtil(AutoVerticalScrollTextView textView, ArrayList<String> datas) {
        this.mDatas = datas;
        this.textView = textView;
//        this.textView.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                if (!ZTextViewClickUtil.isFastClick()) {
//                    if (AutoVerticalScrollTextViewUtil.this.onMyClickListener != null) {
//                        AutoVerticalScrollTextViewUtil.this.onMyClickListener.onMyClickListener(AutoVerticalScrollTextViewUtil.this.currentPos, AutoVerticalScrollTextViewUtil.this.title);
//                    }
//
//                }
//            }
//        });
    }

    public AutoVerticalScrollTextViewUtil setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public void start() {
        this.isRunning = true;
        this.startThread();
    }

    public void stop() {
        this.isRunning = false;
        if (this.executorService != null) {
            this.executorService.shutdownNow();
        }

    }

    public boolean getIsRunning() {
        return this.isRunning;
    }

    private void startThread() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    if (AutoVerticalScrollTextViewUtil.this.mDatas != null && AutoVerticalScrollTextViewUtil.this.mDatas.size() > 0) {
                        while(AutoVerticalScrollTextViewUtil.this.isRunning) {
                            AutoVerticalScrollTextViewUtil.this.currentPos = AutoVerticalScrollTextViewUtil.this.number % AutoVerticalScrollTextViewUtil.this.mDatas.size();
                            AutoVerticalScrollTextViewUtil.this.title = (String)AutoVerticalScrollTextViewUtil.this.mDatas.get(AutoVerticalScrollTextViewUtil.this.currentPos);
                            AutoVerticalScrollTextViewUtil.this.number++;
                            AutoVerticalScrollTextViewUtil.this.handler.sendEmptyMessage(200);
                            Thread.sleep(AutoVerticalScrollTextViewUtil.this.duration);
                        }
                    } else {
                        AutoVerticalScrollTextViewUtil.this.isRunning = false;
                    }
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }

            }
        });
        if (this.executorService == null || this.executorService.isShutdown()) {
            this.executorService = Executors.newSingleThreadScheduledExecutor();
        }

        this.executorService.execute(thread);
    }

//    public void setOnMyClickListener(AutoVerticalScrollTextViewUtil.OnMyClickListener onMyClickListener) {
//        this.onMyClickListener = onMyClickListener;
//    }

//    public interface OnMyClickListener {
//        void onMyClickListener(int var1, CharSequence var2);
//    }

    private static class MyHandler extends Handler {
        private final WeakReference<AutoVerticalScrollTextViewUtil> mUtil;

        MyHandler(AutoVerticalScrollTextViewUtil util) {
            this.mUtil = new WeakReference(util);
        }

        public void handleMessage(Message msg) {
            AutoVerticalScrollTextViewUtil currentUtil = (AutoVerticalScrollTextViewUtil)this.mUtil.get();
            if (msg.what == 200) {
                currentUtil.textView.next();
                if (!TextUtils.isEmpty(currentUtil.title)) {
                    currentUtil.textView.setText(currentUtil.title);
                }
            }

        }
    }
}
