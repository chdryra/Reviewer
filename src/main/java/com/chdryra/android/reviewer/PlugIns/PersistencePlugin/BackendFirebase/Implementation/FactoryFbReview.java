/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils.NullReviewDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils.ReviewDataHolderImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils.ReviewDataHolder;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFbReview {
    private static final ReviewDataHolder NULL_REVIEW = new NullReviewDataHolder();
    private FirebaseValidator mValidator;

    public FactoryFbReview(FirebaseValidator validator) {
        mValidator = validator;
    }

    public FbReview newFbReview(Review review, TagsManager tagsManager) {
        return new FbReview(review, tagsManager);
    }

    public ReviewDataHolder newReviewDataHolder(FbReview fbReview, TagsManager tagsManager) {
        ReviewDataHolder reviewDataHolder = toReviewDataHolder(fbReview);
        String reviewId = fbReview.getReviewId();
        if(mValidator.validateString(reviewId)) {
            for(String tag : fbReview.getTags()) {
                if(!tagsManager.tagsItem(reviewId, tag)) tagsManager.tagItem(reviewId, tag);
            }
        }

        return reviewDataHolder;
    }

    public ReviewDataHolder nullReview() {
        return NULL_REVIEW;
    }

    private ReviewDataHolder toReviewDataHolder(FbReview review) {
        if(!mValidator.isValid(review)) return NULL_REVIEW;

        ReviewId reviewId = new DatumReviewId(review.getReviewId());

        Author fbAuthor = review.getAuthor();
        DatumUserId userId = new DatumUserId(fbAuthor.getUserId());
        DataAuthor author = new DatumAuthorReview(reviewId, fbAuthor.getName(), userId);

        DataDate date = new DatumDateReview(reviewId, review.getPublishDate());
        String subject = review.getSubject();
        Rating fbRating = review.getRating();
        float rating = (float)fbRating.getRating();
        int ratingWeight = (int)fbRating.getRatingWeight();
        boolean isAverage = review.isRatingAverageOfCriteria();

        ArrayList<DataCriterion> criteria = new ArrayList<>();
        for(Criterion criterion : review.getCriteria()) {
            criteria.add(new DatumCriterion(reviewId, criterion.getSubject(),
                    (float) criterion.getRating()));
        }

        ArrayList<DataComment> comments = new ArrayList<>();
        for(Comment comment : review.getComments()) {
            comments.add(new DatumComment(reviewId, comment.getComment(), comment.isHeadline()));
        }

        ArrayList<DataFact> facts = new ArrayList<>();
        for(Fact fact : review.getFacts()) {
            facts.add(new DatumFact(reviewId, fact.getLabel(), fact.getValue(), fact.isUrl()));
        }

        ArrayList<DataImage> images = new ArrayList<>();
        for(ImageData image : review.getImages()) {
            Bitmap bitmap = decodeBitmapString(image.getBitmap());
            images.add(new DatumImage(reviewId, bitmap, new DatumDateReview(reviewId,
                    image.getDate()), image.getCaption(), image.isCover()));
        }

        ArrayList<DataLocation> locations = new ArrayList<>();
        for(Location location : review.getLocations()) {
            LatitudeLongitude latLng = location.getLatLng();
            locations.add(new DatumLocation(reviewId,
                    new LatLng(latLng.getLatitude(), latLng.getLongitude()), location.getName()));
        }

        return new ReviewDataHolderImpl(reviewId, author, date, subject,rating, ratingWeight,
                comments, images, facts, locations, criteria, isAverage);
    }

    private Bitmap decodeBitmapString(String bitmapString) {
        byte[] imageAsBytes = Base64.decode(bitmapString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
