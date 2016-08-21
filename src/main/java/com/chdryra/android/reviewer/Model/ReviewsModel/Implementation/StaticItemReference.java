/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticItemReference<T extends HasReviewId> extends SimpleItemReference<T> implements ReviewItemReference<T> {
    private T mValue;

    public StaticItemReference(final T value) {
        super(new Dereferencer<T>() {
            @Override
            public ReviewId getReviewId() {
                return value.getReviewId();
            }

            @Override
            public void dereference(DereferenceCallback<T> callback) {
                callback.onDereferenced(value, CallbackMessage.ok());
            }
        });

        mValue = value;
    }

    protected T getData() {
        return mValue;
    }
}
