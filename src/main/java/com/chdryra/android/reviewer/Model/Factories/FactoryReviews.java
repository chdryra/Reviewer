/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Interfaces.ReviewRecreater;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils.ReviewDataHolder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdAuthor;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDataCollection;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDate;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdRating;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewStamp;

import java.util.ArrayList;

/**
 * Factory for creating Reviews and ReviewNodes.
 * <p/>
 * <p>
 * A convenient place to
 * put constructors so as to minimise the use of constructors in multiple places.
 * </p>
 */
public class FactoryReviews implements ReviewRecreater {
    private AuthorsStamp mAuthorsStamp;
    private FactoryReviewNode mNodeFactory;
    private ConverterMd mConverter;
    private DataValidator mValidator;
    private static Review sNullReview;

    public FactoryReviews(FactoryReviewNode nodeFactory,
                          ConverterMd converter,
                          DataValidator validator) {
        mNodeFactory = nodeFactory;
        mConverter = converter;
        mValidator = validator;
    }

    public void setAuthorsStamp(AuthorsStamp authorsStamp) {
        mAuthorsStamp = authorsStamp;
    }

    @Nullable
    public DataAuthor getAuthor() {
        return mAuthorsStamp != null ? mAuthorsStamp.getAuthor() : null;
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

    public ReviewNodeMutable createMetaReviewMutable(Iterable<Review> reviews, String subject) {
        Review meta = createUserReview(subject, 0f);
        ReviewNodeMutable parent = mNodeFactory.createReviewNodeComponent(meta, true);
        for (Review review : reviews) {
            parent.addChild(createReviewNodeComponent(review, false));
        }

        return parent;
    }

    public ReviewNodeMutable createReviewNodeComponent(Review review, boolean isAverage) {
        return mNodeFactory.createReviewNodeComponent(review, isAverage);
    }

    @Override
    public Review recreateReview(ReviewDataHolder review) {
        if(!review.isValid(mValidator)) return sNullReview;

        Iterable<? extends DataCriterion> criteria = review.getCriteria();
        ArrayList<Review> critList = new ArrayList<>();
        for(DataCriterion criterion : criteria) {
            critList.add(createUserReview(criterion.getSubject(), criterion.getRating()));
        }

        return newReviewUser(new MdReviewId(review.getReviewId()), review.getAuthor(),
                review.getPublishDate(), review.getSubject(), review.getRating(),
                review.getComments(), review.getImages(), review.getFacts(), review.getLocations(),
                critList, review.isAverage());
    }

    public Review getNullReview() {
        if(sNullReview == null) sNullReview = createUserReview("NULL_REVIEW", 0f);
        return sNullReview;
    }

    public ReviewNode getNullNode() {
        return getNullReview().getTreeRepresentation();
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
        if(mAuthorsStamp == null) throw new IllegalStateException("No author stamp!");
        
        ReviewStamp stamp = mAuthorsStamp.newStamp();
        DataAuthor author = stamp.getAuthor();
        DataDate date = stamp.getDate();

        MdReviewId id = new MdReviewId(author.getUserId().toString(),
                date.getTime(), stamp.getPublishedIndex());

        if (ratingIsAverage) {
            Review meta = createMetaReview(criteria, "");
            rating = meta.getRating().getRating();
        }

        return newReviewUser(id, author, date, subject, rating,
                comments,
                images,
                facts,
                locations,
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
