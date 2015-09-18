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
import com.chdryra.android.reviewer.Model.ReviewData.ReviewPublisher;
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
        ReviewPublisher publisher = new ReviewPublisher(author, PublishDate.now());
        Review feed = FactoryReview.createReviewUser(publisher, title, 0f);
        mFeedNode = FactoryReview.createReviewTreeNode(feed, true);

        mDatabase = getDatabase();
        mDatabase.loadTags();
        for (Review review : mDatabase.getReviewsFromDb()) {
            add(review);
        }
    }

    private static ReviewFeed getFeed(Context context) {
        sSingleton = getSingleton(sSingleton, ReviewFeed.class, context);
        return sSingleton;
    }

    public static void addToFeed(Context context, Review review) {
        getFeed(context).add(review);
    }

    public static void removeFromFeed(Context context, String reviewId) {
        getFeed(context).removeFromFeed(reviewId);
    }

    public static ReviewNode getFeedNode(Context context) {
        return getFeed(context).getFeedNode();
    }

    public static ReviewNode findInFeed(Context context, String reviewId) {
        return getFeed(context).findInFeed(ReviewId.fromString(reviewId));
    }

    public static void deleteTestDatabase(Context context) {
        if (USE_TEST_DATABASE) {
            context.deleteDatabase(getFeed(context).mDatabase.getDatabaseName());
        }
    }

    public static void registerObserver(Context context, ReviewFeedObserver observer) {
        getFeed(context).registerObserver(observer);
    }

    public ReviewNode findInFeed(ReviewId id) {
        return mFeedNode.getChildren().get(id);
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

    private void add(Review review) {
        ReviewTreeNode node = FactoryReview.createReviewTreeNode(review, false);
        mFeedNode.addChild(node);
        addToDatabase(review);
        notifyObservers();
    }

    private void addToDatabase(Review review) {
        mDatabase.addReviewToDb(review);
    }

    private void deleteFromDatabase(ReviewId id) {
        mDatabase.deleteReviewFromDb(id.toString());
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
