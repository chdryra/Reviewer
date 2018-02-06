/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DataReferenceBasic<T> implements DataReference<T> {
    protected Collection<InvalidationListener> mListeners;
    private boolean mDeleted = false;

    protected DataReferenceBasic() {
        mListeners = new ArrayList<>();
    }

    @Override
    public boolean isValidReference() {
        return !mDeleted;
    }

    @Override
    public void registerListener(InvalidationListener listener) {
        if(mDeleted) {
            listener.onReferenceInvalidated(this);
        } else {
            if (!mListeners.contains(listener)) mListeners.add(listener);
        }
    }

    @Override
    public void unregisterListener(InvalidationListener listener) {
        if(mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    public void invalidate() {
        if(!isDeleted()) {
            mDeleted = true;
            for (InvalidationListener listener : mListeners) {
                listener.onReferenceInvalidated(this);
            }
            mListeners.clear();
            onInvalidate();
        }
    }

    private boolean isDeleted() {
        return mDeleted;
    }

    protected void onInvalidate() {

    }
}
