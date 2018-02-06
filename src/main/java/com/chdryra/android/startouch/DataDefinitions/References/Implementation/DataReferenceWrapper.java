/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataReferenceWrapper<T> extends DereferencableBasic<T> {
    private final T mValue;

    public DataReferenceWrapper(T value) {
        super();
        mValue = value;
    }

    @Override
    protected void doDereferencing(final DereferenceCallback<T> callback) {
        callback.onDereferenced(new DataValue<>(mValue));
    }
}
