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

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarLaunchSummary extends RatingBarUi {
    private ReviewNode mNode;

    public RatingBarLaunchSummary(RatingBar view, final ReviewNode node,
                                  final ReviewLauncher launcher, boolean clickable) {
        super(view, new ValueGetter<Float>() {
            @Override
            public Float getValue() {
                return node.getRating().getRating();
            }
        });

        mNode = node;
        if(clickable) {
            getView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    launcher.launchSummary(mNode.getReviewId());
                    return false;
                }
            });
        }
    }
}
