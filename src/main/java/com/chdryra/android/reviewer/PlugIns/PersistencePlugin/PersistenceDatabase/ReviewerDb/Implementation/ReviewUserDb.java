package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb
        .Implementation;

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
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerReadableDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUserDb implements Review {
    private RowReview mRow;
    private ReviewerReadableDb mDatabase;
    private ReviewNode mNode;

    public ReviewUserDb(RowReview row,
                        ReviewerReadableDb database,
                        FactoryReviewNode nodeFactory) {
        mRow = row;
        mDatabase = database;
        mNode = nodeFactory.createReviewNode(this, false);
    }

    @Override
    public ReviewId getReviewId() {
        return mRow.getReviewId();
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
        return new DatumAuthorReview(getReviewId(), row.getName(), row.getUserId());
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
        IdableList<DataCriterionReview> criteriaDb = new IdableDataList<>(getReviewId());
        for (Review criterion : criteria) {
            criteriaDb.add(new DatumCriterionReview(getReviewId(), criterion));
        }

        return criteriaDb;
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return new IdableRowList<>(getReviewId(), loadFromDataTable(mDatabase.getCommentsTable()));
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return new IdableRowList<>(getReviewId(), loadFromDataTable(mDatabase.getFactsTable()));
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return new IdableRowList<>(getReviewId(), loadFromDataTable(mDatabase.getImagesTable()));
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return new IdableRowList<>(getReviewId(), loadFromDataTable(mDatabase.getLocationsTable()));
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        ArrayList<RowImage> covers =
                mDatabase.getRowsWhere(mDatabase.getImagesTable(), RowImage.COLUMN_IS_COVER, "1");

        return new IdableRowList<>(getReviewId(), covers);
    }

    private <T extends DbTableRow> T getRowWhere(DbTable<T> table, String col, String val) {
        TableTransactor db = mDatabase.beginReadTransaction();
        T row = mDatabase.getUniqueRowWhere(db, table, col, val);
        mDatabase.endTransaction(db);

        return row;
    }

    private <T extends ReviewDataRow> ArrayList<T> loadFromDataTable(DbTable<T> table) {
        TableTransactor db = mDatabase.beginReadTransaction();
        ArrayList<T> data = mDatabase.loadFromDataTable(db, table, getReviewId().toString());
        mDatabase.endTransaction(db);

        return data;
    }

    private ArrayList<Review> loadCriteria() {
        TableTransactor db = mDatabase.beginReadTransaction();
        ArrayList<Review> criteria = mDatabase.loadReviewsWhere(db,
                RowReview.COLUMN_PARENT_ID, getReviewId().toString());
        mDatabase.endTransaction(db);
        return criteria;
    }
}
