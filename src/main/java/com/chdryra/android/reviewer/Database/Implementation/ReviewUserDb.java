package com.chdryra.android.reviewer.Database.Implementation;

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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.TableTransactor;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerReadableDb;
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
    private ReviewerReadableDb mDatabase;
    private ReviewNode mNode;

    public ReviewUserDb(RowReview row,
                        ReviewerReadableDb database,
                        FactoryReviewNode nodeFactory) {
        mDatabase = database;
        mReviewId = row.getReviewId();
        mRow = row;
        mNode = nodeFactory.createReviewNode(this, false);
    }

    private <T extends DbTableRow> T getRowWhere(DbTable<T> table, String
            col, String val) {
        TableTransactor db = mDatabase.beginReadTransaction();
        T row = mDatabase.getRowWhere(db, table, col, val);
        mDatabase.endTransaction(db);

        return row;
    }

    private <T extends ReviewDataRow> ArrayList<T> loadFromDataTable(DbTable<T> table) {
        TableTransactor db = mDatabase.beginReadTransaction();
        ArrayList<T> data = mDatabase.loadFromDataTable(db, table, mReviewId.toString());
        mDatabase.endTransaction(db);

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
        TableTransactor db = mDatabase.beginReadTransaction();
        ArrayList<Review> criteria = mDatabase.loadReviewsFromDbWhere(db,
                RowReview.COLUMN_PARENT_ID, mReviewId.toString());
        mDatabase.endTransaction(db);
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
