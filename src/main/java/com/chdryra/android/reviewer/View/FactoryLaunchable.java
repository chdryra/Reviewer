/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.View;

import android.util.Log;

import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLaunchable {
    private static final String TAG = "FactoryLaunchable";
    private static FactoryLaunchable sFactory;

    private FactoryLaunchable() {
    }

    private static FactoryLaunchable getFactory() {
        if (sFactory == null) sFactory = new FactoryLaunchable();
        return sFactory;
    }

    public static LaunchableUi newLaunchable(Class<? extends LaunchableUi> uiClass) throws
            RuntimeException {
        if (uiClass == null) return null;

        try {
            return uiClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "Couldn't create UI for " + uiClass.getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "IllegalAccessException: trying to create " + uiClass.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static LaunchableUi newLaunchable(ReviewViewAdapter adapter,
            ReviewViewParams params) {
        return getFactory().new LaunchableReviewView(adapter, params);
    }

    public static LaunchableUi newLaunchable(ReviewViewAdapter adapter) {
        return getFactory().new LaunchableReviewView(adapter, new ReviewViewParams());
    }

    private class LaunchableReviewView extends ActivityReviewView implements LaunchableUi {
        private ReviewView mView;

        private LaunchableReviewView(ReviewViewAdapter adapter, ReviewViewParams
                params) {
            mView = new ReviewView(adapter, params);
        }

        @Override
        public String getLaunchTag() {
            return mView.getLaunchTag();
        }

        @Override
        public void launch(LauncherUi launcher) {
            mView.launch(launcher);
        }
    }
}
