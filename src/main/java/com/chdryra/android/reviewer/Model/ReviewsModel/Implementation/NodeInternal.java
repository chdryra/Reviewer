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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
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
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryDataCollector;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ValueBinder;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeInternal extends ReviewNodeBasic implements ReviewNodeComponent,
        ReferenceBinder.DataBinder, ReferenceBinder.DataSizeBinder {
    private final DataReviewInfo mMeta;
    private final MdDataList<ReviewNodeComponent> mChildren;
    private FactoryBinders mBinderFactory;
    private FactoryDataCollector mCollectorFactory;
    private FactoryReviews mReviewsFactory;
    
    private Map<ReviewId, ReferenceBinder> mChildBinders;

    public NodeInternal(DataReviewInfo meta,
                        FactoryBinders binderFactory,
                        FactoryDataCollector collectorFactory,
                        FactoryReviews reviewFactory) {
        super(binderFactory.newBindersManager());
        mMeta = meta;
        mBinderFactory = binderFactory;
        mCollectorFactory = collectorFactory;
        mReviewsFactory = reviewFactory;

        mChildren = new MdDataList<>(getReviewId());
        mChildBinders = new HashMap<>();
    }

    @Override
    public boolean addChild(ReviewNodeComponent child) {
        if (mChildren.containsId(child.getReviewId())) return false;

        mChildren.add(child);
        child.setParent(this);

        bindToChild(child);

        notifyNodeObservers();

        return true;
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

        notifyNodeObservers();
    }

    @Override
    public void removeChild(ReviewId reviewId) {
        if (!mChildren.containsId(reviewId)) return;

        ReviewNodeComponent childNode = mChildren.getItem(reviewId);
        mChildren.remove(reviewId);
        if (childNode != null) childNode.setParent(null);

        unbindFromChild(reviewId);

        notifyNodeObservers();
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
    public DataAuthorReview getAuthor() {
        return mMeta.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mMeta.getPublishDate();
    }

    @Override
    public ReviewNode asNode() {
        return this;
    }

    @Override
    public void onTags(IdableList<DataTag> tags, CallbackMessage message) {
        if (!message.isError()) {
            getTags(new TagsCallback() {
                @Override
                public void onTags(IdableList<DataTag> tags, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getTagsBinders(), tags, message);
                }
            });
        }
    }

    @Override
    public void onComments(IdableList<DataComment> comments, CallbackMessage message) {
        if (!message.isError()) {
            getComments(new CommentsCallback() {
                @Override
                public void onComments(IdableList<DataComment> comments, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getCommentsBinders(), comments, message);
                }
            });
        }
    }

    @Override
    public void onCovers(IdableList<DataImage> covers, CallbackMessage message) {
        if (!message.isError()) {
            getCovers(new CoversCallback() {
                @Override
                public void onCovers(IdableList<DataImage> covers, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getCoversBinders(), covers, message);
                }
            });
        }
    }

    @Override
    public void onCriteria(IdableList<DataCriterion> criteria, CallbackMessage message) {
        if (!message.isError()) {
            getCriteria(new CriteriaCallback() {
                @Override
                public void onCriteria(IdableList<DataCriterion> criteria, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getCriteriaBinders(), criteria, message);
                }
            });
        }
    }

    @Override
    public void onFacts(IdableList<DataFact> facts, CallbackMessage message) {
        if (!message.isError()) {
            getFacts(new FactsCallback() {
                @Override
                public void onFacts(IdableList<DataFact> facts, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getFactsBinders(), facts, message);
                }
            });
        }
    }

    @Override
    public void onImages(IdableList<DataImage> images, CallbackMessage message) {
        if (!message.isError()) {
            getImages(new ImagesCallback() {
                @Override
                public void onImages(IdableList<DataImage> images, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getImagesBinders(), images, message);
                }
            });
        }
    }

    @Override
    public void onLocations(IdableList<DataLocation> locations, CallbackMessage message) {
        if (!message.isError()) {
            getLocations(new LocationsCallback() {
                @Override
                public void onLocations(IdableList<DataLocation> locations, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getLocationsBinders(), locations, message);
                }
            });
        }
    }

    @Override
    public void onNumComments(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getLocationsSize(new LocationsSizeCallback() {
                @Override
                public void onNumLocations(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getCommentsSizeBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumCriteria(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getCriteriaSize(new CriteriaSizeCallback() {
                @Override
                public void onNumCriteria(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getCriteriaSizeBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumFacts(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getFactsSize(new FactsSizeCallback() {
                @Override
                public void onNumFacts(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getFactsSizeBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumImages(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getImagesSize(new ImagesSizeCallback() {
                @Override
                public void onNumImages(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getImagesSizeBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumLocations(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getLocationsSize(new LocationsSizeCallback() {
                @Override
                public void onNumLocations(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getLocationsSizeBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumTags(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getTagsSize(new TagsSizeCallback() {
                @Override
                public void onNumTags(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getTagsSizeBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void getCovers(final CoversCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getTags(TagsCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getCriteria(CriteriaCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getImages(ImagesCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getComments(CommentsCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getLocations(LocationsCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getFacts(FactsCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getTagsSize(TagsSizeCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getCriteriaSize(CriteriaSizeCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getImagesSize(ImagesSizeCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getCommentsSize(CommentsSizeCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getLocationsSize(LocationsSizeCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getFactsSize(FactsSizeCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }
    
    @Override
    public void dereference(final DereferenceCallback callback) {
        NodeDereferencer dereferencer = new NodeDereferencer();
        dereferencer.dereference(getChildren(), new NodeDereferencer.DereferenceCallback() {
            @Override
            public void onDereferenced(List<Review> reviews,
                                       AsyncMethodTracker.AsyncErrors errors) {
                if(!errors.hasErrors()) {
                    callback.onDereferenced(mReviewsFactory.createMetaReview(mMeta, reviews),
                            CallbackMessage.ok());
                } else {
                    callback.onDereferenced(null, errors.getMessage());
                }
            }
        });
    }

    @Override
    public boolean isValid() {
        return mMeta != null;
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

    private void bindToChild(ReviewNodeComponent child) {
        mChildBinders.put(child.getReviewId(), newBinder(child));
    }

    private void unbindFromChild(ReviewId childId) {
        ReferenceBinder remove = mChildBinders.remove(childId);
        remove.unregisterDataBinder(this);
        remove.unregisterDataBinder(this);
    }

    private ReferenceBinder newBinder(ReviewReference reference) {
        ReferenceBinder binder = mBinderFactory.newBinder(reference);
        binder.registerDataBinder(this);
        binder.registerSizeBinder(this);

        return binder;
    }

    private <T extends HasReviewId> void notifyOnValue(Collection<? extends
            ValueBinder<IdableList<T>>> binders, IdableList<T> data, CallbackMessage message) {
        if (!message.isError()) {
            for (ValueBinder<IdableList<T>> binder : binders) {
                binder.onValue(data);
            }
        }
    }

    private void notifyOnValue(Collection<ReferenceBinders.SizeBinder> binders, DataSize size,
                               CallbackMessage message) {
        if (!message.isError()) {
            for (ReferenceBinders.SizeBinder binder : binders) {
                binder.onValue(size);
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
        if (!mBinderFactory.equals(that.mBinderFactory)) return false;
        if (!mCollectorFactory.equals(that.mCollectorFactory)) return false;
        if (!mReviewsFactory.equals(that.mReviewsFactory)) return false;
        return mChildBinders.equals(that.mChildBinders);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mMeta.hashCode();
        result = 31 * result + mChildren.hashCode();
        result = 31 * result + mBinderFactory.hashCode();
        result = 31 * result + mCollectorFactory.hashCode();
        result = 31 * result + mReviewsFactory.hashCode();
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
                    node.dereference(new ReviewReference.DereferenceCallback() {
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
