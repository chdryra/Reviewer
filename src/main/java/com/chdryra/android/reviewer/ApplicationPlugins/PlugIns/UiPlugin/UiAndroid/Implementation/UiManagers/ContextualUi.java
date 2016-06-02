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
import android.widget.LinearLayout;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
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

    public ContextualUi(ReviewView reviewView, LinearLayout view, int buttonId, int textColour) {
        mReviewView = reviewView;
        mView = view;
        mButton = (Button) mView.findViewById(buttonId);
        initialise(textColour);
    }

    private void initialise(int textColour) {
        ContextualButtonAction<?> action = mReviewView.getActions().getContextualAction();
        if (action == null) {
            mView.setVisibility(View.GONE);
            return;
        }

        mButton.setText(action.getButtonTitle());
        mButton.setTextColor(textColour);
        mButton.setOnClickListener(newClickListener(action));
        mButton.setOnLongClickListener(newLongClickListener(action));

        update();
    }

    public void update() {
    }

    @NonNull
    private View.OnLongClickListener newLongClickListener(final ContextualButtonAction<?> action) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return action.onLongClick(v);
            }
        };
    }

    @NonNull
    private View.OnClickListener newClickListener(final ContextualButtonAction<?> action) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.onClick(v);
            }
        };
    }
}
