/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhAuthorId extends VhText implements ReferenceBinder<NamedAuthor>{
    private DataReference<NamedAuthor> mReference;

    @Override
    public void updateView(ViewHolderData data) {
        GvAuthorId datum = (GvAuthorId) data;
        if(datum.getReference() != null) {
            if (mReference != null) mReference.unbindFromValue(this);
            mReference = datum.getReference();
            mReference.bindToValue(this);
            updateView(Strings.LOADING);
        }
    }

    @Override
    public void onReferenceValue(NamedAuthor value) {
        updateView(value.getName());
    }

    @Override
    public void onInvalidated(DataReference<NamedAuthor> reference) {
        updateView("");
    }
}
