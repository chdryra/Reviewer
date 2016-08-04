/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 30/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeBasic implements ReviewNode {
    protected ArrayList<NodeObserver> mObservers;
    protected Collection<InvalidationListener> mListeners;
    private boolean mDeleted = false;

    public ReviewNodeBasic() {
        mObservers = new ArrayList<>();
        mListeners = new ArrayList<>();
    }

    @Override
    public boolean isValidReference() {
        return !mDeleted;
    }

    @Override
    public void registerListener(DataReference.InvalidationListener listener) {
        if(!mListeners.contains(listener)) mListeners.add(listener);
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
        }
    }

    protected boolean isDeleted() {
        return mDeleted;
    }

    protected void onInvalidate() {

    }

    @Override
    public boolean isLeaf() {
        return getChildren().size() == 0;
    }

    @Nullable
    @Override
    public ReviewReference getReference() {
        return null;
    }

    @Override
    public void unregisterObserver(NodeObserver binder) {
        if (mObservers.contains(binder)) mObservers.remove(binder);
    }

    @Override
    public void registerObserver(NodeObserver binder) {
        if (!mObservers.contains(binder)) mObservers.add(binder);
    }

    protected void notifyOnChildAdded(ReviewNode child) {
        for (NodeObserver observer : mObservers) {
            observer.onChildAdded(child);
        }
    }

    protected void notifyOnChildRemoved(ReviewNode child) {
        for (NodeObserver observer : mObservers) {
            observer.onChildRemoved(child);
        }
    }

    protected void notifyOnNodeChanged() {
        for (NodeObserver observer : mObservers) {
            observer.onNodeChanged();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNodeBasic)) return false;

        ReviewNodeBasic that = (ReviewNodeBasic) o;

        return mObservers.equals(that.mObservers);

    }

    @Override
    public int hashCode() {
        return mObservers.hashCode();
    }
}
