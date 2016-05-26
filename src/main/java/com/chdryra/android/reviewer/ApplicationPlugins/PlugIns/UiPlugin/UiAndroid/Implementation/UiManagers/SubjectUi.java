/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Widgets.ClearableEditText;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectUi {
    private ReviewView<?> mReviewView;
    private TextView mView;

    public SubjectUi(ReviewView<?> reviewView, TextView view) {
        mReviewView = reviewView;
        mView = view;
        initialise();
    }

    public String getSubject() {
        return mView.getText().toString().trim();
    }

    public void update() {
        mView.setText(mReviewView.getSubject());
    }

    public int getTextColour() {
        return mView.getTextColors().getDefaultColor();
    }

    private void initialise() {
        if (!mReviewView.getParams().isSubjectVisible()) {
            mView.setVisibility(View.GONE);
            return;
        }

        boolean isEditable = mReviewView.isEditable();
        mView.setFocusable(isEditable);
        ((ClearableEditText) mView).makeClearable(isEditable);
        if (isEditable) {
            SubjectAction<?> action = mReviewView.getActions().getSubjectAction();
            mView.setOnEditorActionListener(newSubjectActionListener(action));
        }

        update();
    }

    @NonNull
    private TextView.OnEditorActionListener newSubjectActionListener(final SubjectAction<?>
                                                                                 action) {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    action.onKeyboardDone(v.getText());
                    return true;
                }
                return false;
            }
        };
    }
}
