/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import android.graphics.Bitmap;

import java.util.Date;

/**
 * {@link ReviewViewAdapter} for {@link RCollectionReview} data.
 */
public class ReviewCollectionAdapter extends ReviewViewAdapterBasic {
    private Author mAuthor;
    private Date   mPublishDate;
    private String mTitle;

    private RCollectionReview<Review> mReviews;


    public ReviewCollectionAdapter(Author author, Date date, String title) {
        mAuthor = author;
        mPublishDate = date;
        mTitle = title;
        mReviews = new RCollectionReview<>();
    }

    public void add(Review review) {
        mReviews.add(review);
    }

    @Override
    public String getSubject() {
        return mTitle;
    }

    @Override
    public float getRating() {
        return getAverageRating();
    }

    @Override
    public float getAverageRating() {
        return createReview().getRating().get();
    }

    @Override
    public GvDataList getGridData() {
        GvReviewList data = new GvReviewList();
        for (Review r : mReviews) {
            GvImageList images = MdGvConverter.convert(r.getImages());
            GvCommentList comments = MdGvConverter.convert(r.getComments());
            GvLocationList locations = MdGvConverter.convert(r.getLocations());

            Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
            String headline = comments.size() > 0 ? comments.getItem(0).getCommentHeadline() : null;
            String location = locations.size() > 0 ? locations.getItem(0).getName() : null;

            data.add(r.getId().toString(), r.getAuthor().getName(), r.getPublishDate(),
                    r.getSubject().get(), r.getRating().get(), cover, headline, location);
        }

        return data;
    }

    @Override
    public Author getAuthor() {
        return mAuthor;
    }

    @Override
    public Date getPublishDate() {
        return mPublishDate;
    }

    @Override
    public GvImageList getImages() {
        return null;
    }

    private ReviewNode createReview() {
        return FactoryReview.createReviewCollection(mAuthor, mPublishDate, mTitle, mReviews);
    }
}
