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
import android.view.View;
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
public class SubjectUi extends TextUi<EditText> {
    private boolean mSubjectRefresh = true;
    private String mCurrentText;
    private SubjectAction<?> mSubjectAction;

    public SubjectUi(final ReviewView<?> reviewView, EditText view) {
        super(view, new ValueGetter<String>() {
            @Override
            public String getValue() {
                return reviewView.getSubject();
            }
        });
        initialise(reviewView);
    }

    @Override
    public void update() {
        if(mSubjectRefresh) super.update();
    }

    public void update(boolean force) {
        if(force) {
            super.update();
        } else {
            update();
        }
    }

    private void initialise(ReviewView<?> reviewView) {
        EditText mEditText = getView();

        mCurrentText = reviewView.getSubject();
        mEditText.setText(mCurrentText);
        ReviewViewParams.SubjectParams params = reviewView.getParams().getSubjectParams();
        boolean isEditable = params.isEditable();
        mSubjectRefresh = !isEditable && params.isUpdateOnRefresh();
        if(isEditable) mEditText.setHint(params.getHint());

        mEditText.setFocusable(isEditable);
        ((ClearableEditText) mEditText).makeClearable(isEditable);
        if (isEditable) {
            mSubjectAction = reviewView.getActions().getSubjectAction();
            mEditText.setOnEditorActionListener(newSubjectActionListener());
            mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if(!hasFocus && getView().getText().length() > 0) setSubject();
                }
            });
            mEditText.addTextChangedListener(newSubjectChangeListener());
        }

        update();
    }

    @NonNull
    private TextWatcher newSubjectChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSubjectAction.onTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @NonNull
    private TextView.OnEditorActionListener newSubjectActionListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    setSubject();
                    return true;
                }
                return false;
            }
        };
    }

    private void setSubject() {
        String newText = getView().getText().toString();
        if(!mCurrentText.equals(newText)) {
            mCurrentText = newText;
            mSubjectAction.onKeyboardDone(newText);
        }
    }
}
