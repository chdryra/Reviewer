/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewStructure;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewPublisher;
import com.chdryra.android.reviewer.Database.ReviewDataHolder;
import com.chdryra.android.reviewer.Model.ReviewData.MdIdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdAuthor;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewData.MdDate;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdRating;
import com.chdryra.android.reviewer.Model.ReviewData.MdReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.MdSubject;

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
    private FactoryReviewNodeComponent mComponentFactory;
    private MdGvConverter mConverter;

    //Constructors
    public FactoryReview(FactoryReviewNodeComponent componentFactory, MdGvConverter converter) {
        mComponentFactory = componentFactory;
        mConverter = converter;
    }

    public Review createUserReview(ReviewPublisher publisher, String subject,
                                   float rating,
                                   Iterable<? extends DataComment> comments,
                                   Iterable<? extends DataImage> images,
                                   Iterable<? extends DataFact> facts,
                                   Iterable<? extends DataLocation> locations,
                                   MdIdableList<Review> criteria, boolean ratingIsAverage) {
        return newReviewUser(publisher, subject, rating, comments,
                images, facts, locations, criteria, ratingIsAverage);
    }

    public Review createUserReview(ReviewPublisher publisher, String subject, float rating) {
        return newReviewUser(publisher, subject, rating);
    }

    public Review createMetaReview(ReviewPublisher publisher, Review review) {
        MdIdableList<Review> single = new MdIdableList<>();
        single.add(review);

        return createMetaReview(single, publisher, review.getSubject().getSubject());
    }

    public Review createMetaReview(MdIdableList<Review> reviews, ReviewPublisher publisher,
                                       String subject) {
        Review meta = createUserReview(publisher, subject, 0f);
        ReviewNodeComponent parent = mComponentFactory.createReviewNodeComponent(meta, true);
        for (Review review : reviews) {
            ReviewNodeComponent child = mComponentFactory.createReviewNodeComponent(review, false);
            parent.addChild(child);
        }

        return parent.makeTree();
    }

    //private methods
    private Review newReviewUser(MdReviewId id,
                                 DataAuthor author,
                                 DataDate publishDate,
                                 String subject,
                                 float rating,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations,
                                 MdIdableList<Review> criteria,
                                 boolean ratingIsAverage) {
        if (ratingIsAverage) {
            ReviewPublisher publisher = new ReviewPublisher(author, publishDate);
            Review meta = createMetaReview(criteria, publisher, "");
            rating = meta.getRating().getRating();
        }

        MdAuthor mdAuthor = new MdAuthor(id, author.getName(), author.getUserId());
        MdDate mdDate = new MdDate(id, publishDate.getTime());
        MdSubject mdSubject = new MdSubject(id, subject);
        MdCommentList mdComments = mConverter.toMdCommentList(comments, id);
        MdImageList mdImages = mConverter.toMdImageList(images, id);
        MdFactList mdFacts = mConverter.toMdFactList(facts, id);
        MdLocationList mdLocations = mConverter.toMdLocationList(locations, id);
        MdCriterionList mdCriteria = new MdCriterionList(id, criteria);
        MdRating mdRating = new MdRating(id, rating, 1);

        return new ReviewUser(id, mdAuthor, mdDate, mdSubject, mdRating, mdComments,
                mdImages, mdFacts, mdLocations, mdCriteria, ratingIsAverage, mComponentFactory);
    }

    private Review newReviewUser(ReviewPublisher publisher, String subject, float
            rating) {
        return newReviewUser(publisher, subject, rating,
                new ArrayList<MdCommentList.MdComment>(),
                new ArrayList<MdImageList.MdImage>(),
                new ArrayList<MdFactList.MdFact>(),
                new ArrayList<MdLocationList.MdLocation>(),
                new MdIdableList<Review>(), false);
    }

    private Review newReviewUser(ReviewPublisher publisher, String subject, float rating,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations,
                                 MdIdableList<Review> criteria, boolean ratingIsAverage) {
        DataAuthor author = publisher.getAuthor();
        DataDate date = publisher.getDate();
        MdReviewId id = new MdReviewId(author.getUserId(), date.getTime(), publisher.getIncrement());
        return newReviewUser(id, author, date, subject, rating,
                mConverter.toMdCommentList(comments, id),
                mConverter.toMdImageList(images, id),
                mConverter.toMdFactList(facts, id),
                mConverter.toMdLocationList(locations, id),
                criteria,
                ratingIsAverage);
    }

    //Overridden
    @Override
    public Review createUserReview(ReviewDataHolder review) {
        return newReviewUser(review.getId(), review.getAuthor(), review.getPublishDate(),
                review.getSubject(), review.getRating(), review.getComments(), review.getImages(),
                review.getFacts(), review.getLocations(), review.getCritList(), review.isAverage());
    }
}
