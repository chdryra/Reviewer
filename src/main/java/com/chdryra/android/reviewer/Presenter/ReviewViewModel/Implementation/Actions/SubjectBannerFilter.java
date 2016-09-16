/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class SubjectBannerFilter<T extends GvData> extends ReviewViewActionFilter<T>
        implements BannerButtonAction<T>, SubjectAction<T> {

    private String mButtonTitle;
    private String mWorkingMessage;
    private BannerButton mButton;
    private boolean mFiltering = false;

    public SubjectBannerFilter(String buttonTitle, String workingMessage) {
        mButtonTitle = buttonTitle;
        mWorkingMessage = workingMessage;
    }

    @Override
    public String getSubject() {
        return getAdapter().getSubject();
    }

    @Override
    public void onKeyboardDone(CharSequence s) {
        performFiltering(s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s) {
        performFiltering(s.toString());
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void setButton(BannerButton button) {
        mButton = button;
        mButton.setTitle(mButtonTitle);
    }

    @Override
    public void onClick(View v) {
        performFiltering(getSubject());
    }

    public void performFiltering(String query) {
        if(mButton != null && !mFiltering) {
            mFiltering = true;
            mButton.setTitle(mWorkingMessage);
            doFiltering(query);
        }
    }

    @Override
    public void onFiltered() {
        mFiltering = false;
        mButton.setTitle(mButtonTitle);
    }
}
