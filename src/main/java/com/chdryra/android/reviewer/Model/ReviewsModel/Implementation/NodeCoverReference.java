/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataReferenceBasic;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
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
        ReviewItemReference<DataImage>, ReviewNode.NodeObserver, DataReference.InvalidationListener {
    private final Random RANDOM = new Random();
    private final ReviewNode mRoot;
    private final ArrayList<ReferenceBinder<DataImage>> mBinders;
    private ReviewItemReference<ReviewReference> mReview;

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
        if (mReview == null || !mReview.isValidReference()) {
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
            public void onDereferenced(DataValue<DataImage> value) {
                if(value.hasValue()) binder.onReferenceValue(value.getData());
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
        RefDataList<ReviewReference> reviews = mRoot.getReviews();
        reviews.toItemReferences(new ReviewListReference.ItemReferencesCallback<ReviewReference, ReviewItemReference<ReviewReference>>() {
            @Override
            public void onItemReferences(IdableList<ReviewItemReference<ReviewReference>> references) {
                ReviewItemReference<ReviewReference> review
                        = references.getItem(RANDOM.nextInt(references.size()));
                boolean changed = false;
                if(review.isValidReference()) {
                    changed = mReview != review;
                    if(changed) setReview(review);
                }
                callback.onChosen(changed);
            }
        });
    }

    private void setReview(ReviewItemReference<ReviewReference> review) {
        if(mReview != null) mReview.unregisterListener(NodeCoverReference.this);
        mReview = review;
        mReview.registerListener(NodeCoverReference.this);
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        mReview = null;
        chooseAgainAndNotifyIfNecessary();
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
        if(mReview != null && mReview.isValidReference()) {
            mReview.dereference(new DereferenceCallback<ReviewReference>() {
                @Override
                public void onDereferenced(DataValue<ReviewReference> value) {
                    if (value.hasValue()) value.getData().getCover().dereference(callback);
                }
            });
        } else {
            callback.onDereferenced(new DataValue<DataImage>(CallbackMessage.error("No cover")));
        }
    }

    private void notifyBinders() {
        dereferenceCurrent(new DereferenceCallback<DataImage>() {
            @Override
            public void onDereferenced(DataValue<DataImage> value) {
                if (value.hasValue()) notifyBinders(value.getData());
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
        if(mReview != null) mReview.unregisterListener(this);
        for(ReferenceBinder<DataImage> binder : mBinders) {
            binder.onInvalidated(this);
        }
        mBinders.clear();
    }
}
