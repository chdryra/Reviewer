/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.InvalidatableReferenceBasic;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by: Rizwan Choudrey
 * On: 20/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeCoverReference extends InvalidatableReferenceBasic<DataImage> implements
        ReviewItemReference<DataImage>, ReviewNode.NodeObserver, DataReference
        .InvalidationListener {
    private final ReviewNode mRoot;
    private final ArrayList<ValueSubscriber<DataImage>> mBinders;
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
        dereferenceCurrent(callback);
    }

    @Override
    public void subscribe(final ValueSubscriber<DataImage> subscriber) {
        if (!mBinders.contains(subscriber)) mBinders.add(subscriber);
        dereferenceCurrent(new DereferenceCallback<DataImage>() {
            @Override
            public void onDereferenced(DataValue<DataImage> value) {
                if (value.hasValue()) subscriber.onReferenceValue(value.getData());
            }
        });
    }

    @Override
    public void unsubscribe(ValueSubscriber<DataImage> subscriber) {
        if (mBinders.contains(subscriber)) mBinders.remove(subscriber);
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
        chooseAgainAndNotifyIfNecessary();
    }

    @Override
    public void onTreeChanged() {
        chooseAgainAndNotifyIfNecessary();
    }

    @Override
    public void onInvalidated(DataReference<?> reference) {
        mReview = null;
        chooseAgainAndNotifyIfNecessary();
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        if (mReview != null) mReview.unregisterListener(this);
        for (ValueSubscriber<DataImage> binder : mBinders) {
            binder.onInvalidated(this);
        }
        mBinders.clear();
    }

    private void findReviewWithCover(final ChoiceCallback callback) {
        DataListRef<ReviewReference> reviews = mRoot.getReviews();
        reviews.toItemReferences(new ReviewListReference.ItemReferencesCallback<ReviewReference,
                ReviewItemReference<ReviewReference>>() {
            @Override
            public void onItemReferences(IdableList<ReviewItemReference<ReviewReference>>
                                                 references) {
                int size = references.size();
                if (size > 0) {
                    findReviewWithCover(shuffle(references), 0, callback);
                } else {
                    callback.onChosen(false);
                }
            }
        });
    }

    @NonNull
    private ArrayList<ReviewItemReference<ReviewReference>> shuffle
            (IdableList<ReviewItemReference<ReviewReference>> references) {
        ArrayList<ReviewItemReference<ReviewReference>> list
                = new ArrayList<>(references);
        Collections.shuffle(list);
        return list;
    }

    private void findReviewWithCover(final ArrayList<ReviewItemReference<ReviewReference>> list,
                                     final int index, final ChoiceCallback callback) {
        if (index >= list.size()) {
            callback.onChosen(false);
            return;
        }

        final ReviewItemReference<ReviewReference> review = list.get(index);
        final int nextIndex = index + 1;
        if (review.isValidReference()) {
            review.dereference(new DereferenceCallback<ReviewReference>() {
                @Override
                public void onDereferenced(DataValue<ReviewReference> value) {
                    if (value.hasValue()) {
                        checkSize(value.getData().getImages().getSize(),
                                review, list, nextIndex, callback);
                    } else {
                        findReviewWithCover(list, nextIndex, callback);
                    }
                }
            });
        } else {
            findReviewWithCover(list, nextIndex, callback);
        }
    }

    private void checkSize(ReviewItemReference<DataSize> size,
                           final ReviewItemReference<ReviewReference> review,
                           final ArrayList<ReviewItemReference<ReviewReference>> list,
                           final int nextIndex, final ChoiceCallback callback) {
        size.dereference(new DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(DataValue<DataSize> value) {
                if (value.hasValue() && value.getData().getSize() > 0) {
                    ReviewItemReference<ReviewReference> old = mReview;
                    setReview(review);
                    callback.onChosen(old == null
                            || !old.getReviewId().equals(review.getReviewId()));
                } else {
                    findReviewWithCover(list, nextIndex, callback);
                }
            }
        });
    }

    private void setReview(ReviewItemReference<ReviewReference> review) {
        if (mReview != null) mReview.unregisterListener(NodeCoverReference.this);
        mReview = review;
        mReview.registerListener(NodeCoverReference.this);
    }

    private void chooseAgainAndNotifyIfNecessary() {
        findReviewWithCover(new ChoiceCallback() {
            @Override
            public void onChosen(boolean changed) {
                if (changed) notifyBinders();
            }
        });
    }

    private void dereferenceCurrent(final DereferenceCallback<DataImage> callback) {
        if (mReview != null && mReview.isValidReference()) {
            doDereference(callback);
        } else {
            findReviewWithCover(new ChoiceCallback() {
                @Override
                public void onChosen(boolean changed) {
                    doDereference(callback);
                }
            });
        }
    }

    private void doDereference(final DereferenceCallback<DataImage> callback) {
        if (mReview != null && mReview.isValidReference()) {
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
        for (ValueSubscriber<DataImage> binder : mBinders) {
            binder.onReferenceValue(data);
        }
    }
}
