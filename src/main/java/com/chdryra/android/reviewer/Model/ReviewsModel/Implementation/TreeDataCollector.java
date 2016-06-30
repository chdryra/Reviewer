/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 24/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeDataCollector {
    public static class Covers extends NodeDataCollector<DataImage> {
        private ReviewReference.CoversCallback mCallback;

        public Covers(IdableList<ReviewNode> nodes, ReviewReference.CoversCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getData(new ReviewReference.CoversCallback() {
                @Override
                public void onCovers(IdableList<? extends DataImage> covers, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), covers, message);
                }
            });
            
            return CallbackMessage.ok();
        }

        @Override
        public void onCompleted(IdableList<DataImage> data, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onCovers(data, errors.getMessage());
        }
    }

    public static class Images extends NodeDataCollector<DataImage> {
        private ReviewReference.ImagesCallback mCallback;

        public Images(IdableList<ReviewNode> nodes, ReviewReference.ImagesCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getData(new ReviewReference.ImagesCallback() {
                @Override
                public void onImages(IdableList<? extends DataImage> images, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), images, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        public void onCompleted(IdableList<DataImage> data, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onImages(data, errors.getMessage());
        }
    }

    public static class ImagesSize extends NodeDataCollector.Size {
        private ReviewReference.ImagesSizeCallback mCallback;

        public ImagesSize(IdableList<ReviewNode> nodes, ReviewReference.ImagesSizeCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getSize(new ReviewReference.ImagesSizeCallback() {
                @Override
                public void onNumImages(DataSize size, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), size, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        protected void onCompleted(DataSize size, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onNumImages(size, errors.getMessage());
        }
    }

    public static class Locations extends NodeDataCollector<DataLocation> {
        private ReviewReference.LocationsCallback mCallback;

        public Locations(IdableList<ReviewNode> nodes, ReviewReference.LocationsCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getData(new ReviewReference.LocationsCallback() {
                @Override
                public void onLocations(IdableList<? extends DataLocation> locations, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), locations, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        public void onCompleted(IdableList<DataLocation> data, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onLocations(data, errors.getMessage());
        }
    }

    public static class LocationsSize extends NodeDataCollector.Size {
        private ReviewReference.LocationsSizeCallback mCallback;

        public LocationsSize(IdableList<ReviewNode> nodes, ReviewReference.LocationsSizeCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getSize(new ReviewReference.LocationsSizeCallback() {
                @Override
                public void onNumLocations(DataSize size, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), size, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        protected void onCompleted(DataSize size, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onNumLocations(size, errors.getMessage());
        }
    }

    public static class Facts extends NodeDataCollector<DataFact> {
        private ReviewReference.FactsCallback mCallback;

        public Facts(IdableList<ReviewNode> nodes, ReviewReference.FactsCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getData(new ReviewReference.FactsCallback() {
                @Override
                public void onFacts(IdableList<? extends DataFact> facts, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), facts, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        public void onCompleted(IdableList<DataFact> data, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onFacts(data, errors.getMessage());
        }
    }

    public static class FactsSize extends NodeDataCollector.Size {
        private ReviewReference.FactsSizeCallback mCallback;

        public FactsSize(IdableList<ReviewNode> nodes, ReviewReference.FactsSizeCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getSize(new ReviewReference.FactsSizeCallback() {
                @Override
                public void onNumFacts(DataSize size, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), size, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        protected void onCompleted(DataSize size, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onNumFacts(size, errors.getMessage());
        }
    }
    
    public static class Comments extends NodeDataCollector<DataComment> {
        private ReviewReference.CommentsCallback mCallback;

        public Comments(IdableList<ReviewNode> nodes, ReviewReference.CommentsCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getData(new ReviewReference.CommentsCallback() {
                @Override
                public void onComments(IdableList<? extends DataComment> comments, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), comments, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        public void onCompleted(IdableList<DataComment> data, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onComments(data, errors.getMessage());
        }
    }

    public static class CommentsSize extends NodeDataCollector.Size {
        private ReviewReference.CommentsSizeCallback mCallback;

        public CommentsSize(IdableList<ReviewNode> nodes, ReviewReference.CommentsSizeCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getSize(new ReviewReference.CommentsSizeCallback() {
                @Override
                public void onNumComments(DataSize size, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), size, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        protected void onCompleted(DataSize size, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onNumComments(size, errors.getMessage());
        }
    }
    
    public static class Criteria extends NodeDataCollector<DataCriterion> {
        private ReviewReference.CriteriaCallback mCallback;

        public Criteria(IdableList<ReviewNode> nodes, ReviewReference.CriteriaCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getData(new ReviewReference.CriteriaCallback() {
                @Override
                public void onCriteria(IdableList<? extends DataCriterion> criteria, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), criteria, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        public void onCompleted(IdableList<DataCriterion> data, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onCriteria(data, errors.getMessage());
        }
    }

    public static class Tags extends NodeDataCollector<DataTag> {
        private ReviewReference.TagsCallback mCallback;

        public Tags(IdableList<ReviewNode> nodes, ReviewReference.TagsCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getData(new ReviewReference.TagsCallback() {
                @Override
                public void onTags(IdableList<? extends DataTag> tags, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), tags, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        public void onCompleted(IdableList<DataTag> data, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onTags(data, errors.getMessage());
        }
    }

    public static class CriteriaSize extends NodeDataCollector.Size {
        private ReviewReference.CriteriaSizeCallback mCallback;

        public CriteriaSize(IdableList<ReviewNode> nodes, ReviewReference.CriteriaSizeCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getSize(new ReviewReference.CriteriaSizeCallback() {
                @Override
                public void onNumCriteria(DataSize size, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), size, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        protected void onCompleted(DataSize size, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onNumCriteria(size, errors.getMessage());
        }
    }
    
    public static class TagsSize extends NodeDataCollector.Size {
        private ReviewReference.TagsSizeCallback mCallback;

        public TagsSize(IdableList<ReviewNode> nodes, ReviewReference.TagsSizeCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public CallbackMessage doAsyncMethod(final ReviewNode node) {
            node.getSize(new ReviewReference.TagsSizeCallback() {
                @Override
                public void onNumTags(DataSize size, CallbackMessage message) {
                    onNodeReturned(node.getReviewId(), size, message);
                }
            });

            return CallbackMessage.ok();
        }

        @Override
        protected void onCompleted(DataSize size, AsyncMethodTracker.AsyncErrors errors) {
            mCallback.onNumTags(size, errors.getMessage());
        }
    }
}
