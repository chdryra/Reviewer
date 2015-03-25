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
    private final Author mAuthor;
    private final Date   mPublishDate;
    private final String mTitle;

    private final RCollectionReview<Review> mReviews;


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
        for (Review review : mReviews) {
            GvImageList images = MdGvConverter.convert(review.getImages());
            GvCommentList headlines = MdGvConverter.convert(review.getComments()).getHeadlines();
            GvLocationList locations = MdGvConverter.convert(review.getLocations());

            Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
            String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() :
                    null;
            String location = locations.size() > 0 ? locations.getItem(0).getName() : null;

            data.add(review.getId().toString(), review.getAuthor().getName(),
                    review.getPublishDate(),
                    review.getSubject().get(), review.getRating().get(), cover, headline, location);
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
