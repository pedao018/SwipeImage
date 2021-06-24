package com.hainguyen.myapplication.ui;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeDetectModel {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private GestureDetector gestureDetector;

    public SwipeDetectModel(Context context, SwipeListener swipeListener) {
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = moveEvent.getY() - downEvent.getY();
                    float diffX = moveEvent.getX() - downEvent.getX();
                    // which was greater?  movement across Y or X?
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        // right or left swipe
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                swipeListener.onSwipeToRight();
                            } else {
                                swipeListener.onSwipeToLeft();
                            }
                            result = true;
                        }
                    } else {
                        // up or down swipe
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                swipeListener.onSwipeToBottom();
                            } else {
                                swipeListener.onSwipeToTop();
                            }
                            result = true;
                        }
                    }
                } catch (Exception ex) {
                }
                return result;
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    interface SwipeListener {
        void onSwipeToRight();

        void onSwipeToLeft();

        void onSwipeToTop();

        void onSwipeToBottom();
    }
}
