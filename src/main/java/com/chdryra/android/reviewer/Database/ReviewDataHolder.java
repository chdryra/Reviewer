package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataLocation;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.UserData.Author;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataHolder {
    private final ReviewId mId;
    private final Author mAuthor;
    private final PublishDate mPublishDate;
    private final String mSubject;
    private final float mRating;
    private final int mRatingWeight;
    private final Iterable<DataComment> mComments;
    private final Iterable<DataImage> mImages;
    private final Iterable<DataFact> mFacts;
    private final Iterable<DataLocation> mLocations;
    private final IdableList<Review> mCritList;
    private final boolean mIsAverage;

    public ReviewDataHolder(ReviewId id, Author author, PublishDate publishDate,
                            String subject, float rating, int ratingWeight,
                            Iterable<DataComment> comments,
                            Iterable<DataImage> images,
                            Iterable<DataFact> facts,
                            Iterable<DataLocation> locations,
                            IdableList<Review> critList, boolean isAverage) {
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

    public ReviewId getId() {
        return mId;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public PublishDate getPublishDate() {
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

    public Iterable<DataComment> getComments() {
        return mComments;
    }

    public Iterable<DataImage> getImages() {
        return mImages;
    }

    public Iterable<DataFact> getFacts() {
        return mFacts;
    }

    public Iterable<DataLocation> getLocations() {
        return mLocations;
    }

    public IdableList<Review> getCritList() {
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
        Review createReviewUser(ReviewDataHolder review);
    }
}
