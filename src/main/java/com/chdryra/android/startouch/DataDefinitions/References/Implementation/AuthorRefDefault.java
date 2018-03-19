/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.NullDataReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorRef;

/**
 * Created by: Rizwan Choudrey
 * On: 24/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class AuthorRefDefault implements AuthorRef {
    private final AuthorId mAuthorId;
    private final DataReference<AuthorName> mReference;

    public AuthorRefDefault() {
        mAuthorId = new DatumAuthorId();
        mReference = new NullDataReference<>();
    }

    public AuthorRefDefault(AuthorId authorId, DataReference<AuthorName> reference) {
        mAuthorId = authorId;
        mReference = reference;
    }

    @Override
    public void dereference(DereferenceCallback<AuthorName> callback) {
        mReference.dereference(callback);
    }

    @Override
    public void subscribe(ValueSubscriber<AuthorName> subscriber) {
        mReference.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(ValueSubscriber<AuthorName> subscriber) {
        mReference.unsubscribe(subscriber);
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
