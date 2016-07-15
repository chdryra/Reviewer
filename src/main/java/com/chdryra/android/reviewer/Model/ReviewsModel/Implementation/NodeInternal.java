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
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryDataCollector;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
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

public class NodeInternal extends ReviewNodeComponentBasic implements ReviewNodeComponent,
        ReferenceBinder.DataBinder, ReferenceBinder.DataSizeBinder {
    private final DataReviewInfo mMeta;
    private final MdDataList<ReviewNodeComponent> mChildren;
    private FactoryReviewNode mNodeFactory;
    
    private Map<ReviewId, ReferenceBinder> mChildBinders;

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

    //TODO optimisation that doesn't involve regetting of all data on a single child's update.
    @Override
    public void onAuthors(IdableList<? extends DataAuthorReview> Authors, CallbackMessage message) {
        if (!message.isError()) {
            getData(new AuthorsCallback() {
                @Override
                public void onAuthors(IdableList<? extends DataAuthorReview> authors, CallbackMessage message) {
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
    public void onDates(IdableList<? extends DataDateReview> Dates, CallbackMessage message) {
        if (!message.isError()) {
            getData(new DatesCallback() {
                @Override
                public void onDates(IdableList<? extends DataDateReview> dates, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getDatesBinders(), dates, message);
                }
            });
        }
    }

    @Override
    public void onNumDates(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new DatesSizeCallback() {
                @Override
                public void onNumDates(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumCommentsBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onReviews(IdableList<ReviewReference> reviews, CallbackMessage message) {
        if (!message.isError()) {
            getData(new ReviewsCallback() {
                @Override
                public void onReviews(IdableList<ReviewReference> reviews, CallbackMessage message) {
                    notifyOnReviews(reviews, message);
                }
            });
        }
    }

    @Override
    public void onNumReviews(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new ReviewsSizeCallback() {
                @Override
                public void onNumReviews(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumCommentsBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onSubjects(IdableList<? extends DataSubject> subjects, CallbackMessage message) {
        if (!message.isError()) {
            getData(new SubjectsCallback() {
                @Override
                public void onSubjects(IdableList<? extends DataSubject> subjects, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getSubjectsBinders(), subjects, message);
                }
            });
        }
    }

    @Override
    public void onNumSubjects(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new SubjectsSizeCallback() {
                @Override
                public void onNumSubjects(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumCommentsBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onTags(IdableList<? extends DataTag> tags, CallbackMessage message) {
        if (!message.isError()) {
            getData(new TagsCallback() {
                @Override
                public void onTags(IdableList<? extends DataTag> tags, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getTagsBinders(), tags, message);
                }
            });
        }
    }

    @Override
    public void onComments(IdableList<? extends DataComment> comments, CallbackMessage message) {
        if (!message.isError()) {
            getData(new CommentsCallback() {
                @Override
                public void onComments(IdableList<? extends DataComment> comments, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getCommentsBinders(), comments, message);
                }
            });
        }
    }

    @Override
    public void onCovers(IdableList<? extends DataImage> covers, CallbackMessage message) {
        if (!message.isError()) {
            getData(new CoversCallback() {
                @Override
                public void onCovers(IdableList<? extends DataImage> covers, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getCoversBinders(), covers, message);
                }
            });
        }
    }

    @Override
    public void onCriteria(IdableList<? extends DataCriterion> criteria, CallbackMessage message) {
        if (!message.isError()) {
            getData(new CriteriaCallback() {
                @Override
                public void onCriteria(IdableList<? extends DataCriterion> criteria, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getCriteriaBinders(), criteria, message);
                }
            });
        }
    }

    @Override
    public void onFacts(IdableList<? extends DataFact> facts, CallbackMessage message) {
        if (!message.isError()) {
            getData(new FactsCallback() {
                @Override
                public void onFacts(IdableList<? extends DataFact> facts, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getFactsBinders(), facts, message);
                }
            });
        }
    }

    @Override
    public void onImages(IdableList<? extends DataImage> images, CallbackMessage message) {
        if (!message.isError()) {
            getData(new ImagesCallback() {
                @Override
                public void onImages(IdableList<? extends DataImage> images, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getImagesBinders(), images, message);
                }
            });
        }
    }

    @Override
    public void onLocations(IdableList<? extends DataLocation> locations, CallbackMessage message) {
        if (!message.isError()) {
            getData(new LocationsCallback() {
                @Override
                public void onLocations(IdableList<? extends DataLocation> locations, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getLocationsBinders(), locations, message);
                }
            });
        }
    }

    @Override
    public void onNumComments(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new LocationsSizeCallback() {
                @Override
                public void onNumLocations(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumCommentsBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumCriteria(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new CriteriaSizeCallback() {
                @Override
                public void onNumCriteria(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumCriteriaBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumFacts(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new FactsSizeCallback() {
                @Override
                public void onNumFacts(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumFactsBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumImages(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new ImagesSizeCallback() {
                @Override
                public void onNumImages(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumImagesBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumLocations(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new LocationsSizeCallback() {
                @Override
                public void onNumLocations(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumLocationsBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void onNumTags(DataSize size, CallbackMessage message) {
        if (!message.isError()) {
            getSize(new TagsSizeCallback() {
                @Override
                public void onNumTags(DataSize size, CallbackMessage message) {
                    notifyOnValue(getBindersManager().getNumTagsBinders(), size, message);
                }
            });
        }
    }

    @Override
    public void getData(final CoversCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getData(TagsCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getData(CriteriaCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getData(ImagesCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getData(CommentsCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getData(LocationsCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getData(FactsCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getSize(TagsSizeCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getSize(CriteriaSizeCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getSize(ImagesSizeCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getSize(CommentsSizeCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getSize(LocationsSizeCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getSize(FactsSizeCallback callback) {
        getCollectorFactory().newCollector(getChildren(), callback).collect();
    }
    
    @Override
    public void dereference(final DereferenceCallback callback) {
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
        ReferenceBinder remove = mChildBinders.remove(childId);
        getBindersManager().unmanageBinder(remove);
    }

    private void bindToChild(ReviewNode child) {
        ReferenceBinder binder = mNodeFactory.getBinderFactory().bindTo(child, this, this);
        getBindersManager().manageBinder(binder);
        mChildBinders.put(child.getReviewId(), binder);
    }

    private void notifyOnReviews(IdableList<ReviewReference> data, CallbackMessage message) {
        if (!message.isError()) {
            for (ValueBinder<IdableList<ReviewReference>> binder : getBindersManager().getReviewsBinders()) {
                binder.onValue(data);
            }
        }
    }
    
    private <T extends HasReviewId> void notifyOnValue(Collection<? extends
            ValueBinder<IdableList<? extends T>>> binders, IdableList<? extends T> data, CallbackMessage message) {
        if (!message.isError()) {
            for (ValueBinder<IdableList<? extends T>> binder : binders) {
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
        if (!getCollectorFactory().equals(that.getCollectorFactory())) return false;
        return mChildBinders.equals(that.mChildBinders);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mMeta.hashCode();
        result = 31 * result + mChildren.hashCode();
        result = 31 * result + getCollectorFactory().hashCode();
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
