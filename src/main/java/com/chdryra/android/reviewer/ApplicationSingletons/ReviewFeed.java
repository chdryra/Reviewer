/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 May, 2015
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorTreeFlattener;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Screens.ReviewDataScreen;

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
        Review feed = FactoryReview.createReviewUser(author, PublishDate.now(), title, 0f);

        mFeedNode = FactoryReview.createReviewTreeNode(feed, true);
        mFeedAdapter = FactoryReviewViewAdapter.newChildOverviewAdapter(context, mFeedNode);

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
        getFeed(context).add(node.getRoot());
        getFeed(context).addToDatabase(node.getRoot());
    }

    public static void removeFromFeed(Context context, String reviewId) {
        getFeed(context).removeFromFeed(reviewId);
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

    public static void deleteTestDatabase(Context context) {
        if (USE_TEST_DATABASE) {
            context.deleteDatabase(getFeed(context).mDatabase.getDatabaseName());
        }
    }

    private void removeFromFeed(String reviewId) {
        ReviewId id = findRootNodeId(reviewId);
        remove(id);
        deleteFromDatabase(id);
    }

    private LaunchableUi getReviewLaunchable(String reviewId) {
        ReviewId id = findRootNodeId(reviewId);
        if (id == null) return null;

        GvReviewOverviewList list = (GvReviewOverviewList) mFeedAdapter.getGridData();
        for (GvReviewOverviewList.GvReviewOverview review : list) {
            if (review.getId().equals(id.toString())) {
                ReviewViewAdapter adapter = mFeedAdapter.expandItem(review);
                return ReviewDataScreen.newScreen(getContext(), adapter);
            }
        }

        return null;
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

    private void deleteFromDatabase(ReviewId id) {
        mDatabase.deleteReviewTreeFromDb(id.toString());
    }

    private void remove(ReviewId id) {
        mFeedNode.removeChild(id);
        mFeedAdapter.notifyGridDataObservers();
    }

    private ReviewerDb getDatabase() {
        if (USE_TEST_DATABASE) {
            return ReviewerDb.getTestDatabase(getContext());
        } else {
            return ReviewerDb.getDatabase(getContext());
        }
    }

    private ReviewId findRootNodeId(String reviewId) {
        ReviewId id = ReviewId.fromString(reviewId);
        ReviewId root = null;
        for (ReviewNode child : mFeedNode.getChildren()) {
            ReviewNode tree = child.getReview().getTreeRepresentation();
            ReviewIdableList<ReviewNode> nodes = VisitorTreeFlattener.flatten(tree);
            if (nodes.containsId(id)) {
                root = nodes.getItem(0).getId();
                break;
            }
        }

        return root;
    }
}
