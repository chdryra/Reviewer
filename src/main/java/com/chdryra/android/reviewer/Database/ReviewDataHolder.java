package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
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
    private final MdCommentList mComments;
    private final MdImageList mImages;
    private final MdFactList mFacts;
    private final MdLocationList mLocations;
    private final IdableList<Review> mCritList;
    private final boolean mIsAverage;

    public ReviewDataHolder(ReviewId id, Author author, PublishDate publishDate,
                            String subject, float rating, MdCommentList comments,
                            MdImageList images, MdFactList facts, MdLocationList locations,
                            IdableList<Review> critList, boolean isAverage) {
        mId = id;
        mAuthor = author;
        mPublishDate = publishDate;
        mSubject = subject;
        mRating = rating;
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

    public MdCommentList getComments() {
        return mComments;
    }

    public MdImageList getImages() {
        return mImages;
    }

    public MdFactList getFacts() {
        return mFacts;
    }

    public MdLocationList getLocations() {
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
