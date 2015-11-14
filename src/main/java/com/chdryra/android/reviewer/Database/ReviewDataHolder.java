package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthor;
import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataDate;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataHolder {
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

    public ReviewDataHolder(String id, DataAuthor author, DataDate publishDate,
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

    public String getId() {
        return mId;
    }

    public DataAuthor getAuthor() {
        return mAuthor;
    }

    public DataDate getPublishDate() {
        return mPublishDate;
    }

    public String getSubject() {
        return mSubject;
    }

    public float getRating() {
        return mRating;
    }

    public float getRatingWeight() {
        return mRatingWeight;
    }

    public Iterable<? extends DataComment> getComments() {
        return mComments;
    }

    public Iterable<? extends DataImage> getImages() {
        return mImages;
    }

    public Iterable<? extends DataFact> getFacts() {
        return mFacts;
    }

    public Iterable<? extends DataLocation> getLocations() {
        return mLocations;
    }

    public ArrayList<Review> getCritList() {
        return mCritList;
    }

    public boolean isAverage() {
        return mIsAverage;
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 07/11/2015
     * Email: rizwan.choudrey@gmail.com
     */
    public interface BuilderReviewUser {
        Review createUserReview(ReviewDataHolder review);
    }
}
