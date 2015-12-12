package com.chdryra.android.reviewer.Database.Implementation;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUserDb implements Review {
    private ReviewId mReviewId;
    private RowReview mRow;
    private ReviewerDb mDatabase;
    private ReviewNode mNode;

    //Constructors
    public ReviewUserDb(RowReview row,
                        ReviewerDb database,
                        FactoryReviewNode nodeFactory) {
        mDatabase = database;
        mReviewId = row.getReviewId();
        mRow = row;
        mNode = nodeFactory.createReviewNode(this, false);
    }

    private <T extends DbTableRow> T getRowWhere(DbTable<T> table, String
            col, String val) {
        SQLiteDatabase db = startDatabaseTransaction();
        T row = mDatabase.getRowWhere(db, table, col, val);
        endDatabaseTransaction(db);

        return row;
    }

    @NonNull
    private SQLiteDatabase startDatabaseTransaction() {
        SQLiteDatabase db = mDatabase.getReadableDatabase();
        db.beginTransaction();
        return db;
    }

    private void endDatabaseTransaction(SQLiteDatabase db) {
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    private <T extends ReviewDataRow> ArrayList<T> loadFromDataTable(DbTable<T> table) {
        SQLiteDatabase db = startDatabaseTransaction();
        ArrayList<T> data = mDatabase.loadFromDataTable(db, table, mReviewId);
        endDatabaseTransaction(db);

        return data;
    }

    @Override
    public ReviewId getReviewId() {
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
        return new DatumAuthorReview(mReviewId, row.getName(), row.getUserId());
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
    public IdableList<? extends DataCriterionReview> getCriteria() {
        ArrayList<Review> criteria = loadCriteria();
        IdableList<DataCriterionReview> criteriaDb = new IdableDataList<>(mReviewId);
        for(Review criterion : criteria) {
            criteriaDb.add(new DatumCriterionReview(mReviewId, criterion));
        }

        return criteriaDb;
    }

    private ArrayList<Review> loadCriteria() {
        SQLiteDatabase db = startDatabaseTransaction();
        ArrayList<Review> criteria = mDatabase.loadReviewsFromDbWhere(db,
                RowReview.COLUMN_PARENT_ID, mReviewId);
        endDatabaseTransaction(db);
        return criteria;
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
