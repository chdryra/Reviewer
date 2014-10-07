/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.reviewer.GVReviewOverviewList.GVReviewOverview;

import java.text.DateFormat;

class VHReviewNodeOverview extends ViewHolderBasic {
    private static final int LAYOUT   = R.layout.grid_cell_review_overview;
    private static final int SUBJECT  = R.id.review_subject;
    private static final int RATING   = R.id.review_rating_bar;
    private static final int IMAGE    = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int LOCATION = R.id.review_location;
    private static final int PUBLISH  = R.id.review_publish_data;

    private TextView  mSubject;
    private RatingBar mRating;
    private ImageView mImage;
    private TextView  mHeadline;
    private TextView  mLocation;
    private TextView  mPublishDate;

    public VHReviewNodeOverview() {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, LOCATION, PUBLISH});
    }

    @Override
    public void updateView(GVData data) {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (RatingBar) getView(RATING);
        if (mImage == null) mImage = (ImageView) getView(IMAGE);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mLocation == null) mLocation = (TextView) getView(LOCATION);
        if (mPublishDate == null) mPublishDate = (TextView) getView(PUBLISH);

        GVReviewOverview review = (GVReviewOverview) data;
        if (review == null) return;

        mSubject.setText(review.getSubject());
        mRating.setRating(review.getRating());
        mImage.setImageBitmap(review.getCoverImage());

        String author = review.getAuthor();
        String date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                DateFormat.SHORT).format(review.getPublishDate());
        mPublishDate.setText(date + " by " + author);

        String location = review.getLocationName();
        if (location != null && location.length() > 0) {
            mLocation.setText("@" + location);
        }

        String headline = review.getHeadline();
        if (headline != null && headline.length() > 0) {
            mHeadline.setText("\"" + headline + "\"");
        }
    }
}
