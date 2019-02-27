package com.ripple.lendmoney.utils;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


/**
 * 日志输出工具类
 *
 * @author ripple
 */
public class LogUtils {
    private static final String TAG = "Ripple";
    public static boolean DEBUG = true;

    /**
     * 保存日志到文件中
     */
    private final boolean isSaveToFile = false;

    /**
     * 日志保存路径，sdcard/(UMS_APP_NAME设置的名称)/Log.txt
     */
//    private static final String LOG_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + FileConstants.RESOURCE_DIRECTORY;
//    private static final String LOG_PATH = LOG_DIR + "Log.txt";

    private static LogUtils instance = new LogUtils();

    public static LogUtils getLogger() {
        return instance;
    }

    /**
     * log.i
     */
    public void info(String msg) {
        if (DEBUG && !TextUtils.isEmpty(msg)) {
            final String message = createMessage(msg);

            Log.i(TAG, message + "\n");
        }
    }

    public static void i(String msg) {
        if (msg == null) {
            instance.info("参数为空!!");
            return;
        }
        instance.info(msg);
    }

    public static void i(int msg) {
        i(msg + "");
    }

    /**
     * @param msg
     */
    public static void i(String tag, String msg) {

        if (msg == null) {
            LogUtils.i(tag, "参数为空!!");
            return;
        }
        instance.info(tag, msg);
    }


    public void info(String tag, String msg) {
        if (DEBUG) {
            final String message = createMessage(msg);
            Log.i(tag, message);
        }
    }

    public static void i(Exception e) {
        instance.info(e != null ? e.toString() : "null");
    }

    /**
     * log.v
     */
    public void verbose(String msg) {
        if (DEBUG) {
            final String message = createMessage(msg);
            Log.v(TAG, message);
        }
    }

    public void verbose(String tag, String msg) {
        if (DEBUG) {
            final String message = createMessage(msg);
            Log.v(tag, message);
        }
    }

    public static void v(String msg) {
        instance.verbose(msg);
    }

    public static void v(String tag, String msg) {
        instance.verbose(tag, msg);
    }

    public static void v(Exception e) {
        instance.verbose(e != null ? e.toString() : "null");
    }

    /**
     * log.d
     */
    public void debug(String msg) {
        if (DEBUG) {
            final String message = createMessage(msg);
            Log.d(TAG, message);
        }
    }

    public void debug(String tag, String msg) {
        if (DEBUG) {
            final String message = createMessage(msg);
            Log.d(tag, message);
        }
    }

    public static void d(String msg) {
        instance.debug(msg);
    }

    public static void d(String tag, String msg) {
        instance.debug(tag, msg);
    }


    public static void d(Exception e) {
        instance.debug(e != null ? e.toString() : "null");
    }

    /**
     * log.warn
     *
     * @param msg
     */
    public void warn(String tag, String msg) {
        if (DEBUG) {
            final String message = createMessage(msg);
            Log.w(tag, message);
        }
    }

    public void warn(String msg) {
        if (DEBUG) {
            final String message = createMessage(msg);
            Log.w(TAG, message);
        }
    }

    public static void w(String tag, String msg) {
        instance.warn(tag, msg);
    }

    public static void w(String msg) {
        instance.warn(msg);
    }

    public static void w(Exception e) {
        instance.warn(e != null ? e.toString() : "null");
    }

    /**
     * log.e
     *
     * @param msg
     */
    public void error(String msg) {
        if (DEBUG) {
            final String message = createMessage(msg);
            Log.e(TAG, message);
        }
    }

    public void error(String tag, String msg) {
        if (DEBUG) {
            final String message = createMessage(msg);
            Log.e(tag, message);
        }
    }

    public static void thread(String msg) {
     e(msg+"线程:"+ Thread.currentThread().getName());
    }

    public static void e(String msg) {
        instance.error(msg);
    }

    public static void e(int msg) {
        instance.error(msg + "");
    }

    public static void e(String tag, String msg) {
        instance.error(tag, msg);
    }


    public static void e(Exception e) {
        instance.error(e);
    }

    /**
     * log.error
     *
     * @param e
     */
    public void error(Exception e) {
        if (DEBUG) {
            final StringBuffer sb = new StringBuffer();
            final String name = getFunctionName();
            final StackTraceElement[] sts = e.getStackTrace();

            if (name != null) {
                sb.append(name + " - " + e + "\r\n");
            } else {
                sb.append(e + "\r\n");
            }
            if (sts != null && sts.length > 0) {
                for (final StackTraceElement st : sts) {
                    if (st != null) {
                        sb.append("[ " + st.getFileName() + ":" + st.getLineNumber() + " ]\r\n");
                    }
                }
            }
            Log.e(TAG, sb.toString());
        }
    }

    private static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);

    /**
     * 获取函数名称
     */
    private String getFunctionName() {
        final StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (final StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }

            Calendar c = Calendar.getInstance();
            String ctime = formater.format(c.getTime());

            return ctime + "[" + st.getFileName()
                    + ":" + st.getLineNumber() + "]"
                    + "[" + st.getMethodName() + "]\n";
        }

        return null;
    }

    private String createMessage(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "null";
        }

        final String functionName = getFunctionName();
        final String message = (functionName == null ? msg : (functionName + " - " + msg));

        if (isSaveToFile) {
//            FileUtil.writeStringToSDCard(message, LOG_DIR, LOG_PATH, true,true);
        }

        return message;
    }


    public static void printBundle(Bundle bundle) {
        LogUtils.i("--------------------打印  bundle---------------------");
        if (bundle == null) {
            LogUtils.i("bundle为空!!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("bundle: ");
        for (String key : bundle.keySet()) {
            sb.append("\nkey:" + key + ", value:" + bundle.get(key));
        }
        LogUtils.i(sb.toString());
    }

    public static void printMap(Map map) {
        try {
            Set set = map.keySet();
            Iterator ite = set.iterator();
            StringBuilder sb = new StringBuilder();
            sb.append("\n+++++++++++++++开始打印Map集合++++++++++++++");
            int i = 0;
            while (ite.hasNext()) {
                String key = (String) ite.next();
                String value = map.get(key).toString();
                sb.append("\n第" + (i++) + "个key:" + key + ", value:" + value);
            }
            i(sb.toString());
        } catch (Exception e) {
            i("Map打印出错了!!");
            e.printStackTrace();
        }
    }

    public static void printList(List list) {
        if (list == null) {
            i("list 集合为空!!!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("开始打印 List 集合了:\n");
        try {
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            i(sb.toString());
        } catch (Exception e) {
            i("list 打印出错了");
            e.printStackTrace();
        }

    }
}
