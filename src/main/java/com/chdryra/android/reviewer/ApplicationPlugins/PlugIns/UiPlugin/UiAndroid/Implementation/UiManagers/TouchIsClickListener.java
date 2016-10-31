/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 31/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TouchIsClickListener implements RecyclerView.OnItemTouchListener {
    private static final float CLICK_THRESHOLD = 10;

    private final Command mCommand;

    private float mStartX = 0f;
    private float mStartY = 0f;

    public TouchIsClickListener(Command command) {
        mCommand = command;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = e.getX();
                mStartY = e.getY();
                break;
            case MotionEvent.ACTION_UP: {
                float endX = e.getX();
                float endY = e.getY();
                if (isAClick(mStartX, endX, mStartY, endY)) mCommand.execute();
                break;
            }
        }
        return false;

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        return !(Math.abs(startX - endX) > CLICK_THRESHOLD
                || Math.abs(startY - endY) > CLICK_THRESHOLD);
    }
}
