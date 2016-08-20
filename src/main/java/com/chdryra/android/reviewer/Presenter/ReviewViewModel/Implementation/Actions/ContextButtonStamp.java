/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ContextButtonStamp<T extends GvData> extends ReviewViewActionBasic<T>
        implements ContextualButtonAction<T>, DataReference.DereferenceCallback<NamedAuthor> {
    private ApplicationInstance mApp;
    private AuthorId mAuthorId;
    private String mDate;
    private String mName;

    public ContextButtonStamp(ApplicationInstance app, ReviewStamp stamp) {
        mApp = app;
        mAuthorId = stamp.getAuthorId();
        mDate = stamp.toReadableDate();
        mApp.getUsersManager().getAuthorsRepository().getName(mAuthorId).dereference(this);
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void onClick(View v) {
        mApp.launchFeed(mAuthorId);
    }

    @Override
    public String getButtonTitle() {
        return mName != null ? mName + " " + mDate : mDate;
    }

    @Override
    public void onDereferenced(@Nullable NamedAuthor data, CallbackMessage message) {
        if(data != null && !message.isError()) {
            mName = data.getName();
            if(isAttached()) getReviewView().updateContextButton();
        }
    }
}
