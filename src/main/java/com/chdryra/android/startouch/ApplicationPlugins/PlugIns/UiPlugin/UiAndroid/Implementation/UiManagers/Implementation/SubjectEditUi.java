/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chdryra.android.corelibrary.Widgets.ClearableEditText;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectEditUi extends SubjectViewUi<EditText> {
    private SubjectAction<?> mSubjectAction;
    private CharSequence mHint;

    public SubjectEditUi(final ReviewView<?> reviewView, EditText view) {
        super(view, reviewView.getParams().getSubjectParams());
//        new ReferenceValueGetter<String>() {
//            @Override
//            public String getValue() {
//                return reviewView.getSubject();
//            }
//        });
        initialise(reviewView);
    }

    private void initialise(ReviewView<?> reviewView) {
        final EditText editText = getView();
        mHint = editText.getHint();
        setViewValue(reviewView.getSubject());

        final ReviewViewParams.Subject params = getParams();
        boolean isEditable = params.isEditable();
        setSubjectRefresh(!isEditable && params.isUpdateOnRefresh());
        editText.setFocusable(isEditable);
        ((ClearableEditText) editText).makeClearable(isEditable);

        if (isEditable) {
            editText.setHint(params.getHint());
            mSubjectAction = reviewView.getActions().getSubjectAction();
            editText.setOnEditorActionListener(newSubjectActionListener());

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if(!hasFocus && getView().getText().length() > 0) setSubject();
                }
            });

            editText.addTextChangedListener(newSubjectChangeListener());
        }
    }

    private void setSubject() {
        String newText = getView().getText().toString();
        if(!getTextCache().equals(newText)) {
            updateTextCache();
            mSubjectAction.onKeyboardDone(newText);
        }
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

}
