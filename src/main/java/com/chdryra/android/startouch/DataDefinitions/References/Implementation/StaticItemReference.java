/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticItemReference<T extends HasReviewId> extends SimpleItemReference<T> {
    private final T mValue;

    public StaticItemReference(final T value) {
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
