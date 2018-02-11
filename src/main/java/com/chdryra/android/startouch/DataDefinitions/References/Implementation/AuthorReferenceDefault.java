/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 24/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class AuthorReferenceDefault implements AuthorReference {
    private final AuthorId mAuthorId;
    private final DataReference<AuthorName> mReference;

    public AuthorReferenceDefault() {
        mAuthorId = new DatumAuthorId();
        mReference = new NullDataReference<>();
    }

    public AuthorReferenceDefault(AuthorId authorId, DataReference<AuthorName> reference) {
        mAuthorId = authorId;
        mReference = reference;
    }

    @Override
    public void dereference(DereferenceCallback<AuthorName> callback) {
        mReference.dereference(callback);
    }

    @Override
    public void bindToValue(ReferenceBinder<AuthorName> binder) {
        mReference.bindToValue(binder);
    }

    @Override
    public void unbindFromValue(ReferenceBinder<AuthorName> binder) {
        mReference.unbindFromValue(binder);
    }

    @Override
    public void registerListener(InvalidationListener listener) {
        mReference.registerListener(listener);
    }

    @Override
    public void unregisterListener(InvalidationListener listener) {
        mReference.unregisterListener(listener);
    }

    @Override
    public boolean isValidReference() {
        return mReference.isValidReference();
    }

    @Override
    public void invalidate() {
        mReference.invalidate();
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }
}
