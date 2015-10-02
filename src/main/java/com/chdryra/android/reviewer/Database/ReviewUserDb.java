package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewData.MdData;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdRating;
import com.chdryra.android.reviewer.Model.ReviewData.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 02/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUserDb implements Review {
    private ReviewId mReviewId;
    private ReviewerDb mDatabase;
    private final UserId mUserId;
    private final PublishDate mPublishDate;
    private final MdSubject   mSubject;
    private final MdRating    mRating;
    private final boolean mRatingIsAverage;
    private ReviewNode mNode;

    public ReviewUserDb(String reviewId, ReviewerDb database) {
        mReviewId = ReviewId.fromString(reviewId);
        mDatabase = database;

        RowReview row = getRowWhere(ReviewerDb.REVIEWS, RowReview.REVIEW_ID, reviewId);
        ContentValues values = row.getContentValues();

        String subject = values.getAsString(RowReview.SUBJECT);
        mSubject = new MdSubject(subject, mReviewId);
        mPublishDate = PublishDate.then(values.getAsLong(RowReview.PUBLISH_DATE));
        String userId = values.getAsString(RowReview.AUTHOR_ID);
        mUserId = UserId.fromString(userId);
        float rating = values.getAsFloat(RowReview.RATING);
        mRating = new MdRating(rating, mReviewId);
        mRatingIsAverage = values.getAsBoolean(RowReview.IS_AVERAGE);
        mNode = FactoryReview.createReviewTreeNode(this, false).createTree();
    }

    @Override
    public ReviewId getId() {
        return mReviewId;
    }

    @Override
    public MdSubject getSubject() {
        return mSubject;
    }

    @Override
    public MdRating getRating() {
        return mRating;
    }

    @Override
    public Author getAuthor() {
        RowAuthor row = getRowWhere(ReviewerDb.AUTHORS, RowAuthor.USER_ID, mUserId.toString());
        return row.toAuthor();
    }

    @Override
    public PublishDate getPublishDate() {
        return mPublishDate;
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return mNode;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return mRatingIsAverage;
    }

    @Override
    public MdCriterionList getCriteria() {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();
        IdableList<Review> criteria = mDatabase.loadReviewsFromDbWhere(db, RowReview.PARENT_ID,
                mReviewId.toString());
        return new MdCriterionList(criteria, mReviewId);
    }

    @Override
    public MdCommentList getComments() {
        return loadFromDataTable(ReviewerDb.COMMENTS, MdCommentList.class);
    }

    @Override
    public MdFactList getFacts() {
        return loadFromDataTable(ReviewerDb.FACTS, MdFactList.class);
    }

    @Override
    public MdImageList getImages() {
        return loadFromDataTable(ReviewerDb.IMAGES, MdImageList.class);
    }

    @Override
    public MdLocationList getLocations() {
        return loadFromDataTable(ReviewerDb.LOCATIONS, MdLocationList.class);
    }

    private <T extends ReviewerDbRow.TableRow> T getRowWhere(ReviewerDbTable<T> table, String col, String val) {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();

        db.beginTransaction();
        T row = mDatabase.getRowWhere(db, table, col, val);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        return row;
    }

    private <T1 extends MdData, T2 extends MdDataList<T1>, T3 extends MdDataRow<T1>> T2
    loadFromDataTable(ReviewerDbTable<T3> table, Class<T2> listClass) {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();

        db.beginTransaction();
        T2 data = mDatabase.loadFromDataTable(db, table, mReviewId.toString(), listClass);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        return data;
    }
}
