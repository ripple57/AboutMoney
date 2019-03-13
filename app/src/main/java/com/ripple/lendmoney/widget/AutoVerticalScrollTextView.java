package com.ripple.lendmoney.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class AutoVerticalScrollTextView extends TextSwitcher implements ViewFactory {
    private Context mContext;
    private AutoVerticalScrollTextView.Rotate3dAnimation mInUp;
    private AutoVerticalScrollTextView.Rotate3dAnimation mOutUp;

    public AutoVerticalScrollTextView(Context context) {
        this(context, (AttributeSet)null);
    }

    public AutoVerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.init();
    }

    private void init() {
        this.setFactory(this);
        this.mInUp = this.createAnim(true, true);
        this.mOutUp = this.createAnim(false, true);
        this.setInAnimation(this.mInUp);
        this.setOutAnimation(this.mOutUp);
    }

    private AutoVerticalScrollTextView.Rotate3dAnimation createAnim(boolean turnIn, boolean turnUp) {
        AutoVerticalScrollTextView.Rotate3dAnimation rotation = new AutoVerticalScrollTextView.Rotate3dAnimation(turnIn, turnUp);
        rotation.setDuration(1000L);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());
        return rotation;
    }

    public View makeView() {
        TextView textView = new TextView(this.mContext);
//        textView.setGravity(8388611);
        textView.setTextSize(11.0f);
        textView.setSingleLine(true);
        textView.setGravity(16);
        textView.setEllipsize(TruncateAt.END);
        textView.setTextColor(Color.parseColor("#ababab"));
        return textView;
    }

    public void next() {
        if (this.getInAnimation() != this.mInUp) {
            this.setInAnimation(this.mInUp);
        }

        if (this.getOutAnimation() != this.mOutUp) {
            this.setOutAnimation(this.mOutUp);
        }

    }

    private class Rotate3dAnimation extends Animation {
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        Rotate3dAnimation(boolean turnIn, boolean turnUp) {
            this.mTurnIn = turnIn;
            this.mTurnUp = turnUp;
        }

        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            this.mCamera = new Camera();
            this.mCenterY = (float)AutoVerticalScrollTextView.this.getHeight();
            this.mCenterX = (float)AutoVerticalScrollTextView.this.getWidth();
        }

        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float centerX = this.mCenterX;
            float centerY = this.mCenterY;
            Camera camera = this.mCamera;
            int derection = this.mTurnUp ? 1 : -1;
            Matrix matrix = t.getMatrix();
            camera.save();
            if (this.mTurnIn) {
                camera.translate(0.0F, (float)derection * this.mCenterY * (interpolatedTime - 1.0F), 0.0F);
            } else {
                camera.translate(0.0F, (float)derection * this.mCenterY * interpolatedTime, 0.0F);
            }

            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}
