/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.widget.Button;
import android.widget.LinearLayout;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ContextualUi {
    private ReviewView<?> mReviewView;
    private LinearLayout mView;
    private Button mButton;

    public ContextualUi(ReviewView reviewView, LinearLayout view, int buttonId) {
        mReviewView = reviewView;
        mView = view;
        mButton = (Button) mView.findViewById(buttonId);
    }
}
