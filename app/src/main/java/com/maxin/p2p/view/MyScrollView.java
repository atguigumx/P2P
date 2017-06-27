package com.maxin.p2p.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by shkstart on 2017/6/23.
 */

public class MyScrollView extends ScrollView {

    private int lastY;
    private View childView;
    //用来标记动画是否执行完成
    private boolean isAnStart = false;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    final Rect rect = new Rect();

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int eventY = (int) ev.getY();
        if(isAnStart&&getChildCount()==0) {
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = eventY;
                rect.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = eventY - lastY;
                /*if(rect.isEmpty()) {
                    rect.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                }*/
                childView.layout(childView.getLeft(), childView.getTop() + dy, childView.getRight(),
                        childView.getBottom() + dy);
                lastY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                int translationY = childView.getTop() - rect.top;
                TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -translationY);
                ta.setDuration(200);
                ta.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        isAnStart=true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isAnStart=false;
                        childView.layout(rect.left,rect.top,rect.right,rect.bottom);

                        rect.setEmpty();
                        childView.clearAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
                childView.startAnimation(ta);
                break;


        }
        return true;
    }
    private int lastX;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isOnIntercept = false;
        int eventy = (int) ev.getY();
        int eventx = (int) ev.getX();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = eventy;
                lastX = eventx;
                break;
            case MotionEvent.ACTION_MOVE:
                //y轴的偏移量
                int dy = Math.abs(eventy - lastY);
                //x轴的偏移量
                int dx = Math.abs(eventx - lastX);
                //如果y轴的偏移量大于x轴的偏移量就拦截
                if (dy > dx && dy > 20){
                    isOnIntercept = true;
                }
                lastX = eventx;
                lastY = eventy;
                break;
        }
        return isOnIntercept;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }
}
