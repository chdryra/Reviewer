/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.app.Fragment;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ActivityReviewView extends ActivitySingleFragment {
    private FragmentReviewView mFragment;
    private ReviewView mView;

    protected abstract ReviewView createReviewView();

    public ReviewView getReviewView() {
        return mView;
    }

    public FragmentReviewView getFragment() {
        return mFragment;
    }

    @Override
    protected Fragment createFragment() {
        mView = createReviewView();
        mFragment = new FragmentReviewView();
        return mFragment;
    }

}
