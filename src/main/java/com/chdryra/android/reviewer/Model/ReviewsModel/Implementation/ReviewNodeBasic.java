/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeBasic extends MetaReferenceBasic implements ReviewNode {
    protected ArrayList<NodeObserver> mObservers;

    public ReviewNodeBasic(BindersManagerMeta binders) {
        super(binders);
        mObservers = new ArrayList<>();
    }

    @Override
    public boolean isLeaf() {
        return getChildren().size() == 0 && isValidReference();
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
}
