package com.chdryra.android.reviewer.Model.ReviewStructure;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Database.MdDataRow;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Database.DbTable;
import com.chdryra.android.reviewer.Database.RowAuthor;
import com.chdryra.android.reviewer.Database.RowReview;
import com.chdryra.android.reviewer.Database.DbTableRow;
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
    private UserId mUserId;
    private PublishDate mPublishDate;
    private MdSubject mSubject;
    private MdRating mRating;
    private boolean mRatingIsAverage;
    private ReviewNode mNode;

    //Constructors
    public ReviewUserDb(RowReview row, ReviewerDb database, FactoryReview reviewFactory) {
        mDatabase = database;
        init(row, reviewFactory);
    }

    public ReviewUserDb(String reviewId, ReviewerDb database, FactoryReview reviewFactory) {
        mDatabase = database;
        RowReview row = getRowWhere(mDatabase.getReviewsTable(), RowReview.REVIEW_ID, reviewId);
        init(row, reviewFactory);
    }

    private void init(RowReview row, FactoryReview reviewFactory) {
        ContentValues values = row.getContentValues();

        String subject = values.getAsString(RowReview.SUBJECT);
        mReviewId = ReviewId.fromString(values.getAsString(RowReview.REVIEW_ID));
        mSubject = new MdSubject(subject, mReviewId);
        mPublishDate = PublishDate.then(values.getAsLong(RowReview.PUBLISH_DATE));
        String userId = values.getAsString(RowReview.AUTHOR_ID);
        mUserId = UserId.fromString(userId);
        float rating = values.getAsFloat(RowReview.RATING);
        mRating = new MdRating(rating, 1, mReviewId);
        mRatingIsAverage = values.getAsBoolean(RowReview.IS_AVERAGE);
        mNode = reviewFactory.createReviewTreeNode(this, false).createTree();
    }

    private <T extends DbTableRow> T getRowWhere(DbTable<T> table, String
            col, String val) {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();

        db.beginTransaction();
        T row = mDatabase.getRowWhere(db, table, col, val);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        return row;
    }

    private <T1 extends MdData, T2 extends MdDataList<T1>, T3 extends MdDataRow<T1>> T2
    loadFromDataTable(DbTable<T3> table, Class<T2> listClass) {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();

        db.beginTransaction();
        T2 data = mDatabase.loadFromDataTable(db, table, mReviewId.toString(), listClass);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        return data;
    }

    //Overridden
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
        RowAuthor row = getRowWhere(mDatabase.getAuthorsTable(), RowAuthor.USER_ID, mUserId.toString());
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
        return loadFromDataTable(mDatabase.getCommentsTable(), MdCommentList.class);
    }

    @Override
    public MdFactList getFacts() {
        return loadFromDataTable(mDatabase.getFactsTable(), MdFactList.class);
    }

    @Override
    public MdImageList getImages() {
        return loadFromDataTable(mDatabase.getImagesTable(), MdImageList.class);
    }

    @Override
    public MdLocationList getLocations() {
        return loadFromDataTable(mDatabase.getLocationsTable(), MdLocationList.class);
    }

    @Override
    public ReviewId getId() {
        return mReviewId;
    }
}
