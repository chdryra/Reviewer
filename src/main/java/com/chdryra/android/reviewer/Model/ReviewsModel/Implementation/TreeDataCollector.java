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
    /**
     * Created by: Rizwan Choudrey
     * On: 24/06/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class Covers extends NodeDataCollector<DataImage> {
        private ReviewReference.CoversCallback mCallback;

        public Covers(IdableList<ReviewNode> nodes, ReviewReference.CoversCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public void doAsyncMethod(ReviewNode node) {
            node.getCovers(new ReviewReference.CoversCallback() {
                @Override
                public void onCovers(IdableList<DataImage> covers, CallbackMessage message) {
                    onNodeReturned(covers, message);
                }
            });
        }

        @Override
        public void onCompleted(IdableList<DataImage> data) {
            mCallback.onCovers(data, CallbackMessage.ok());
        }
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 24/06/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class Images extends NodeDataCollector<DataImage> {
        private ReviewReference.ImagesCallback mCallback;

        public Images(IdableList<ReviewNode> nodes, ReviewReference.ImagesCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public void doAsyncMethod(ReviewNode node) {
            node.getImages(new ReviewReference.ImagesCallback() {
                @Override
                public void onImages(IdableList<DataImage> images, CallbackMessage message) {
                    onNodeReturned(images, message);
                }
            });
        }

        @Override
        public void onCompleted(IdableList<DataImage> data) {
            mCallback.onImages(data, CallbackMessage.ok());
        }
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 24/06/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class Locations extends NodeDataCollector<DataLocation> {
        private ReviewReference.LocationsCallback mCallback;

        public Locations(IdableList<ReviewNode> nodes, ReviewReference.LocationsCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public void doAsyncMethod(ReviewNode node) {
            node.getLocations(new ReviewReference.LocationsCallback() {
                @Override
                public void onLocations(IdableList<DataLocation> locations, CallbackMessage message) {
                    onNodeReturned(locations, message);
                }
            });
        }

        @Override
        public void onCompleted(IdableList<DataLocation> data) {
            mCallback.onLocations(data, CallbackMessage.ok());
        }
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 24/06/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class Facts extends NodeDataCollector<DataFact> {
        private ReviewReference.FactsCallback mCallback;

        public Facts(IdableList<ReviewNode> nodes, ReviewReference.FactsCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public void doAsyncMethod(ReviewNode node) {
            node.getFacts(new ReviewReference.FactsCallback() {
                @Override
                public void onFacts(IdableList<DataFact> facts, CallbackMessage message) {
                    onNodeReturned(facts, message);
                }
            });
        }

        @Override
        public void onCompleted(IdableList<DataFact> data) {
            mCallback.onFacts(data, CallbackMessage.ok());
        }
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 24/06/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class Comments extends NodeDataCollector<DataComment> {
        private ReviewReference.CommentsCallback mCallback;

        public Comments(IdableList<ReviewNode> nodes, ReviewReference.CommentsCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public void doAsyncMethod(ReviewNode node) {
            node.getComments(new ReviewReference.CommentsCallback() {
                @Override
                public void onComments(IdableList<DataComment> comments, CallbackMessage message) {
                    onNodeReturned(comments, message);
                }
            });
        }

        @Override
        public void onCompleted(IdableList<DataComment> data) {
            mCallback.onComments(data, CallbackMessage.ok());
        }
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 24/06/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class Criteria extends NodeDataCollector<DataCriterion> {
        private ReviewReference.CriteriaCallback mCallback;

        public Criteria(IdableList<ReviewNode> nodes, ReviewReference.CriteriaCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public void doAsyncMethod(ReviewNode node) {
            node.getCriteria(new ReviewReference.CriteriaCallback() {
                @Override
                public void onCriteria(IdableList<DataCriterion> criteria, CallbackMessage message) {
                    onNodeReturned(criteria, message);
                }
            });
        }

        @Override
        public void onCompleted(IdableList<DataCriterion> data) {
            mCallback.onCriteria(data, CallbackMessage.ok());
        }
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 24/06/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class Tags extends NodeDataCollector<DataTag> {
        private ReviewReference.TagsCallback mCallback;

        public Tags(IdableList<ReviewNode> nodes, ReviewReference.TagsCallback callback) {
            super(nodes);
            mCallback = callback;
        }

        @Override
        public void doAsyncMethod(ReviewNode node) {
            node.getTags(new ReviewReference.TagsCallback() {
                @Override
                public void onTags(IdableList<DataTag> tags, CallbackMessage message) {
                    onNodeReturned(tags, message);
                }
            });
        }

        @Override
        public void onCompleted(IdableList<DataTag> data) {
            mCallback.onTags(data, CallbackMessage.ok());
        }
    }
}
