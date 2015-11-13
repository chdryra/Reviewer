package com.chdryra.android.reviewer.Database;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthorReview;
import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataCriterion;
import com.chdryra.android.reviewer.Interfaces.Data.DataDateReview;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Interfaces.Data.DataRating;
import com.chdryra.android.reviewer.Interfaces.Data.DataSubject;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUserDb implements Review {
    private String mReviewId;
    private RowReview mRow;
    private ReviewerDb mDatabase;
    private ReviewNode mNode;

    //Constructors
    public ReviewUserDb(RowReview row,
                        ReviewerDb database,
                        FactoryReviewNodeComponent reviewFactory) {
        mDatabase = database;
        init(row, reviewFactory);
    }

    private void init(RowReview row, FactoryReviewNodeComponent reviewFactory) {
        mReviewId = row.getReviewId();
        mRow = row;
        mNode = reviewFactory.createReviewNodeComponent(this, false).makeTree();
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

    private <T extends ReviewDataRow> ArrayList<T> loadFromDataTable(DbTable<T> table) {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();

        db.beginTransaction();
        ArrayList<T> data = mDatabase.loadFromDataTable(db, table, mReviewId);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        return data;
    }

    //Overridden

    @Override
    public String getReviewId() {
        return mReviewId;
    }

    @Override
    public DataSubject getSubject() {
        return mRow;
    }

    @Override
    public DataRating getRating() {
        return mRow;
    }

    @Override
    public DataAuthorReview getAuthor() {
        RowAuthor row = getRowWhere(mDatabase.getAuthorsTable(), RowAuthor.COLUMN_USER_ID,
                mRow.getAuthorId());
        return new AuthorDb(mReviewId, row.getName(), row.getUserId());
    }

    @Override
    public DataDateReview getPublishDate() {
        return mRow;
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return mNode;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return mRow.isRatingIsAverage();
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();
        ArrayList<Review> criteria = mDatabase.loadReviewsFromDbWhere(db, RowReview
                .COLUMN_PARENT_ID, mReviewId);
        CriteriaDb criteriaDb = new CriteriaDb(mReviewId);
        for(Review criterion : criteria) {
            criteriaDb.add(new CriterionDb(mReviewId, criterion));
        }

        return criteriaDb;
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return new IdableRowList<>(mReviewId, loadFromDataTable(mDatabase.getCommentsTable()));
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return new IdableRowList<>(mReviewId, loadFromDataTable(mDatabase.getFactsTable()));
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return new IdableRowList<>(mReviewId, loadFromDataTable(mDatabase.getImagesTable()));
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return new IdableRowList<>(mReviewId, loadFromDataTable(mDatabase.getLocationsTable()));
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        TableRowList<RowImage> covers =
                mDatabase.getRowsWhere(mDatabase.getImagesTable(), RowImage.COLUMN_IS_COVER, "1");
        ArrayList<RowImage> coversArray = new ArrayList<>();
        for(RowImage cover : covers) {
            coversArray.add(cover);
        }

        return new IdableRowList<>(mReviewId, coversArray);
    }
}
