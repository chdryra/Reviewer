/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Database.Interfaces.BuilderReview;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdAuthor;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDate;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdRating;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdSubject;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;

import java.util.ArrayList;

/**
 * Factory for creating Reviews and ReviewNodes.
 * <p/>
 * <p>
 * A convenient place to
 * put constructors so as to minimise the use of constructors in multiple places.
 * </p>
 */
public class FactoryReviews implements BuilderReview {
    private FactoryReviewPublisher mPublisherFactory;
    private FactoryReviewNode mNodeFactory;
    private ConverterMd mConverter;

    public FactoryReviews(FactoryReviewPublisher publisherFactory,
                          FactoryReviewNode nodeFactory,
                          ConverterMd converter) {
        mPublisherFactory = publisherFactory;
        mNodeFactory = nodeFactory;
        mConverter = converter;
    }

    public Review createUserReview(String subject,
                                   float rating,
                                   Iterable<? extends DataComment> comments,
                                   Iterable<? extends DataImage> images,
                                   Iterable<? extends DataFact> facts,
                                   Iterable<? extends DataLocation> locations,
                                   IdableCollection<Review> criteria, boolean ratingIsAverage) {
        return newReviewUser(subject, rating, comments,
                images, facts, locations, criteria, ratingIsAverage);
    }

    public Review createUserReview(String subject, float rating) {
        return newReviewUser(subject, rating);
    }

    public ReviewNode createMetaReview(Review review) {
        MdDataList<Review> single = new MdDataList<>(null);
        single.add(review);

        return createMetaReview(single, review.getSubject().getSubject());
    }

    public ReviewNode createMetaReview(Iterable<Review> reviews,
                                   String subject) {
        return mNodeFactory.createReviewNode(createMetaReviewMutable(reviews, subject));
    }

    public ReviewNodeComponent createMetaReviewMutable(Iterable<Review> reviews, String subject) {
        Review meta = createUserReview(subject, 0f);
        ReviewNodeComponent parent = mNodeFactory.createReviewNodeComponent(meta, true);
        for (Review review : reviews) {
            ReviewNodeComponent child = mNodeFactory.createReviewNodeComponent(review, false);
            parent.addChild(child);
        }

        return parent;
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
                                 Iterable<Review> criteria,
                                 boolean ratingIsAverage) {
        MdAuthor mdAuthor = new MdAuthor(id, author.getName(), author.getUserId());
        MdDate mdDate = new MdDate(id, publishDate.getTime());
        MdSubject mdSubject = new MdSubject(id, subject);
        MdRating mdRating = new MdRating(id, rating, 1);
        MdDataList<MdComment> mdComments = mConverter.toMdCommentList(comments, id.toString());
        MdDataList<MdImage> mdImages = mConverter.toMdImageList(images, id.toString());
        MdDataList<MdFact> mdFacts = mConverter.toMdFactList(facts, id.toString());
        MdDataList<MdLocation> mdLocations = mConverter.toMdLocationList(locations, id.toString());
        MdDataList<MdCriterion> mdCriteria = mConverter.reviewsToMdCriterionList(criteria, id
                .toString());

        return new ReviewUser(id, mdAuthor, mdDate, mdSubject, mdRating, mdComments,
                mdImages, mdFacts, mdLocations, mdCriteria, ratingIsAverage, mNodeFactory);
    }

    private Review newReviewUser(String subject, float rating) {
        return newReviewUser(subject, rating,
                new ArrayList<MdComment>(),
                new ArrayList<MdImage>(),
                new ArrayList<MdFact>(),
                new ArrayList<MdLocation>(),
                new MdDataList<Review>(null), false);
    }

    private Review newReviewUser(String subject, float rating,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations,
                                 Iterable<Review> criteria, boolean ratingIsAverage) {
        ReviewPublisher publisher = mPublisherFactory.newPublisher();
        DataAuthor author = publisher.getAuthor();
        DataDate date = publisher.getDate();
        MdReviewId id = new MdReviewId(author.getUserId(), date.getTime(), publisher.getPublishedIndex());
        if (ratingIsAverage) {
            Review meta = createMetaReview(criteria, "");
            rating = meta.getRating().getRating();
        }

        return newReviewUser(id, author, date, subject, rating,
                mConverter.toMdCommentList(comments, id.toString()),
                mConverter.toMdImageList(images, id.toString()),
                mConverter.toMdFactList(facts, id.toString()),
                mConverter.toMdLocationList(locations, id.toString()),
                criteria,
                ratingIsAverage);
    }

    public ReviewNodeComponent createReviewNodeComponent(Review review, boolean isAverage) {
        return mNodeFactory.createReviewNodeComponent(review, isAverage);
    }

    //Overridden
    @Override
    public Review createReview(ReviewDataHolder review) {
        return newReviewUser(new MdReviewId(review.getId()), review.getAuthor(), review.getPublishDate(),
                review.getSubject(), review.getRating(), review.getComments(), review.getImages(),
                review.getFacts(), review.getLocations(), review.getCriteria(), review.isAverage());
    }
}
