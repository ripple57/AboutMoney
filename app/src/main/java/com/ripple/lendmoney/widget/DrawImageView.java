package com.ripple.lendmoney.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
public class DrawImageView extends AppCompatImageView {

    public DrawImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Paint paint = new Paint();

    {
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(2.5f);//设置线宽
        paint.setAlpha(100);
    }

    ;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(new Rect(100, 200, 400, 500), paint);//绘制矩形

    }


}