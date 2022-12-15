package com.sawaaid.malltemplate.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class ViewDotsLoading extends View {

    private static final String TAG = ViewDotsLoading.class.getSimpleName();

    //actual dot radius
    private int mDotRadius = 4;


    //to get identified in which position dot has to bounce
    private int mDotPosition;

    //specify how many dots you need in a progressbar
    private int mDotAmount = 3;

    private Paint paint;
    private Context context;


    public ViewDotsLoading(Context context) {
        super(context);
        init(context);
    }

    public ViewDotsLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewDotsLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        mDotRadius = dpToPx(mDotRadius);
        paint = new Paint();
        //set the color for the dot that you want to draw
        int color = Color.GRAY;
        Drawable background = getBackground();
        if (background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();
        }
        paint.setColor(color);
        setBackgroundColor(Color.TRANSPARENT);
    }

    //Method to draw your customized dot on the canvas
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //function to create dot
        createDot(canvas, paint);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    private void createDot(Canvas canvas, Paint paint) {
        for (int i = 0; i < mDotAmount; i++) {
            if (i == mDotPosition) {
                paint.setAlpha(255);
            } else {
                paint.setAlpha(100);
            }
            canvas.drawCircle(mDotRadius + (i * mDotRadius * 3), mDotRadius, mDotRadius, paint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //calculate the view width
        int width = mDotRadius * 2 + ((mDotAmount - 1) * mDotRadius * 3);
        int height = mDotRadius * 2;

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    private void startAnimation() {
        BounceAnimation bounceAnimation = new BounceAnimation();
        bounceAnimation.setDuration(600);
        bounceAnimation.setRepeatCount(Animation.INFINITE);
        bounceAnimation.setInterpolator(new LinearInterpolator());
        bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mDotPosition++;
                if (mDotPosition == mDotAmount) mDotPosition = 0;
                invalidate();
            }
        });
        startAnimation(bounceAnimation);
    }


    private class BounceAnimation extends Animation {

    }

    private int dpToPx(int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
