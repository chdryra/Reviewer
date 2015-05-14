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
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewTreeNode;
import com.chdryra.android.reviewer.View.GvReviewId;
import com.chdryra.android.reviewer.View.GvReviewList;
import com.chdryra.android.reviewer.View.LaunchableUi;
import com.chdryra.android.reviewer.View.ReviewDataScreen;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewFeed extends ApplicationSingleton {
    private static final String  NAME              = "ReviewFeed";
    private static final boolean USE_TEST_DATABASE = true;

    private static ReviewFeed sFeed;
    private final  ReviewerDb mDatabase;

    private ReviewTreeNode mFeedNode;
    private ReviewViewAdapter mFeedAdapter;

    private ReviewFeed(Context context) {
        super(context, NAME);
        Author author = Administrator.get(context).getAuthor();
        String title = author.getName() + "'s feed";
        Review feed = FactoryReview.createReviewUser(author, new Date(), title, 0f);

        mFeedNode = FactoryReview.createReviewTreeNode(feed, true);
        mFeedAdapter = FactoryReviewViewAdapter.newChildListAdapter(context, mFeedNode);

        mDatabase = getDatabase();
        mDatabase.loadTags();
        for (ReviewNode node : mDatabase.getReviewTreesFromDb()) {
            add(node);
        }
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

    public static ReviewViewAdapter getAggregateAdapter(Context context) {
        return ((AdapterReviewNode) getFeed(context).getFeedAdapter()).getTreeDataAdapter(context);
    }

    public static LaunchableUi getReviewLaunchable(Context context, GvReviewId id) {
        return getFeed(context).getReviewLaunchable(id.getId());
    }

    public static ReviewNode getReviewNode(Context context, String id) {
        return getFeed(context).getFeedNode().getChildren().get(ReviewId.fromString(id));
    }

    public static void deleteTestDatabase(Context context) {
        if (USE_TEST_DATABASE) {
            context.deleteDatabase(getFeed(context).mDatabase.getDatabaseName());
        }
    }

    private LaunchableUi getReviewLaunchable(String reviewId) {
        GvReviewList list = (GvReviewList) mFeedAdapter.getGridData();
        for (GvReviewList.GvReviewOverview review : list) {
            if (review.getId().equals(reviewId)) {
                ReviewViewAdapter adapter = mFeedAdapter.expandItem(review);
                return ReviewDataScreen.newScreen(getContext(), adapter);
            }
        }

        return null;
    }

    private ReviewNode getFeedNode() {
        return mFeedNode;
    }

    private ReviewViewAdapter getFeedAdapter() {
        return mFeedAdapter;
    }

    private void add(ReviewNode node) {
        mFeedNode.addChild(FactoryReview.createReviewTreeNode(node, false));
        mFeedAdapter.notifyGridDataObservers();
    }

    private void addToDatabase(ReviewNode node) {
        mDatabase.addReviewTreeToDb(node);
    }

    private void deleteFromDatabase(String reviewId) {
        mDatabase.deleteReviewTreeFromDb(reviewId);
    }

    private void remove(String reviewId) {
        mFeedNode.removeChild(ReviewId.fromString(reviewId));
        mFeedAdapter.notifyGridDataObservers();
    }

    private ReviewerDb getDatabase() {
        if (USE_TEST_DATABASE) {
            return ReviewerDb.getTestDatabase(getContext());
        } else {
            return ReviewerDb.getDatabase(getContext());
        }
    }
}
