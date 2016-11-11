/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarSummaryUi extends RatingBarUi {
    public RatingBarSummaryUi(RatingBar view, ValueGetter<Float> rating, final ReviewId reviewId,
                              final ReviewLauncher launcher, boolean clickable) {
        super(view, rating);
        if(clickable) {
            getView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    launcher.launchSummary(reviewId);
                    return false;
                }
            });
        }
    }
}
