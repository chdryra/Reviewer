/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullDataReference<T> implements DataReference<T> {
    @Override
    public void registerListener(InvalidationListener listener) {

    }

    @Override
    public void unregisterListener(InvalidationListener listener) {

    }

    @Override
    public void dereference(DereferenceCallback<T> callback) {
        callback.onDereferenced(new DataValue<T>());
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
    public void invalidate() {

    }

    public static class NullList<T, C extends Collection<T>, S extends Size> extends NullDataReference<C> implements CollectionReference<T, C, S> {

        @Override
        public void bindToItems(CollectionBinder<T> binder) {

        }

        @Override
        public void unbindFromItems(CollectionBinder<T> binder) {

        }

        @Override
        public DataReference<S> getSize() {
            return new NullSize<>();
        }
    }

    private static class NullSize<S extends Size> extends NullDataReference<S> implements DataReference<S> {

    }
}
