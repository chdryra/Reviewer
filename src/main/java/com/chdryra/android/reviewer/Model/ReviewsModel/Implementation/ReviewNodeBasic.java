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

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeBasic extends ReviewReferenceBasic implements ReviewNode {
    private ArrayList<NodeBinder> mObservers;

    public ReviewNodeBasic(BindersManager bindersManager) {
        super(bindersManager);
        mObservers = new ArrayList<>();
    }

    @Override
    public void bindToNode(NodeBinder binder) {
        if(!mObservers.contains(binder)) mObservers.add(binder);
    }

    @Override
    public void unbindFromNode(NodeBinder binder) {
        if(mObservers.contains(binder)) mObservers.remove(binder);
    }

    protected void notifyOnChildAdded(ReviewNode child) {
        for (NodeBinder observer : mObservers) {
            observer.onChildAdded(child);
        }
    }

    protected void notifyOnChildRemoved(ReviewNode child) {
        for (NodeBinder observer : mObservers) {
            observer.onChildRemoved(child);
        }
    }

    protected void notifyOnParentChanged(@Nullable ReviewNode oldParent, @Nullable ReviewNode newParent) {
        for (NodeBinder observer : mObservers) {
            observer.onParentChanged(oldParent, newParent);
        }
    }

    protected void notifyOnNodeChanged() {
        for (NodeBinder observer : mObservers) {
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
