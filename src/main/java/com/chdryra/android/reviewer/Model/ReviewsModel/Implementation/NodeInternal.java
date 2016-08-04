/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryDataCollector;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NodeInternal extends ReviewNodeComponentBasic implements ReviewNodeComponent,
        ReviewReferenceBinder.DataBinder, ReviewReferenceBinder.DataSizeBinder {
    private final DataReviewInfo mMeta;
    private final MdDataList<ReviewNodeComponent> mChildren;
    private FactoryReviewNode mNodeFactory;
    
    private Map<ReviewId, ReviewReferenceBinder> mChildBinders;

    public NodeInternal(DataReviewInfo meta, FactoryReviewNode nodeFactory) {
        super(nodeFactory);
        mMeta = meta;
        mNodeFactory = nodeFactory;

        mChildren = new MdDataList<>(getReviewId());
        mChildBinders = new HashMap<>();
    }

    private FactoryDataCollector getCollectorFactory() {
        return mNodeFactory.getDataCollectorFactory();
    }

    protected FactoryReviewNode getNodeFactory() {
        return mNodeFactory;
    }

    @Override
    public void addChild(ReviewNodeComponent child) {
        if (mChildren.containsId(child.getReviewId())) return;

        mChildren.add(child);
        child.setParent(this);

        bindToChild(child);

        notifyOnChildAdded(child);
    }

    @Override
    public void addChildren(Iterable<ReviewNodeComponent> children) {
        for(ReviewNodeComponent child : children) {
            if (mChildren.containsId(child.getReviewId())) continue;
            mChildren.add(child);
            child.setParent(this);
        }

        for(ReviewNodeComponent child : children) {
            bindToChild(child);
        }

        for(ReviewNodeComponent child : children) {
            notifyOnChildAdded(child);
        }
    }

    @Override
    public void removeChild(ReviewId reviewId) {
        if (!hasChild(reviewId)) return;

        ReviewNodeComponent childNode = mChildren.getItem(reviewId);
        mChildren.remove(reviewId);
        if (childNode != null) childNode.setParent(null);

        unbindFromChild(reviewId);

        if (childNode != null) notifyOnChildRemoved(childNode);
    }

    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return mChildren.getItem(reviewId);
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return mChildren.containsId(reviewId);
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        MdDataList<ReviewNode> children = new MdDataList<>(mChildren.getReviewId());
        children.addAll(mChildren);
        return children;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return true;
    }

    //-------------Review Reference methods--------------
    @Override
    public ReviewId getReviewId() {
        return mMeta.getReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return mMeta.getSubject();
    }

    @Override
    public DataRating getRating() {
        return getAverageRating();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mMeta.getAuthorId();
    }

    @Override
    public DataDate getPublishDate() {
        return mMeta.getPublishDate();
    }

    @Override
    public ReviewNode asNode() {
        return this;
    }

    @Override
    public ReviewListReference<ReviewReference> getReviews() {
        return null;
    }

    //TODO optimisation that doesn't involve regetting of all data on a single child's update.
    @Override
    public void onAuthors(IdableList<? extends DataAuthorId> Authors, CallbackMessage message) {
        if (!message.isError()) {
            getData(new AuthorsCallback() {
                @Override
                public void onAuthors(IdableList<? extends DataAuthorId> authors, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getAuthorsBinders(), authors, message);
                }
            });
        }
    }

    @Override
    public void onNumAuthors(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new AuthorsSizeCallback() {
                @Override
                public void onNumAuthors(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumCommentsBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void getData(final CoverCallback callback) {
        getCollectorFactory().newCollector(getChildren(), new TreeDataCollector.CoversCallback() {
            @Override
            public void onCovers(IdableList<? extends DataImage> covers, CallbackMessage message) {
                callback.onCover(covers.getItem(new Random().nextInt(covers.size())), message);
            }
        }).collect();
    }

    @Override
    public void getData(TagsCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void dereference(final DereferenceCallback<Review> callback) {
        NodeDereferencer dereferencer = new NodeDereferencer();
        dereferencer.dereference(getChildren(), new NodeDereferencer.DereferenceCallback() {
            @Override
            public void onDereferenced(List<Review> reviews,
                                       AsyncMethodTracker.AsyncErrors errors) {
                if(!errors.hasErrors()) {
                    Review review
                            = mNodeFactory.getReviewsFactory().createMetaReview(mMeta, reviews);
                    callback.onDereferenced(review, CallbackMessage.ok());
                } else {
                    callback.onDereferenced(null, errors.getMessage());
                }
            }
        });
    }

    @Override
    public boolean isValidReference() {
        return mMeta != null && mChildren.size() > 0;
    }

    @NonNull
    private DataRating getAverageRating() {
        float rating = 0f;
        int weight = 0;
        for (ReviewNode child : getChildren()) {
            DataRating childRating = child.getRating();
            rating += childRating.getRating() * childRating.getRatingWeight();
            weight += childRating.getRatingWeight();
        }
        if (weight > 0) rating /= weight;
        return new MdRating(new MdReviewId(getReviewId()), rating, weight);
    }

    private void unbindFromChild(ReviewId childId) {
        ReviewReferenceBinder remove = mChildBinders.remove(childId);
        getBindersManager().unmanageBinder(remove);
    }

    private void bindToChild(ReviewNode child) {
        ReviewReferenceBinder binder = mNodeFactory.getBinderFactory().bindTo(child, this, this);
        getBindersManager().manageBinder(binder);
        mChildBinders.put(child.getReviewId(), binder);
    }

    private void notifyOnReviews(IdableList<ReviewReference> data, CallbackMessage message) {
        if (!message.isError()) {
            for (ReferenceBinder<IdableList<ReviewReference>> binder : getBindersManager().getReviewsBinders()) {
                binder.onReferenceValue(data);
            }
        }
    }

    private void notifyOnValue(DataImage cover, CallbackMessage message) {
        if (!message.isError()) {
            for (ReferenceBinder<DataImage> binder : getBindersManager().getCoverBinders()) {
                binder.onReferenceValue(cover);
            }
        }
    }

    private <T extends HasReviewId> void notifyOnValue(Collection<? extends
            ReferenceBinder<IdableList<? extends T>>> binders, IdableList<? extends T> data, CallbackMessage message) {
        if (!message.isError()) {
            for (ReferenceBinder<IdableList<? extends T>> binder : binders) {
                binder.onReferenceValue(data);
            }
        }
    }

    private void notifyOnValue(Collection<ReferenceBinders.SizeBinder> binders, DataSize size,
                               CallbackMessage message) {
        if (!message.isError()) {
            for (ReferenceBinders.SizeBinder binder : binders) {
                binder.onReferenceValue(size);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeInternal)) return false;
        if (!super.equals(o)) return false;

        NodeInternal that = (NodeInternal) o;

        if (!mMeta.equals(that.mMeta)) return false;
        if (!mChildren.equals(that.mChildren)) return false;
        if (!mNodeFactory.equals(that.mNodeFactory)) return false;
        return mChildBinders.equals(that.mChildBinders);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mMeta.hashCode();
        result = 31 * result + mChildren.hashCode();
        result = 31 * result + mNodeFactory.hashCode();
        result = 31 * result + mChildBinders.hashCode();
        return result;
    }

    private static class NodeDereferencer {
        private AsyncMethodTracker mTracker;
        private List<Review> mReviews;

        public interface DereferenceCallback {
            void onDereferenced(List<Review> reviews, AsyncMethodTracker.AsyncErrors errors);
        }

        public NodeDereferencer() {
            mReviews = new ArrayList<>();
        }

        public void dereference(IdableList<ReviewNode> nodes,
                                final DereferenceCallback callback) {
            mTracker = new AsyncMethodTracker(nodes, new AsyncMethodTracker.AsyncMethod() {
                @Override
                public CallbackMessage execute(final ReviewNode node) {
                    node.dereference(new ReviewReference.DereferenceCallback<Review>() {
                        @Override
                        public void onDereferenced(@Nullable Review review, CallbackMessage message) {
                            if(!message.isError() && review != null) mReviews.add(review);
                            mTracker.onNodeReturned(node.getReviewId(), message);
                        }
                    });

                    return CallbackMessage.ok();
                }

                @Override
                public void onNodesCompleted(AsyncMethodTracker.AsyncErrors errors) {
                    callback.onDereferenced(mReviews, errors);
                }
            });
        }
    }
}
