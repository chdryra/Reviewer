/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Models.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.ConverterMd;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryReviewPublisher;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewPublisher;
import com.chdryra.android.reviewer.Database.Interfaces.BuilderReview;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdAuthor;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdCommentList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdDate;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdImageList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdRating;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdSubject;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Models.UserModel.Author;

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

    public Author getAuthor() {
        return mPublisherFactory.getAuthor();
    }

    public FactoryReviewNodeComponent getComponentFactory() {
        return mComponentFactory;
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
        MdIdableCollection<Review> single = new MdIdableCollection<>();
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
        MdCommentList mdComments = mConverter.toMdCommentList(comments, id.toString());
        MdImageList mdImages = mConverter.toMdImageList(images, id.toString());
        MdFactList mdFacts = mConverter.toMdFactList(facts, id.toString());
        MdLocationList mdLocations = mConverter.toMdLocationList(locations, id.toString());
        MdCriterionList mdCriteria = mConverter.reviewsToMdCriterionList(criteria, id.toString());

        return new ReviewUser(id, mdAuthor, mdDate, mdSubject, mdRating, mdComments,
                mdImages, mdFacts, mdLocations, mdCriteria, ratingIsAverage, mComponentFactory);
    }

    private Review newReviewUser(String subject, float
            rating) {
        return newReviewUser(subject, rating,
                new ArrayList<MdCommentList.MdComment>(),
                new ArrayList<MdImageList.MdImage>(),
                new ArrayList<MdFactList.MdFact>(),
                new ArrayList<MdLocationList.MdLocation>(),
                new MdIdableCollection<Review>(), false);
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

    //Overridden
    @Override
    public Review createReview(ReviewDataHolder review) {
        return newReviewUser(new MdReviewId(review.getId()), review.getAuthor(), review.getPublishDate(),
                review.getSubject(), review.getRating(), review.getComments(), review.getImages(),
                review.getFacts(), review.getLocations(), review.getCriteria(), review.isAverage());
    }
}
