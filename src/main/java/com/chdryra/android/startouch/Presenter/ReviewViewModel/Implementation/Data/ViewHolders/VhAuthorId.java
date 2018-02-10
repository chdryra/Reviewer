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
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhAuthorId extends VhText implements ReferenceBinder<NamedAuthor>{
    private DataReference<NamedAuthor> mReference;
    private NamedAuthor mAuthor;

    @Override
    public void updateView(ViewHolderData data) {
        GvAuthorId datum = (GvAuthorId) data;
        if(datum.getReference() != null) {
            if (mReference != null) mReference.unbindFromValue(this);
            mReference = datum.getReference();
            mReference.bindToValue(this);
            updateView(Strings.Progress.LOADING);
        }
    }

    @Override
    public void onReferenceValue(NamedAuthor value) {
        mAuthor = value;
        updateView(value.getName());
    }

    @Override
    public void onInvalidated(DataReference<NamedAuthor> reference) {
        updateView("");
    }

    @Nullable
    public NamedAuthor getAuthor() {
        return mAuthor;
    }
}
