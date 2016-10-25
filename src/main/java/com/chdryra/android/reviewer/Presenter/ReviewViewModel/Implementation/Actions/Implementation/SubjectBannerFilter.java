/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class SubjectBannerFilter<T extends GvData> extends ReviewViewActionFilter<T>
        implements BannerButtonAction<T>, SubjectAction<T> {

    private final String mButtonTitle;
    private final String mWorkingMessage;
    private final List<ClickListener> mListeners;

    private ButtonTitle mButton;
    private boolean mFiltering = false;

    public SubjectBannerFilter(String buttonTitle, String workingMessage) {
        mButtonTitle = buttonTitle;
        mWorkingMessage = workingMessage;
        mListeners = new ArrayList<>();
    }

    @Override
    public String getSubject() {
        return getReviewView().getContainerSubject();
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
    public void setTitle(ButtonTitle title) {
        mButton = title;
        mButton.update(mButtonTitle);
    }

    @Override
    public String getTitleString() {
        return mButtonTitle;
    }

    @Override
    public void onClick(View v) {
        performFiltering(getSubject());
    }

    public void performFiltering(String query) {
        if(mButton != null && !mFiltering) {
            mFiltering = true;
            mButton.update(mWorkingMessage);
            doFiltering(query);
        }
    }

    @Override
    public void onFiltered() {
        mFiltering = false;
        mButton.update(mButtonTitle);
    }

    @Override
    public void registerListener(ClickListener listener) {
        if(!mListeners.contains(listener)) mListeners.add(listener);
    }

    @Override
    public void unregisterListener(ClickListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }
}
