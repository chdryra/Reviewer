/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.FactoryReviewFromDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdAuthor;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataCollection;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDate;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdRating;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdSubject;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewPublisher;

import java.util.ArrayList;

/**
 * Factory for creating Reviews and ReviewNodes.
 * <p/>
 * <p>
 * A convenient place to
 * put constructors so as to minimise the use of constructors in multiple places.
 * </p>
 */
public class FactoryReviews implements FactoryReviewFromDataHolder {
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

    public DataAuthor getAuthor() {
        return mPublisherFactory.getAuthor();
    }

    public Review createUserReview(String subject, float rating,
                                   Iterable<? extends DataComment> comments,
                                   Iterable<? extends DataImage> images,
                                   Iterable<? extends DataFact> facts,
                                   Iterable<? extends DataLocation> locations,
                                   Iterable<Review> criteria, boolean ratingIsAverage) {
        return newReviewUser(subject, rating, comments,
                images, facts, locations, criteria, ratingIsAverage);
    }

    public ReviewNode createMetaReview(Review review) {
        IdableCollection<Review> single = new MdDataCollection<>();
        single.add(review);

        return createMetaReview(single, review.getSubject().getSubject());
    }

    public ReviewNode createMetaReview(Iterable<Review> reviews, String subject) {
        return mNodeFactory.createReviewNode(createMetaReviewMutable(reviews, subject));
    }

    public ReviewNodeComponent createMetaReviewMutable(Iterable<Review> reviews, String subject) {
        Review meta = createUserReview(subject, 0f);
        ReviewNodeComponent parent = mNodeFactory.createReviewNodeComponent(meta, true);
        for (Review review : reviews) {
            parent.addChild(createReviewNodeComponent(review, false));
        }

        return parent;
    }

    public ReviewNodeComponent createReviewNodeComponent(Review review, boolean isAverage) {
        return mNodeFactory.createReviewNodeComponent(review, isAverage);
    }

    @Override
    public Review recreateReview(ReviewDataHolder review) {
        return newReviewUser(new MdReviewId(review.getReviewId()), review.getAuthor(),
                review.getPublishDate(), review.getSubject(), review.getRating(),
                review.getComments(), review.getImages(), review.getFacts(), review.getLocations(),
                review.getCriteria(), review.isAverage());
    }

    /********************************************************/
    //private methods
    private Review createUserReview(String subject, float rating) {
        return newReviewUser(subject, rating,
                new ArrayList<MdComment>(),
                new ArrayList<MdImage>(),
                new ArrayList<MdFact>(),
                new ArrayList<MdLocation>(),
                new MdDataCollection<Review>(), false);
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
        MdReviewId id = new MdReviewId(author.getUserId().toString(),
                date.getTime(), publisher.getPublishedIndex());
        if (ratingIsAverage) {
            Review meta = createMetaReview(criteria, "");
            rating = meta.getRating().getRating();
        }

        return newReviewUser(id, author, date, subject, rating,
                mConverter.toMdCommentList(comments, id),
                mConverter.toMdImageList(images, id),
                mConverter.toMdFactList(facts, id),
                mConverter.toMdLocationList(locations, id),
                criteria,
                ratingIsAverage);
    }

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
        MdDataList<MdComment> mdComments = mConverter.toMdCommentList(comments, id);
        MdDataList<MdImage> mdImages = mConverter.toMdImageList(images, id);
        MdDataList<MdFact> mdFacts = mConverter.toMdFactList(facts, id);
        MdDataList<MdLocation> mdLocations = mConverter.toMdLocationList(locations, id);
        MdDataList<MdCriterion> mdCriteria = mConverter.toMdCriterionList(criteria, id);

        return new ReviewUser(id, mdAuthor, mdDate, mdSubject, mdRating, mdComments,
                mdImages, mdFacts, mdLocations, mdCriteria, ratingIsAverage, mNodeFactory);
    }
}
