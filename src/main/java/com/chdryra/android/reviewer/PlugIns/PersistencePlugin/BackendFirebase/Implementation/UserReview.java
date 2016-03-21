/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserReview {
    private String mReviewId;
    private String mSubject;
    private Rating mRating;
    private Author mAuthor;
    private long mDate;
    private List<Criterion> mCriteria;
    private List<Comment> mComments;
    private List<Fact> mFacts;
    private List<ImageData> mImages;
    private List<Location> mLocations;

    private boolean mIsAverageOfCriteria;

    public UserReview() {
    }

    public UserReview(Review review) {
        mReviewId = review.getReviewId().toString();
        mSubject = review.getSubject().getSubject();
        mRating = new Rating(review.getRating());
        mAuthor = new Author(review.getAuthor());
        mDate = review.getPublishDate().getTime();
        mCriteria = new ArrayList<>();
        for(DataCriterion criterion : review.getCriteria()) {
            mCriteria.add(new Criterion(criterion));
        }
        mComments = new ArrayList<>();
        for(DataComment comment : review.getComments()) {
            mComments.add(new Comment(comment));
        }
        mFacts = new ArrayList<>();
        for(DataFact fact : review.getFacts()) {
            mFacts.add(new Fact(fact));
        }
        mImages = new ArrayList<>();
        for(DataImage image : review.getImages()) {
            mImages.add(new ImageData(image));
        }
        mLocations = new ArrayList<>();
        for(DataLocation location : review.getLocations()) {
            mLocations.add(new Location(location));
        }
        
        mIsAverageOfCriteria = review.isRatingAverageOfCriteria();
    }

    public String getSubject() {
        return mSubject;
    }

    public Rating getRating() {
        return mRating;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public long getPublishDate() {
        return mDate;
    }

    public boolean isRatingAverageOfCriteria() {
        return mIsAverageOfCriteria;
    }

    public List<Criterion> getCriteria() {
        return mCriteria;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public List<Fact> getFacts() {
        return mFacts;
    }

    public List<ImageData> getImages() {
        return mImages;
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    public String getReviewId() {
        return mReviewId;
    }
}
