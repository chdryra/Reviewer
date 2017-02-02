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
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SimpleItemReference<T extends HasReviewId> extends DereferencableBasic<T> implements
        ReviewItemReference<T> {
    private final Dereferencer<T> mDereferencer;

    public interface Dereferencer<T extends HasReviewId> {
        ReviewId getReviewId();

        void dereference(DereferenceCallback<T> callback);
    }

    public SimpleItemReference(Dereferencer<T> dereferencer) {
        super();
        mDereferencer = dereferencer;
    }

    protected Dereferencer<T> getDereferencer() {
        return mDereferencer;
    }


    @Override
    public ReviewId getReviewId() {
        return mDereferencer.getReviewId();
    }

    @Override
    protected void doDereferencing(final DereferenceCallback<T> callback) {
        mDereferencer.dereference(new DereferenceCallback<T>() {
            @Override
            public void onDereferenced(DataValue<T> value) {
                if (value.hasValue()) {
                    callback.onDereferenced(value);
                } else {
                    invalidate();
                    invalidReference(callback);
                }
            }
        });
    }
}
