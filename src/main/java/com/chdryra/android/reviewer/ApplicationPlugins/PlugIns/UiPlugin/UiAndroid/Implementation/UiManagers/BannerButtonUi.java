/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonUi {
    private final ReviewView<?> mReviewView;
    private final Button mView;

    public BannerButtonUi(ReviewView<?> reviewView, Button view, int textColour) {
        mReviewView = reviewView;
        mView = view;
        initialise(textColour);
    }

    public void update() {
    }

    private void initialise(int textColour) {
        BannerButtonAction action = mReviewView.getActions().getBannerButtonAction();
        action.setButton(new ButtonWrapper());
        mView.setTextColor(textColour);
        mView.setOnClickListener(newClickListener(action));
        mView.setOnLongClickListener(newLongClickListener(action));

        update();
    }

    @NonNull
    private View.OnLongClickListener newLongClickListener(final BannerButtonAction action) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return action.onLongClick(v);
            }
        };
    }

    @NonNull
    private View.OnClickListener newClickListener(final BannerButtonAction action) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.onClick(v);
            }
        };
    }

    private class ButtonWrapper implements BannerButtonAction.BannerButton {
        @Override
        public void setTitle(String title) {
            mView.setText(title);
        }
    }
}
