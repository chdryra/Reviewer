/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Database.Interfaces.BuilderReview;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNodeComponent;
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
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNodeComponent;

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
    private FactoryReviewNodeComponent mComponentFactory;
    private ConverterMd mConverter;

    //Constructors
    public FactoryReviews(FactoryReviewPublisher publisherFactory,
                          FactoryReviewNodeComponent componentFactory,
                          ConverterMd converter) {
        mPublisherFactory = publisherFactory;
        mComponentFactory = componentFactory;
        mConverter = converter;
    }

    public DataAuthor getAuthor() {
        return mPublisherFactory.getAuthor();
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

    public Review createMetaReview(Review review) {
        MdDataList<Review> single = new MdDataList<>(null);
        single.add(review);

        return createMetaReview(single, review.getSubject().getSubject());
    }

    public Review createMetaReview(Iterable<Review> reviews,
                                   String subject) {
        Review meta = createUserReview(subject, 0f);
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
        MdDataList<MdCriterion> mdCriteria = mConverter.reviewsToMdCriterionList(criteria, id.toString());

        return new ReviewUser(id, mdAuthor, mdDate, mdSubject, mdRating, mdComments,
                mdImages, mdFacts, mdLocations, mdCriteria, ratingIsAverage, mComponentFactory);
    }

    private Review newReviewUser(String subject, float
            rating) {
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
        return mComponentFactory.createReviewNodeComponent(review, isAverage);
    }

    //Overridden
    @Override
    public Review createReview(ReviewDataHolder review) {
        return newReviewUser(new MdReviewId(review.getId()), review.getAuthor(), review.getPublishDate(),
                review.getSubject(), review.getRating(), review.getComments(), review.getImages(),
                review.getFacts(), review.getLocations(), review.getCriteria(), review.isAverage());
    }
}
