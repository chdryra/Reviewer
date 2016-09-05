/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperItemReference<T extends HasReviewId> extends SimpleItemReference<T> implements ReviewItemReference<T> {
    private final T mValue;

    public WrapperItemReference(final T value) {
        super(new Dereferencer<T>() {
            @Override
            public ReviewId getReviewId() {
                return value.getReviewId();
            }

            @Override
            public void dereference(DereferenceCallback<T> callback) {
                callback.onDereferenced(new DataValue<>(value));
            }
        });

        mValue = value;
    }

    T getData() {
        return mValue;
    }
}
