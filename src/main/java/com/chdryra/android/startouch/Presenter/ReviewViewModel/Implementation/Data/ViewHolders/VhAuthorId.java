/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhAuthorId extends VhText implements DataReference.ValueSubscriber<AuthorName> {
    private DataReference<AuthorName> mReference;
    private AuthorName mAuthor;

    @Override
    public void updateView(ViewHolderData data) {
        GvAuthorId datum = (GvAuthorId) data;
        if(datum.getReference() != null) {
            if (mReference != null) mReference.unsubscribe(this);
            mReference = datum.getReference();
            mReference.subscribe(this);
            updateView(Strings.Progress.LOADING);
        }
    }

    @Override
    public void onReferenceValue(AuthorName value) {
        mAuthor = value;
        updateView(value.getName());
    }

    @Override
    public void onInvalidated(DataReference<AuthorName> reference) {
        updateView("");
    }

    @Nullable
    public AuthorName getAuthor() {
        return mAuthor;
    }
}
