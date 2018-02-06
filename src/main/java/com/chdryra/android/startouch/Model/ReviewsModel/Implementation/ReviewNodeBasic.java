/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeBasic implements ReviewNode {
    private final ArrayList<NodeObserver> mObservers;

    //to deal with concurrent modification
    private final ArrayList<NodeObserver> mToRemove;
    private final ArrayList<NodeObserver> mToAdd;
    private boolean mLockObservers = false;

    ReviewNodeBasic() {
        mObservers = new ArrayList<>();
        mToRemove = new ArrayList<>();
        mToAdd = new ArrayList<>();
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
    public void unregisterObserver(NodeObserver observer) {
        if (mObservers.contains(observer)) {
            if (!mLockObservers) {
                mObservers.remove(observer);
            } else {
                mToRemove.add(observer);
            }
        }
    }

    @Override
    public void registerObserver(NodeObserver observer) {
        if (!mObservers.contains(observer)) {
            if (!mLockObservers) {
                mObservers.add(observer);
            } else {
                mToAdd.add(observer);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNodeBasic)) return false;

        ReviewNodeBasic that = (ReviewNodeBasic) o;

        if (mLockObservers != that.mLockObservers) return false;
        if (!mObservers.equals(that.mObservers)) return false;
        if (!mToRemove.equals(that.mToRemove)) return false;
        return mToAdd.equals(that.mToAdd);

    }

    @Override
    public int hashCode() {
        int result = mObservers.hashCode();
        result = 31 * result + mToRemove.hashCode();
        result = 31 * result + mToAdd.hashCode();
        result = 31 * result + (mLockObservers ? 1 : 0);
        return result;
    }

    void notifyOnChildAdded(ReviewNode child) {
        lock();
        for (NodeObserver observer : mObservers) {
            observer.onChildAdded(child);
        }
        unlock();
    }

    void notifyOnChildRemoved(ReviewNode child) {
        lock();
        for (NodeObserver observer : mObservers) {
            observer.onChildRemoved(child);
        }
        unlock();
    }

    void notifyOnNodeChanged() {
        lock();
        for (NodeObserver observer : mObservers) {
            observer.onNodeChanged();
        }
        unlock();
    }

    void notifyOnTreeChanged() {
        lock();
        for (NodeObserver observer : mObservers) {
            observer.onTreeChanged();
        }
        unlock();
    }

    private void rationaliseObservers() {
        for (NodeObserver observer : mToAdd) {
            registerObserver(observer);
        }
        for (NodeObserver observer : mToRemove) {
            unregisterObserver(observer);
        }
        mToAdd.clear();
        mToRemove.clear();
    }

    private void lock() {
        mLockObservers = true;
    }

    private void unlock() {
        mLockObservers = false;
        rationaliseObservers();
    }
}
