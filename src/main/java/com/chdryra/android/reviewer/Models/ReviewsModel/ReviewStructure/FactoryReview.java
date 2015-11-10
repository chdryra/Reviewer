/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.ConverterMd;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewPublisher;
import com.chdryra.android.reviewer.Database.ReviewDataHolder;
import com.chdryra.android.reviewer.Interfaces.Data.DataAuthor;
import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataDate;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdAuthor;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCommentList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCriterionList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdDate;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdImageList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdRating;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdSubject;

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
    private ConverterMd mConverter;

    //Constructors
    public FactoryReview(FactoryReviewNodeComponent componentFactory, ConverterMd converter) {
        mComponentFactory = componentFactory;
        mConverter = converter;
    }

    public Review createUserReview(ReviewPublisher publisher, String subject,
                                   float rating,
                                   Iterable<? extends DataComment> comments,
                                   Iterable<? extends DataImage> images,
                                   Iterable<? extends DataFact> facts,
                                   Iterable<? extends DataLocation> locations,
                                   MdIdableCollection<Review> criteria, boolean ratingIsAverage) {
        return newReviewUser(publisher, subject, rating, comments,
                images, facts, locations, criteria, ratingIsAverage);
    }

    public Review createUserReview(ReviewPublisher publisher, String subject, float rating) {
        return newReviewUser(publisher, subject, rating);
    }

    public Review createMetaReview(ReviewPublisher publisher, Review review) {
        MdIdableCollection<Review> single = new MdIdableCollection<>();
        single.add(review);

        return createMetaReview(single, publisher, review.getSubject().getSubject());
    }

    public Review createMetaReview(Iterable<Review> reviews, ReviewPublisher publisher,
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
                                 Iterable<Review> criteria,
                                 boolean ratingIsAverage) {
        if (ratingIsAverage) {
            ReviewPublisher publisher = new ReviewPublisher(author, publishDate);
            Review meta = createMetaReview(criteria, publisher, "");
            rating = meta.getRating().getRating();
        }

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

    private Review newReviewUser(ReviewPublisher publisher, String subject, float
            rating) {
        return newReviewUser(publisher, subject, rating,
                new ArrayList<MdCommentList.MdComment>(),
                new ArrayList<MdImageList.MdImage>(),
                new ArrayList<MdFactList.MdFact>(),
                new ArrayList<MdLocationList.MdLocation>(),
                new MdIdableCollection<Review>(), false);
    }

    private Review newReviewUser(ReviewPublisher publisher, String subject, float rating,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations,
                                 Iterable<Review> criteria, boolean ratingIsAverage) {
        DataAuthor author = publisher.getAuthor();
        DataDate date = publisher.getDate();
        MdReviewId id = new MdReviewId(author.getUserId(), date.getTime(), publisher.getIncrement());
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
    public Review createUserReview(ReviewDataHolder review) {
        return newReviewUser(review.getId(), review.getAuthor(), review.getPublishDate(),
                review.getSubject(), review.getRating(), review.getComments(), review.getImages(),
                review.getFacts(), review.getLocations(), review.getCritList(), review.isAverage());
    }
}
