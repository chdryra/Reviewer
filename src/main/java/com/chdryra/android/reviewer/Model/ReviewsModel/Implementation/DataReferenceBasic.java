/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DataReferenceBasic<T> implements DataReference<T> {
    protected Collection<DataReference.InvalidationListener> mListeners;
    private boolean mDeleted = false;

    public DataReferenceBasic() {
        mListeners = new ArrayList<>();
    }

    @Override
    public boolean isValidReference() {
        return !mDeleted;
    }

    @Override
    public void registerListener(DataReference.InvalidationListener listener) {
        if(mDeleted) {
            listener.onReferenceInvalidated(this);
        } else {
            if (!mListeners.contains(listener)) mListeners.add(listener);
        }
    }

    @Override
    public void unregisterListener(DataReference.InvalidationListener listener) {
        if(mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    public void invalidate() {
        if(!isDeleted()) {
            mDeleted = true;
            onInvalidate();
            for (DataReference.InvalidationListener listener : mListeners) {
                listener.onReferenceInvalidated(this);
            }
            mListeners.clear();
        }
    }

    protected boolean isDeleted() {
        return mDeleted;
    }

    protected void onInvalidate() {

    }
}
