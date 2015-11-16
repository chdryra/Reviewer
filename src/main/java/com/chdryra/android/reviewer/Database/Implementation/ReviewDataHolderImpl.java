package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataHolderImpl implements ReviewDataHolder{
    private final String mId;
    private final DataAuthor mAuthor;
    private final DataDate mPublishDate;
    private final String mSubject;
    private final float mRating;
    private final int mRatingWeight;
    private final Iterable<? extends DataComment> mComments;
    private final Iterable<? extends DataImage> mImages;
    private final Iterable<? extends DataFact> mFacts;
    private final Iterable<? extends DataLocation> mLocations;
    private final ArrayList<Review> mCritList;
    private final boolean mIsAverage;

    public ReviewDataHolderImpl(String id, DataAuthor author, DataDate publishDate,
                                String subject, float rating, int ratingWeight,
                                Iterable<? extends DataComment> comments,
                                Iterable<? extends DataImage> images,
                                Iterable<? extends DataFact> facts,
                                Iterable<? extends DataLocation> locations,
                                ArrayList<Review> critList, boolean isAverage) {
        mId = id;
        mAuthor = author;
        mPublishDate = publishDate;
        mSubject = subject;
        mRating = rating;
        mRatingWeight = ratingWeight;
        mComments = comments;
        mImages = images;
        mFacts = facts;
        mLocations = locations;
        mCritList = critList;
        mIsAverage = isAverage;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public DataAuthor getAuthor() {
        return mAuthor;
    }

    @Override
    public DataDate getPublishDate() {
        return mPublishDate;
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public float getRatingWeight() {
        return mRatingWeight;
    }

    @Override
    public Iterable<? extends DataComment> getComments() {
        return mComments;
    }

    @Override
    public Iterable<? extends DataImage> getImages() {
        return mImages;
    }

    @Override
    public Iterable<? extends DataFact> getFacts() {
        return mFacts;
    }

    @Override
    public Iterable<? extends DataLocation> getLocations() {
        return mLocations;
    }

    @Override
    public Iterable<Review> getCriteria() {
        return mCritList;
    }

    @Override
    public boolean isAverage() {
        return mIsAverage;
    }
}
