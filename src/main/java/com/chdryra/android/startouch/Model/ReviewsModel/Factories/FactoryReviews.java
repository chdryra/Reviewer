/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewDataHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewStamper;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorRef;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.NodeTitler;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewInfo;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewReferenceWrapper;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewMaker;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewNodeRepo;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View
        .ReviewNodeRepoTitler;
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
    private final FactoryReviewNode mNodeFactory;
    private final FactoryDataReference mReferenceFactory;
    private ReviewStamper mStamper;

    public FactoryReviews(FactoryReviewNode nodeFactory, FactoryDataReference referenceFactory) {
        mNodeFactory = nodeFactory;
        mReferenceFactory = referenceFactory;
    }

    public void setReviewStamper(ReviewStamper stamper) {
        mStamper = stamper;
    }

    public Review createUserReview(@Nullable ReviewId reviewId,
                                   String subject, float rating,
                                   Iterable<? extends DataTag> tags,
                                   Iterable<? extends DataCriterion> criteria,
                                   Iterable<? extends DataComment> comments,
                                   Iterable<? extends DataImage> images,
                                   Iterable<? extends DataFact> facts,
                                   Iterable<? extends DataLocation> locations,
                                   boolean ratingIsAverage) {
        rating = ratingIsAverage ? getRating(criteria) : rating;
        ReviewStamp stamp = reviewId != null ? ReviewStamp.newStamp(reviewId) : newStamp();
        return newReviewUser(stamp, subject, rating, tags, criteria, comments, images,
                facts, locations);
    }

    public ReviewNodeComponent createTree(ReviewReference review) {
        return mNodeFactory.createMetaTree(review);
    }

    public ReviewNodeComponent createTree(Iterable<ReviewReference> reviews, String subject) {
        return mNodeFactory.createMetaTree(createPlaceholderReview(null, subject), reviews);
    }

    public ReviewNodeComponent createLeafNode(ReviewReference reference) {
        return mNodeFactory.createLeafNode(reference);
    }

    public ReviewNode createTree(ReviewsRepoReadable repo, AuthorRef treeOwner, String
            title) {
        return newReviewNodeAuthored(getMetaInfo(treeOwner.getAuthorId()), repo,
                new NodeTitler.AuthorsTree(treeOwner, title));
    }

    public ReviewReference asReference(Review review) {
        return new ReviewReferenceWrapper(review, mReferenceFactory.getReferenceFactory());
    }

    public Review createPlaceholderReview(@Nullable ReviewId reviewId, String subject) {
        return newReviewUser(reviewId != null ? ReviewStamp.newStamp(reviewId) : newStamp(),
                subject, 0f,
                new ArrayList<DataTag>(),
                new ArrayList<DataCriterion>(),
                new ArrayList<DataComment>(),
                new ArrayList<DataImage>(),
                new ArrayList<DataFact>(),
                new ArrayList<DataLocation>());
    }

    @Override
    public Review makeReview(ReviewDataHolder reviewData) {
        return newReviewUser(reviewData.getReviewId(), reviewData.getAuthorId(),
                reviewData.getPublishDate(), reviewData.getSubject(), reviewData.getRating(),
                reviewData.getTags(), reviewData.getCriteria(), reviewData.getComments(),
                reviewData.getImages(),
                reviewData.getFacts(), reviewData.getLocations());
    }

    private Review newReviewUser(ReviewStamp stamp,
                                 String subject,
                                 float rating,
                                 Iterable<? extends DataTag> tags,
                                 Iterable<? extends DataCriterion> criteria,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations) {
        return newReviewUser(stamp, stamp.getAuthorId(), stamp.getDate(), subject, rating,
                tags, criteria, comments, images, facts, locations);
    }

    private Review newReviewUser(ReviewId id,
                                 AuthorId authorId,
                                 DateTime publishDate,
                                 String subject,
                                 float rating,
                                 Iterable<? extends DataTag> tags,
                                 Iterable<? extends DataCriterion> criteria,
                                 Iterable<? extends DataComment> comments,
                                 Iterable<? extends DataImage> images,
                                 Iterable<? extends DataFact> facts,
                                 Iterable<? extends DataLocation> locations) {
        DataAuthorId mdAuthor = new DatumAuthorId(id, authorId.toString());
        DataDate mdDate = new DatumDate(id, publishDate.getTime());
        DataSubject mdSubject = new DatumSubject(id, subject);
        DataRating mdRating = new DatumRating(id, rating, 1);

        IdableList<DataTag> mdTags = getTags(id, tags);
        IdableList<DataComment> mdComments = getComments(id, comments);
        IdableList<DataCriterion> mdCriteria = getCriteria(id, criteria);
        IdableList<DataLocation> mdLocations = getLocations(id, locations);
        IdableList<DataImage> mdImages = getImages(id, images, mdDate, mdLocations);
        IdableList<DataFact> mdFacts = getFacts(id, facts);

        return new ReviewUser(id, mdAuthor, mdDate, mdSubject, mdRating, mdTags, mdComments,
                mdImages, mdCriteria, mdFacts, mdLocations);
    }

    private ReviewStamp newStamp() {
        return mStamper.newStamp();
    }

    @NonNull
    private ReviewNodeRepo newReviewNodeAuthored(DataReview info,
                                                 ReviewsRepoReadable reviews,
                                                 NodeTitler titler) {
        return new ReviewNodeRepoTitler(info, reviews, mReferenceFactory,
                mNodeFactory, titler);
    }

    @NonNull
    private DataReview getMetaInfo(AuthorId authorId) {
        ReviewStamp stamp = ReviewStamp.newStamp(authorId);
        return new ReviewInfo(stamp,
                new DatumSubject(stamp, Strings.Progress.FETCHING),
                new DatumRating(stamp, 0f, 1),
                new DatumAuthorId(stamp, stamp.getAuthorId().toString()),
                new DatumDate(stamp, stamp.getDate().getTime()));
    }

    /********************************************************/
    private float getRating(Iterable<? extends DataCriterion> criteria) {
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

    @NonNull
    private IdableList<DataTag> getTags(ReviewId id, Iterable<? extends DataTag> tags) {
        IdableList<DataTag> mdTags = new IdableDataList<>(id);
        for (DataTag datum : tags) {
            mdTags.add(new DatumTag(id, datum.getTag()));
        }
        return mdTags;
    }

    @NonNull
    private IdableList<DataFact> getFacts(ReviewId id, Iterable<? extends DataFact> facts) {
        IdableList<DataFact> mdFacts = new IdableDataList<>(id);
        for (DataFact datum : facts) {
            mdFacts.add(new DatumFact(id, datum.getLabel(), datum.getValue(), datum.isUrl()));
        }
        return mdFacts;
    }

    @NonNull
    private IdableList<DataImage> getImages(ReviewId id, Iterable<? extends DataImage> images,
                                            DataDate mdDate, IdableList<DataLocation> mdLocations) {
        DataImage cover = null;
        for (DataImage datum : images) {
            cover = datum;
            if (datum.isCover()) break;
        }

        IdableList<DataImage> mdImages = new IdableDataList<>(id);
        if (cover != null) mdImages.add(getDatumImage(id, mdDate, mdLocations, cover, true));
        for (DataImage datum : images) {
            if (datum.equals(cover)) continue;
            mdImages.add(getDatumImage(id, mdDate, mdLocations, datum));
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
        if (latLng == null && mdLocations.size() > 0) latLng = mdLocations.get(0).getLatLng();

        return new DatumImage(id, datum.getBitmap(), date, datum.getCaption(), latLng, isCover);
    }

    @NonNull
    private IdableList<DataLocation> getLocations(ReviewId id, Iterable<? extends DataLocation>
            locations) {
        IdableList<DataLocation> mdLocations = new IdableDataList<>(id);
        for (DataLocation datum : locations) {
            DatumLocation loc = new DatumLocation(id, datum.getLatLng(), datum.getName(),
                    datum.getAddress(), datum.getLocationId());
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
            if (index++ == headlineIndex) continue;
            mdComments.add(new DatumComment(id, datum.getComment(), datum.isHeadline()));
        }

        return mdComments;
    }
}
