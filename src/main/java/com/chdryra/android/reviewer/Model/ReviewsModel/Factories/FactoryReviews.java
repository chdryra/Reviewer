/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewReferenceWrapper;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewMaker;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewNodeAuthoredFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewNodeRepo;
import com.google.android.gms.maps.model.LatLng;

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
        DataReviewInfo info = new com.chdryra.android.reviewer.Model.ReviewsModel.Implementation
                .ReviewInfo(stamp,
                new DatumSubject(stamp, title),
                new DatumRating(stamp, 0f, 1),
                new DatumAuthorId(stamp, stamp.getAuthorId().toString()),
                new DatumDate(stamp, stamp.getDate().getTime()));

        return new ReviewNodeAuthoredFeed(info, app, mReferenceFactory, getNodeFactory());
    }

    public ReviewReference asReference(Review review, TagsManager manager) {
        return new ReviewReferenceWrapper(review, manager, mReferenceFactory.getReferenceFactory());
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

        return newReviewUser(stamp, author, date, subject, rating, criteria, comments, images,
                facts,
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

        IdableList<DataComment> mdComments = getComments(id, comments);
        IdableList<DataCriterion> mdCriteria = getCriteria(id, criteria);
        IdableList<DataLocation> mdLocations = getLocations(id, locations);
        IdableList<DataImage> mdImages = getImages(id, images, mdDate, mdLocations);
        IdableList<DataFact> mdFacts = getFacts(id, facts);

        return new ReviewUser(id, mdAuthor, mdDate, mdSubject, mdRating, mdComments,
                mdImages, mdFacts, mdLocations, mdCriteria);
    }

    @NonNull
    private IdableList<DataFact> getFacts(ReviewId id, Iterable<? extends DataFact> facts) {
        IdableList<DataFact> mdFacts = new IdableDataList<>(id);
        for (DataFact datum : facts) {
            mdFacts.add(new DatumFact(id, datum.getValue(), datum.getLabel(), datum.isUrl()));
        }
        return mdFacts;
    }

    @NonNull
    private IdableList<DataImage> getImages(ReviewId id, Iterable<? extends DataImage> images,
                                            DataDate mdDate, IdableList<DataLocation> mdLocations) {
        DataImage cover = null;
        int coverIndex = 0;
        for (DataImage datum : images) {
            cover = datum;
            if (datum.isCover()) {
                cover = datum;
                break;
            }
            coverIndex++;
        }

        IdableList<DataImage> mdImages = new IdableDataList<>(id);
        if (cover != null) mdImages.add(getDatumImage(id, mdDate, mdLocations, cover, true));
        int index = 0;
        for (DataImage datum : images) {
            if (index == coverIndex) continue;
            DatumImage datumImage = getDatumImage(id, mdDate, mdLocations, datum);
            mdImages.add(datumImage);
        }
        return mdImages;
    }

    @NonNull
    private DatumImage getDatumImage(ReviewId id, DataDate mdDate, IdableList<DataLocation>
            mdLocations, DataImage datum) {
        return getDatumImage(id, mdDate, mdLocations, datum, datum.isCover());
    }

    private DatumImage getDatumImage(ReviewId id, DataDate mdDate, IdableList<DataLocation>
            mdLocations, DataImage datum, boolean isCover) {
        DateTime date = datum.getDate();
        if (date.getTime() == 0) date = mdDate;
        LatLng latLng = datum.getLatLng();
        if (latLng == null && mdLocations.size() > 0) latLng = mdLocations.getItem(0).getLatLng();

        return new DatumImage(id, datum.getBitmap(), date, datum.getCaption(), latLng, isCover);
    }

    @NonNull
    private IdableList<DataLocation> getLocations(ReviewId id, Iterable<? extends DataLocation>
            locations) {
        IdableList<DataLocation> mdLocations = new IdableDataList<>(id);
        for (DataLocation datum : locations) {
            DatumLocation loc = new DatumLocation(id, datum.getLatLng(), datum.getName());
            mdLocations.add(loc);
        }
        return mdLocations;
    }

    @NonNull
    private IdableList<DataCriterion> getCriteria(ReviewId id, Iterable<? extends DataCriterion>
            criteria) {
        IdableList<DataCriterion> mdCriteria = new IdableDataList<>(id);
        for (DataCriterion datum : criteria) {
            mdCriteria.add(new DatumCriterion(id, datum.getSubject(), datum.getRating()));
        }
        return mdCriteria;
    }

    @NonNull
    private IdableList<DataComment> getComments(ReviewId id, Iterable<? extends DataComment>
            comments) {
        IdableList<DataComment> mdComments = new IdableDataList<>(id);
        DataComment headline = null;
        int headlineIndex = 0;
        for (DataComment datum : comments) {
            headline = datum;
            if (datum.isHeadline()) {
                headline = datum;
                break;
            }
            headlineIndex++;
        }

        if (headline != null) mdComments.add(new DatumComment(id, headline.getComment(), true));
        int index = 0;
        for (DataComment datum : comments) {
            if (index == headlineIndex) continue;
            mdComments.add(new DatumComment(id, datum.getComment(), datum.isHeadline()));
        }

        return mdComments;
    }
}
