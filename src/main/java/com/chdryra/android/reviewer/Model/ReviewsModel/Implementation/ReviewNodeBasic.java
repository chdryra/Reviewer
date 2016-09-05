/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeBasic implements ReviewNode {
    private final ArrayList<NodeObserver> mObservers;

    ReviewNodeBasic() {
        mObservers = new ArrayList<>();
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

    void notifyOnChildAdded(ReviewNode child) {
        for (NodeObserver observer : mObservers) {
            observer.onChildAdded(child);
        }
    }

    void notifyOnChildRemoved(ReviewNode child) {
        for (NodeObserver observer : mObservers) {
            observer.onChildRemoved(child);
        }
    }

    void notifyOnNodeChanged() {
        for (NodeObserver observer : mObservers) {
            observer.onNodeChanged();
        }
    }

    void notifyOnDescendantsChanged() {
        for (NodeObserver observer : mObservers) {
            observer.onDescendantsChanged();
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
