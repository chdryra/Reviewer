/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullDataReference<T> implements DataReference<T> {
    @Override
    public void dereference(DereferenceCallback<T> callback) {
        callback.onDereferenced(null, CallbackMessage.error("Null reference"));
    }

    @Override
    public void bindToValue(ReferenceBinder<T> binder) {

    }

    @Override
    public void unbindFromValue(ReferenceBinder<T> binder) {

    }

    @Override
    public boolean isValidReference() {
        return false;
    }

    @Override
    public void delete() {

    }
}
