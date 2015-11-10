package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataLocation;
import com.chdryra.android.reviewer.Model.ReviewData.MdIdableList;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.MdReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.UserData.Author;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataHolder {
    private final MdReviewId mId;
    private final Author mAuthor;
    private final PublishDate mPublishDate;
    private final String mSubject;
    private final float mRating;
    private final int mRatingWeight;
    private final Iterable<? extends DataComment> mComments;
    private final Iterable<? extends DataImage> mImages;
    private final Iterable<? extends DataFact> mFacts;
    private final Iterable<? extends DataLocation> mLocations;
    private final MdIdableList<Review> mCritList;
    private final boolean mIsAverage;

    public ReviewDataHolder(MdReviewId id, Author author, PublishDate publishDate,
                            String subject, float rating, int ratingWeight,
                            Iterable<? extends DataComment> comments,
                            Iterable<? extends DataImage> images,
                            Iterable<? extends DataFact> facts,
                            Iterable<? extends DataLocation> locations,
                            MdIdableList<Review> critList, boolean isAverage) {
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

    public MdReviewId getId() {
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

    public MdIdableList<Review> getCritList() {
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
