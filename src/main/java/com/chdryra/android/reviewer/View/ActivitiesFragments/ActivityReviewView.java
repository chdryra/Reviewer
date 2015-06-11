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

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityReviewView extends ActivitySingleFragment {
//    public static void startNewActivity(Activity activity, ReviewView screen) {
//        if (activity == null) return;
//
//        Intent i = new Intent(activity, ActivityReviewView.class);
//        Administrator.get(activity).packView(screen, i);
//
//        activity.startActivity(i);
//    }

    protected Fragment createFragment() {
        return new FragmentReviewView();
    }

}
