/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 May, 2015
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.UserData.Author;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewFeed extends ApplicationSingleton {
    private static final String  NAME              = "ReviewFeed";
    private static final boolean USE_TEST_DATABASE = true;

    private static ReviewFeed sSingleton;

    private final ReviewerDb mDatabase;
    private ReviewTreeNode mFeedNode;
    private ArrayList<ReviewFeedObserver> mObservers;

    private ReviewFeed(Context context) {
        super(context, NAME);
        mObservers = new ArrayList<>();

        Author author = Administrator.get(context).getAuthor();
        String title = author.getName() + "'s feed";
        Review feed = FactoryReview.createReviewUser(author, PublishDate.now(), title, 0f);
        mFeedNode = FactoryReview.createReviewTreeNode(feed, true);

        mDatabase = getDatabase();
        mDatabase.loadTags();
        for (ReviewNode node : mDatabase.getReviewTreesFromDb()) {
            add(node);
        }
    }

    private static ReviewFeed getFeed(Context context) {
        sSingleton = getSingleton(sSingleton, ReviewFeed.class, context);
        return sSingleton;
    }

    public static void addToFeed(Context context, ReviewNode node) {
        getFeed(context).add(node.getRoot());
        getFeed(context).addToDatabase(node.getRoot());
    }

    public static void removeFromFeed(Context context, String reviewId) {
        getFeed(context).removeFromFeed(reviewId);
    }

    public static ReviewNode getFeedNode(Context context) {
        return getFeed(context).getFeedNode();
    }

    public static void deleteTestDatabase(Context context) {
        if (USE_TEST_DATABASE) {
            context.deleteDatabase(getFeed(context).mDatabase.getDatabaseName());
        }
    }

    public static void registerObserver(Context context, ReviewFeedObserver observer) {
        getFeed(context).registerObserver(observer);
    }

    private ReviewNode getFeedNode() {
        return mFeedNode;
    }

    private void registerObserver(ReviewFeedObserver observer) {
        mObservers.add(observer);
    }

    private void removeFromFeed(String reviewId) {
        ReviewId id = ReviewId.fromString(reviewId);
        remove(id);
        deleteFromDatabase(id);
    }

    private void add(ReviewNode node) {
        mFeedNode.addChild(FactoryReview.createReviewTreeNode(node, false));
        notifyObservers();
    }

    private void addToDatabase(ReviewNode node) {
        mDatabase.addReviewTreeToDb(node);
    }

    private void deleteFromDatabase(ReviewId id) {
        mDatabase.deleteReviewTreeFromDb(id.toString());
    }

    private void remove(ReviewId id) {
        mFeedNode.removeChild(id);
        notifyObservers();
    }

    private ReviewerDb getDatabase() {
        if (USE_TEST_DATABASE) {
            return ReviewerDb.getTestDatabase(getContext());
        } else {
            return ReviewerDb.getDatabase(getContext());
        }
    }

    private void notifyObservers() {
        for (ReviewFeedObserver observer : mObservers) {
            observer.onFeedUpdated();
        }
    }

    public interface ReviewFeedObserver {
        void onFeedUpdated();
    }
}
