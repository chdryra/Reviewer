/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 24/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class AuthorReferenceWrapper implements AuthorReference {
    private final AuthorId mAuthorId;
    private final DataReference<NamedAuthor> mReference;

    public AuthorReferenceWrapper(AuthorId authorId, DataReference<NamedAuthor> reference) {
        mAuthorId = authorId;
        mReference = reference;
    }

    @Override
    public void dereference(DereferenceCallback<NamedAuthor> callback) {
        mReference.dereference(callback);
    }

    @Override
    public void bindToValue(ReferenceBinder<NamedAuthor> binder) {
        mReference.bindToValue(binder);
    }

    @Override
    public void unbindFromValue(ReferenceBinder<NamedAuthor> binder) {
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
