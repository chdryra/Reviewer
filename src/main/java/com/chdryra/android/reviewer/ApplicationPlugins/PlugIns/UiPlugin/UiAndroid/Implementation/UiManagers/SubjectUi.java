/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Widgets.ClearableEditText;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectUi {
    private final ReviewView<?> mReviewView;
    private final EditText mView;
    private boolean mSubjectRefresh = true;

    public SubjectUi(ReviewView<?> reviewView, EditText view) {
        mReviewView = reviewView;
        mView = view;
        initialise();
    }

    public String getSubject() {
        return mView.getText().toString().trim();
    }

    public void update() {
        if(mSubjectRefresh) mView.setText(mReviewView.getSubject());
    }

    public int getTextColour() {
        return mView.getTextColors().getDefaultColor();
    }

    private void initialise() {
        ReviewViewParams.SubjectParams params = mReviewView.getParams().getSubjectParams();
        boolean isEditable = params.isEditable();
        mSubjectRefresh = params.isUpdateOnRefresh();
        if(isEditable) mView.setHint(params.getHint());

        mView.setFocusable(isEditable);
        ((ClearableEditText) mView).makeClearable(isEditable);
        if (isEditable) {
            final SubjectAction<?> action = mReviewView.getActions().getSubjectAction();
            mView.setOnEditorActionListener(newSubjectActionListener(action));
            mView.addTextChangedListener(newSubjectChangeListener(action));
        }

        update();
    }

    @NonNull
    private TextWatcher newSubjectChangeListener(final SubjectAction<?> action) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                action.onTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
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
