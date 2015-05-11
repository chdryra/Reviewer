/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.FactoryReview;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewTreeNode;
import com.chdryra.android.reviewer.View.GvImageList;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewFeed extends ApplicationSingleton {
    private static final String  NAME              = "ReviewFeed";
    private static final boolean USE_TEST_DATABASE = true;

    private static ReviewFeed        sFeed;
    private final  ReviewerDb        mDatabase;
    private        ReviewTreeNode    mFeedNode;
    private        ReviewFeedAdapter mAdapter;

    private ReviewFeed(Context context) {
        super(context, NAME);
        Author author = Administrator.get(context).getAuthor();
        String title = author.getName() + "'s feed";
        Review feed = FactoryReview.createReviewUser(author, new Date(), title, 0f);
        mFeedNode = FactoryReview.createReviewTreeNode(feed, true);

        mDatabase = getDatabase();
        mDatabase.loadTags();
        for (ReviewNode node : mDatabase.getReviewTreesFromDb()) {
            add(node);
        }

        mAdapter = new ReviewFeedAdapter(context);
    }

    private static ReviewFeed getFeed(Context context) {
        if (sFeed == null) {
            sFeed = new ReviewFeed(context);
        } else {
            sFeed.checkContextOrThrow(context);
        }

        return sFeed;
    }

    public static void addToFeed(Context context, ReviewNode node) {
        getFeed(context).add(node);
        getFeed(context).addToDatabase(node);
    }

    public static void removeFromFeed(Context context, String reviewId) {
        getFeed(context).remove(reviewId);
        getFeed(context).deleteFromDatabase(reviewId);
    }

    public static ReviewViewAdapter getFeedAdapter(Context context) {
        return getFeed(context).getFeedAdapter();
    }

    public static ReviewNode getReviewNode(Context context, String id) {
        return getFeed(context).getFeedNode().getChildren().get(ReviewId.fromString(id));
    }

    public static void deleteTestDatabase(Context context) {
        if (USE_TEST_DATABASE) {
            context.deleteDatabase(getFeed(context).mDatabase.getDatabaseName());
        }
    }

    private ReviewNode getFeedNode() {
        return mFeedNode;
    }

    private ReviewViewAdapter getFeedAdapter() {
        return mAdapter;
    }

    private void add(ReviewNode node) {
        mFeedNode.addChild(FactoryReview.createReviewTreeNode(node, false));
    }

    private void addToDatabase(ReviewNode node) {
        mDatabase.addReviewTreeToDb(node);
    }

    private void deleteFromDatabase(String reviewId) {
        mDatabase.deleteReviewTreeFromDb(reviewId);
    }

    private void remove(String reviewId) {
        mFeedNode.removeChild(ReviewId.fromString(reviewId));
    }

    private ReviewerDb getDatabase() {
        if (USE_TEST_DATABASE) {
            return ReviewerDb.getTestDatabase(getContext());
        } else {
            return ReviewerDb.getDatabase(getContext());
        }
    }

    public class ReviewFeedAdapter extends ReviewChildrenAdapter {
        private ReviewFeedAdapter(Context context) {
            super(context, mFeedNode);
        }

        @Override
        public GvImageList getCovers() {
            return new GvImageList();
        }

        public ReviewViewAdapter expandReview(ReviewId id) {
            ReviewIdableList<ReviewNode> nodes = mFeedNode.getChildren();
            if (nodes.containsId(id)) {
                ReviewNode unwrapped = nodes.get(id).getReview().getInternalNode();
                return new ReviewNodeAdapter(getContext(), unwrapped);
            } else {
                return null;
            }
        }
    }
}
