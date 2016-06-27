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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeBasic extends ReviewReferenceBasic implements ReviewNode {
    private ReviewNodeComponent mParent;
    private ArrayList<NodeObserver> mObservers;

    public ReviewNodeBasic(BindersManager bindersManager) {
        super(bindersManager);
        mObservers = new ArrayList<>();
    }

    @Nullable
    @Override
    public ReviewNode getParent() {
        return mParent;
    }


    @Override
    public ReviewNode getRoot() {
        return mParent != null ? mParent.getRoot() : this;
    }

    @Override
    public void registerNodeObserver(NodeObserver observer) {
        if(!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterNodeObserver(NodeObserver observer) {
        if(mObservers.contains(observer)) mObservers.remove(observer);
    }

    protected void notifyNodeObservers() {
        for (NodeObserver observer : mObservers) {
            observer.onNodeChanged();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNodeBasic)) return false;

        ReviewNodeBasic that = (ReviewNodeBasic) o;

        if (mParent != null ? !mParent.equals(that.mParent) : that.mParent != null) return false;
        return mObservers.equals(that.mObservers);

    }

    @Override
    public int hashCode() {
        int result = mParent != null ? mParent.hashCode() : 0;
        result = 31 * result + mObservers.hashCode();
        return result;
    }
}
