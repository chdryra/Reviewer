/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.View;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataReferenceWrapper;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class SubjectFilter<T extends GvData> extends ReviewViewActionFilter<T>
        implements ButtonAction<T>, SubjectAction<T> {

    private final String mTitle;
    private final DataReferenceWrapper<String> mTitleReference;
    private final String mWorkingMessage;
    private final List<ClickListener> mListeners;

    private boolean mFiltering = false;

    public SubjectFilter(String title, String workingMessage) {
        mTitle = title;
        mTitleReference = new DataReferenceWrapper<>(mTitle);
        mWorkingMessage = workingMessage;
        mListeners = new ArrayList<>();
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
    public DataReference<String> getTitle() {
        return mTitleReference;
    }

    @Override
    public void onClick(View v) {
        performFiltering(getReviewView().getContainerSubject());
    }

    public void performFiltering(String query) {
        if(!mFiltering) {
            mFiltering = true;
            mTitleReference.setData(mWorkingMessage);
            doFiltering(query);
        }
    }

    @Override
    public void onFiltered() {
        mFiltering = false;
        mTitleReference.setData(mTitle);
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
