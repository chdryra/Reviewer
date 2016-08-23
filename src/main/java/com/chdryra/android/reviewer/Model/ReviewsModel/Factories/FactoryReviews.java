/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewReferenceWrapper;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewMaker;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewNodeAuthoredFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewNodeRepo;

import java.util.ArrayList;

/**
 * Factory for creating Reviews and ReviewNodes.
 * <p/>
 * <p>
 * A convenient place to
 * put constructors so as to minimise the use of constructors in multiple places.
 * </p>
 */
public class FactoryReviews implements ReviewMaker {
    private AuthorsStamp mAuthorsStamp;
    private FactoryReviewNode mNodeFactory;
    private FactoryMdReference mReferenceFactory;

    public FactoryReviews(FactoryMdReference referenceFactory, AuthorsStamp authorsStamp) {
        mNodeFactory = new FactoryReviewNode(this, referenceFactory);
        mReferenceFactory = referenceFactory;
        mAuthorsStamp = authorsStamp;
    }

    public FactoryReviewNode getNodeFactory() {
        return mNodeFactory;
    }

    public void setAuthorsStamp(AuthorsStamp authorsStamp) {
        mAuthorsStamp = authorsStamp;
    }

    public Review createUserReview(String subject, float rating,
                                   Iterable<? extends DataCriterion> criteria,
                                   Iterable<? extends DataComment> comments,
                                   Iterable<? extends DataImage> images,
                                   Iterable<? extends DataFact> facts,
                                   Iterable<? extends DataLocation> locations,
                                   boolean ratingIsAverage) {
        return newReviewUser(subject, rating, criteria, comments,
                images, facts, locations, ratingIsAverage);
    }

    public ReviewNodeComponent createTree(ReviewReference review) {
        return mNodeFactory.createMetaTree(review);
    }

    public ReviewNodeComponent createTree(Iterable<ReviewReference> reviews, String subject) {
        return mNodeFactory.createMetaTree(createUserReview(subject, 0f), reviews);
    }

    public ReviewNodeComponent createLeafNode(ReviewReference reference) {
        return mNodeFactory.createLeafNode(reference);
    }

    public ReviewNodeRepo createAuthorsTree(AuthorId authorId, ApplicationInstance app) {
        String title = "Fetching...";

        ReviewStamp stamp = ReviewStamp.newStamp(authorId);
        ReviewInfo info = new com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo(stamp,
                new DatumSubject(stamp, title),
                new DatumRating(stamp, 0f, 1),
                new DatumAuthorId(stamp, stamp.getAuthorId().toString()),
                new DatumDate(stamp, stamp.getDate().getTime()));

        return new ReviewNodeAuthoredFeed(info, app, mReferenceFactory, getNodeFactory());
    }

    public ReviewReference asReference(Review review, TagsManager manager) {
        return new ReviewReferenceWrapper(review, manager, mReferenceFactory);
    }

    @Override
    public Review makeReview(ReviewDataHolder reviewData) {
        return newReviewUser(reviewData.getReviewId(), reviewData.getAuthorId(),
                reviewData.getPublishDate(), reviewData.getSubject(), reviewData.getRating(),
                reviewData.getCriteria(), reviewData.getComments(), reviewData.getImages(),
                reviewData.getFacts(), reviewData.getLocations());
    }

    /********************************************************/
    //private methods
    private Review createUserReview(String subject, float rating) {
        return newReviewUser(subject, rating,
                new ArrayList<DataCriterion>(),
                new ArrayList<DataComment>(),
                new ArrayList<DataImage>(),
                new ArrayList<DataFact>(),
                new ArrayList<DataLocation>(), false);
    }

    private Review newReviewUser(String subject, float rating,
                                 Iterable<? extends DataCriterion> criteria,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations,
                                 boolean ratingIsAverage) {
        if (mAuthorsStamp == null) throw new IllegalStateException("No author stamp!");

        ReviewStamp stamp = newStamp();
        AuthorId author = stamp.getAuthorId();
        DateTime date = stamp.getDate();

        if (ratingIsAverage) rating = getAverageRating(criteria);

        return newReviewUser(stamp, author, date, subject, rating, criteria, comments, images, facts,
                locations);
    }

    private ReviewStamp newStamp() {
        return mAuthorsStamp.newStamp();
    }

    private float getAverageRating(Iterable<? extends DataCriterion> criteria) {
        float rating;
        double average = 0;
        int size = 0;
        for (DataCriterion criterion : criteria) {
            average += criterion.getRating();
            size++;
        }
        rating = (float) average / (float) size;
        return rating;
    }

    private Review newReviewUser(ReviewId id,
                                 AuthorId authorId,
                                 DateTime publishDate,
                                 String subject,
                                 float rating,
                                 Iterable<? extends DataCriterion> criteria,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations) {
        DataAuthorId mdAuthor = new DatumAuthorId(id, authorId.toString());
        DataDate mdDate = new DatumDate(id, publishDate.getTime());
        DataSubject mdSubject = new DatumSubject(id, subject);
        DataRating mdRating = new DatumRating(id, rating, 1);
        
        IdableList<DataComment> mdComments = new IdableDataList<>(id);
        for(DataComment datum : comments) {
            mdComments.add(new DatumComment(id, datum.getComment(), datum.isHeadline()));
        }

        IdableList<DataCriterion> mdCriteria = new IdableDataList<>(id);
        for(DataCriterion datum : criteria) {
            mdCriteria.add(new DatumCriterion(id, datum.getSubject(), datum.getRating()));
        }

        IdableList<DataImage> mdImages = new IdableDataList<>(id);
        for(DataImage datum : images) {
            mdImages.add(new DatumImage(id, datum.getBitmap(), datum.getDate(), datum.getCaption(), datum.isCover()));
        }

        IdableList<DataFact> mdFacts = new IdableDataList<>(id);
        for(DataFact datum : facts) {
            mdFacts.add(new DatumFact(id, datum.getValue(), datum.getLabel(), datum.isUrl()));
        }

        IdableList<DataLocation> mdLocations = new IdableDataList<>(id);
        for(DataLocation datum : locations) {
            mdLocations.add(new DatumLocation(id, datum.getLatLng(), datum.getName()));
        }

        return new ReviewUser(id, mdAuthor, mdDate, mdSubject, mdRating, mdComments,
                mdImages, mdFacts, mdLocations, mdCriteria);
    }
}
