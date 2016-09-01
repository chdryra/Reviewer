/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataReference<T> {
    interface DereferenceCallback<T> {
        void onDereferenced(DataValue<T> value);
    }

    interface InvalidationListener {
        void onReferenceInvalidated(DataReference<?> reference);
    }

    void dereference(DereferenceCallback<T> callback);

    void bindToValue(ReferenceBinder<T> binder);

    void unbindFromValue(ReferenceBinder<T> binder);

    void registerListener(InvalidationListener listener);

    void unregisterListener(InvalidationListener listener);

    boolean isValidReference();

    void invalidate();
}
