package com.jackandphantom.demon;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.GestureDetector;

public class SwipeImageView extends AppCompatImageView implements GestureDetector.OnGestureListener {


    public SwipeImageView(Context context) {
        super(context);
    }

    public SwipeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeImageView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
