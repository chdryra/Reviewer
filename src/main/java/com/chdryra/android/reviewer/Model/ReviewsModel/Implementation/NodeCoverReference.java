/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 20/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeCoverReference extends DataReferenceBasic<DataImage> implements
        ReviewItemReference<DataImage>, ReviewNode.NodeObserver {
    private Random RANDOM = new Random();
    private ReviewNode mRoot;
    private ArrayList<ReferenceBinder<DataImage>> mBinders;
    private ReviewItemReference<ReviewReference> mCurrent;

    private interface ChoiceCallback {
        void onChosen(boolean changed);
    }

    public NodeCoverReference(ReviewNode root) {
        mRoot = root;
        mBinders = new ArrayList<>();
    }

    @Override
    public ReviewId getReviewId() {
        return mRoot.getReviewId();
    }

    @Override
    public void dereference(final DereferenceCallback<DataImage> callback) {
        if (mCurrent == null) {
            chooseReviewForCover(new ChoiceCallback() {
                @Override
                public void onChosen(boolean changed) {
                    dereferenceCurrent(callback);
                }
            });
        } else {
            dereferenceCurrent(callback);
        }
    }

    @Override
    public void bindToValue(final ReferenceBinder<DataImage> binder) {
        if (!mBinders.contains(binder)) mBinders.add(binder);
        dereferenceCurrent(new DereferenceCallback<DataImage>() {
            @Override
            public void onDereferenced(@Nullable DataImage data, CallbackMessage message) {
                if(data != null && !message.isError()) binder.onReferenceValue(data);
            }
        });
    }

    @Override
    public void unbindFromValue(ReferenceBinder<DataImage> binder) {
        if (mBinders.contains(binder)) mBinders.remove(binder);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        chooseAgainAndNotifyIfNecessary();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        chooseAgainAndNotifyIfNecessary();
    }

    @Override
    public void onNodeChanged() {

    }

    @Override
    public void onDescendantsChanged() {
        chooseAgainAndNotifyIfNecessary();
    }

    private void chooseReviewForCover(final ChoiceCallback callback) {
        ReviewListReference<ReviewReference> reviews = mRoot.getReviews();
        reviews.toItemReferences(new ReviewListReference.ItemReferencesCallback<ReviewReference>() {
            @Override
            public void onItemReferences(IdableList<ReviewItemReference<ReviewReference>>
                                                 references) {
                ReviewItemReference<ReviewReference> item
                        = references.getItem(RANDOM.nextInt(references.size()));
                boolean changed = mCurrent != item;
                mCurrent = item;
                callback.onChosen(changed);
            }
        });
    }

    private void chooseAgainAndNotifyIfNecessary() {
        chooseReviewForCover(new ChoiceCallback() {
            @Override
            public void onChosen(boolean changed) {
                if (changed) notifyBinders();
            }
        });
    }

    private void dereferenceCurrent(final DereferenceCallback<DataImage> callback) {
        if(mCurrent != null) {
            mCurrent.dereference(new DereferenceCallback<ReviewReference>() {
                @Override
                public void onDereferenced(@Nullable ReviewReference data, CallbackMessage message) {
                    if (data != null && !message.isError()) data.getCover().dereference(callback);
                }
            });
        } else {
            callback.onDereferenced(null, CallbackMessage.ok());
        }
    }

    private void notifyBinders() {
        dereferenceCurrent(new DereferenceCallback<DataImage>() {
            @Override
            public void onDereferenced(@Nullable DataImage data, CallbackMessage message) {
                if (data != null && !message.isError()) notifyBinders(data);
            }
        });
    }

    private void notifyBinders(DataImage data) {
        for (ReferenceBinder<DataImage> binder : mBinders) {
            binder.onReferenceValue(data);
        }
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        for(ReferenceBinder<DataImage> binder : mBinders) {
            binder.onInvalidated(this);
        }
        mBinders.clear();
    }
}
