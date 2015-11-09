/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewStructure;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Database.ReviewDataHolder;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdRating;
import com.chdryra.android.reviewer.Model.ReviewData.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.UserData.Author;

import java.util.ArrayList;

/**
 * Factory for creating Reviews and ReviewNodes.
 * <p/>
 * <p>
 * A convenient place to
 * put constructors so as to minimise the use of constructors in multiple places.
 * </p>
 */
public class FactoryReview implements ReviewDataHolder.BuilderReviewUser {
    private MdGvConverter mConverter;

    //Constructors
    public FactoryReview(MdGvConverter converter) {
        mConverter = converter;
    }

    public Review createReviewUser(ReviewPublisher publisher, String subject,
                                   float rating,
                                   Iterable<? extends DataComment> comments,
                                   Iterable<? extends DataImage> images,
                                   Iterable<? extends DataFact> facts,
                                   Iterable<? extends DataLocation> locations,
                                   IdableList<Review> criteria, boolean ratingIsAverage) {
        return newReviewUser(publisher, subject, rating, comments,
                images, facts, locations, criteria, ratingIsAverage);
    }

    public Review createReviewUser(ReviewPublisher publisher, String subject, float rating) {
        return newReviewUser(publisher, subject, rating);
    }

    public ReviewTreeNode createReviewTreeNode(Review review, boolean isAverage) {
        return newReviewTreeNode(review, isAverage);
    }

    public ReviewNode createMetaReview(Review review) {
        ReviewPublisher publisher = new ReviewPublisher(review.getAuthor(),
                new PublishDate(review.getPublishDate().getTime()));
        IdableList<Review> single = new IdableList<>();
        single.add(review);

        return createMetaReview(single, publisher, review.getSubject().get());
    }

    public ReviewNode createMetaReview(IdableList<Review> reviews, ReviewPublisher publisher,
                                       String subject) {
        Review meta = createReviewUser(publisher, subject, 0f);
        ReviewTreeNode parent = createReviewTreeNode(meta, true);
        for (Review review : reviews) {
            parent.addChild(createReviewTreeNode(review, false));
        }

        return parent.createTree();
    }

    //private methods
    private Review newReviewUser(ReviewId id,
                                 Author author,
                                 PublishDate publishDate,
                                 String subject,
                                 float rating,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations,
                                 IdableList<Review> criteria,
                                 boolean ratingIsAverage) {
        MdSubject mdSubject = new MdSubject(subject, id);
        MdRating mdRating = new MdRating(rating, 1, id);
        MdCommentList mdComments = mConverter.toMdCommentList(comments, id);
        MdImageList mdImages = mConverter.toMdImageList(images, id);
        MdFactList mdFacts = mConverter.toMdFactList(facts, id);
        MdLocationList mdLocations = mConverter.toMdLocationList(locations, id);
        MdCriterionList mdCriteria = new MdCriterionList(criteria, id);
        if (ratingIsAverage) {
            Review dummy = new ReviewUser(id, author, publishDate, mdSubject, mdRating, mdComments,
                    mdImages, mdFacts, mdLocations, mdCriteria, ratingIsAverage, this);
            ReviewTreeNode node = createReviewTreeNode(dummy, true);
            for (Review criterion : criteria) {
                node.addChild(createReviewTreeNode(criterion, false));
            }
            rating = node.getRating().getValue();
        }
        mdRating = new MdRating(rating, 1, id);

        return new ReviewUser(id, author, publishDate, mdSubject, mdRating, mdComments,
                mdImages, mdFacts, mdLocations, mdCriteria, ratingIsAverage, this);
    }

    private Review newReviewUser(ReviewPublisher publisher, String subject, float
            rating) {
        return newReviewUser(publisher, subject, rating,
                new ArrayList<MdCommentList.MdComment>(),
                new ArrayList<MdImageList.MdImage>(),
                new ArrayList<MdFactList.MdFact>(),
                new ArrayList<MdLocationList.MdLocation>(),
                new IdableList<Review>(), false);
    }

    private Review newReviewUser(ReviewPublisher publisher, String subject, float rating,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations,
                                 IdableList<Review> criteria, boolean ratingIsAverage) {
        ReviewId id = ReviewId.newId(publisher);
        return newReviewUser(id, publisher.getAuthor(), publisher.getDate(),
                subject, rating,
                mConverter.toMdCommentList(comments, id),
                mConverter.toMdImageList(images, id),
                mConverter.toMdFactList(facts, id),
                mConverter.toMdLocationList(locations, id),
                criteria,
                ratingIsAverage);
    }

    private ReviewTreeNode newReviewTreeNode(Review review, boolean isAverage) {
        return new ReviewTreeNode(review, isAverage, review.getId());
    }

//Overridden
    @Override
    public Review createReviewUser(ReviewDataHolder review) {
        return newReviewUser(review.getId(), review.getAuthor(), review.getPublishDate(),
                review.getSubject(), review.getRating(), review.getComments(), review.getImages(),
                review.getFacts(), review.getLocations(), review.getCritList(), review.isAverage());
    }
}
