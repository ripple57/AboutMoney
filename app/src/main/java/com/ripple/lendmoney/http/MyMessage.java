package com.ripple.lendmoney.http;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 网络请求的json数据封装为实例的工具类
 */
public class MyMessage implements Serializable {
    private int state = -10000;
    private String description ;
    private String bodyStr;
    private int pageCount;
    private int pageCurrent;
    private int pageNext;
    private int rowCount;
    private String data;

    public MyMessage() {
    }

    public MyMessage(String result) {
        init(result);
    }

    private void init(String result) {

        try {
            JSONObject jsonObject = null;
            if (!TextUtils.isEmpty(result)) {
                jsonObject = new JSONObject(result);
            }
            if (jsonObject != null) {
                if (jsonObject.has("state"))
                    state = jsonObject.getInt("state");
                if (jsonObject.has("des"))
                    description = jsonObject.getString("des");
                if (jsonObject.has("body")) {//有body字段
                    bodyStr = jsonObject.getString("body");
                    if (!TextUtils.isEmpty(bodyStr)) {
                        JSONObject bodyJsonObject = new JSONObject(bodyStr);
                        /************* 翻页相关 *****************/
                        if (bodyJsonObject.has("pageCount"))
                            this.pageCount = bodyJsonObject.getInt("pageCount");
                        if (bodyJsonObject.has("pageCurrent"))
                            this.pageCurrent = bodyJsonObject.getInt("pageCurrent");
                        if (bodyJsonObject.has("pageNext"))
                            this.pageNext = bodyJsonObject.getInt("pageNext");
                        if (bodyJsonObject.has("rowCount"))//
                            this.rowCount = bodyJsonObject.getInt("rowCount");
                        if (bodyJsonObject.has("data"))//
                            this.data = bodyJsonObject.getString("data");
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public String toString() {
        return "MyMessage{" +
                "state=" + state +
                ", description='" + description + '\'' +
                ", bodyStr='" + bodyStr + '\'' +
                ", pageCount=" + pageCount +
                ", pageCurrent=" + pageCurrent +
                ", pageNext=" + pageNext +
                ", rowCount=" + rowCount +
                ", data='" + data + '\'' +
                '}';
    }

    public int getState() {
        return state;
    }


    public void setState(int state) {
        this.state = state;
    }

    public String getBody() {
        return bodyStr;
    }
    public String getData() {
        return data;
    }

    public void setBodyStr(String bodyStr) {
        this.bodyStr = bodyStr;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
    public int getPageCount() {
        return pageCount;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public int getPageNext() {
        return pageNext;
    }

    public int getRowCount() {
        return rowCount;
    }
    public boolean isLastPage() {
        return pageCount == pageCurrent;
    }

}
