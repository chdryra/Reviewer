/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories;


import android.graphics.Bitmap;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Comment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Criterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Fact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ImageData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.LatitudeLongitude;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Location;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Rating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.NullReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewDataHolderImpl;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewMaker;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewConverter {
    private static final ReviewDataHolder NULL_REVIEW = new NullReviewDataHolder();
    private final BackendValidator mValidator;
    private final ReviewMaker mMaker;
    private final TagsManager mTagsManager;

    public BackendReviewConverter(BackendValidator validator, ReviewMaker maker, TagsManager tagsManager) {
        mValidator = validator;
        mMaker = maker;
        mTagsManager = tagsManager;
    }

    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    public ReviewDb convert(Review review) {
        return new ReviewDb(review, mTagsManager.getTags(review.getReviewId().toString()).toStringArray());
    }

    public Review convert(ReviewDb reviewDb) {
        ReviewDataHolder data = newReviewDataHolder(reviewDb, mTagsManager);
        mTagsManager.tagItem(reviewDb.getReviewId(), new ArrayList<>(reviewDb.getTags()));
        return mMaker.makeReview(data);
    }

    public Review getNullReview() {
        return mMaker.makeReview(NULL_REVIEW);
    }

    private ReviewDataHolder newReviewDataHolder(ReviewDb reviewDb, TagsManager tagsManager) {
        if(!mValidator.isValid(reviewDb)) return NULL_REVIEW;

        ReviewDataHolder reviewDataHolder = toReviewDataHolder(reviewDb);
        String reviewId = reviewDb.getReviewId();
        for(String tag : reviewDb.getTags()) {
            if(!tagsManager.tagsItem(reviewId, tag)) tagsManager.tagItem(reviewId, tag);
        }

        return reviewDataHolder;
    }

    public boolean isValid(ReviewDb review) {
        return mValidator.isValid(review);
    }

    private ReviewDataHolder toReviewDataHolder(ReviewDb review) {
        ReviewId reviewId = new DatumReviewId(review.getReviewId());
        AuthorId authorId = new AuthorIdParcelable(review.getAuthorId());

        DateTime date = new DatumDate(reviewId, review.getPublishDate());
        String subject = review.getSubject();
        Rating fbRating = review.getRating();
        float rating = (float)fbRating.getRating();
        int ratingWeight = (int)fbRating.getRatingWeight();

        ArrayList<DataCriterion> criteria = new ArrayList<>();
        for(Criterion criterion : review.getCriteria()) {
            criteria.add(new DatumCriterion(reviewId, criterion.getSubject(),
                    (float) criterion.getRating()));
        }

        ArrayList<DataComment> comments = new ArrayList<>();
        for(Comment comment : review.getComments()) {
            comments.add(new DatumComment(reviewId, comment.toComment(), comment.isHeadline()));
        }

        ArrayList<DataFact> facts = new ArrayList<>();
        for(Fact fact : review.getFacts()) {
            facts.add(new DatumFact(reviewId, fact.getLabel(), fact.getValue(), fact.isUrl()));
        }

        ArrayList<DataImage> images = new ArrayList<>();
        for(ImageData image : review.getImages()) {
            Bitmap bitmap = ImageData.asBitmap(image.getBitmap());
            images.add(new DatumImage(reviewId, bitmap, new DatumDate(reviewId,
                    image.getDate()), image.getCaption(), image.toLatLng(), image.isCover()));
        }

        ArrayList<DataLocation> locations = new ArrayList<>();
        for(Location location : review.getLocations()) {
            LatitudeLongitude latLng = location.getLatLng();
            locations.add(new DatumLocation(reviewId,
                    new LatLng(latLng.getLatitude(), latLng.getLongitude()), location.getName()));
        }

        return new ReviewDataHolderImpl(reviewId, authorId, date, subject,rating, ratingWeight,
                comments, images, facts, locations, criteria);
    }
}
