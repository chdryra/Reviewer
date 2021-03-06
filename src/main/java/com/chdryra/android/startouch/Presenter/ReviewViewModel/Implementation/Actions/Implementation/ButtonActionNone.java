/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import android.view.View;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataReferenceWrapper;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ButtonActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements ButtonAction<T> {

    private final List<ClickListener> mListeners;
    private DataReferenceWrapper<String> mTitle;

    public ButtonActionNone() {
        this("");
    }

    public ButtonActionNone(String title) {
        mTitle = new DataReferenceWrapper<>(title);
        mListeners = new ArrayList<>();
    }

    protected void notifyListeners() {
        for (ClickListener listener : mListeners) {
            listener.onButtonClick();
        }
    }

    @Override
    public DataReference<String> getTitle() {
        return mTitle;
    }

    protected void setTitle(String title) {
        mTitle.setData(title);
    }

    @Override
    public void onClick(View v) {
        notifyListeners();
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void registerListener(ClickListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    @Override
    public void unregisterListener(ClickListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }
}
