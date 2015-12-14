package com.chdryra.android.reviewer.Database.Implementation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Database.Factories.FactoryDbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbHelper;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.SQL;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerReadableDb;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerReadableDbImpl implements ReviewerReadableDb {
    private final ReviewerDbContract mTables;
    private final DbHelper<ReviewerDbContract> mHelper;
    private final TagsManager mTagsManager;
    private final ReviewLoader mReviewLoader;
    private final FactoryDbTableRow mRowFactory;
    private final DataValidator mDataValidator;

    public ReviewerReadableDbImpl(ReviewerDbContract tables,
                                  DbHelper<ReviewerDbContract> helper,
                                  TagsManager tagsManager,
                                  ReviewLoader reviewLoader,
                                  FactoryDbTableRow rowFactory,
                                  DataValidator dataValidator) {
        mTables = tables;
        mHelper = helper;
        mTagsManager = tagsManager;
        mReviewLoader = reviewLoader;
        mRowFactory = rowFactory;
        mDataValidator = dataValidator;
    }

    @Override
    public String getColumnNameReviewId() {
        return mTables.getColumnNameReviewId();
    }

    @Override
    public DbTable<RowReview> getReviewsTable() {
        return mTables.getReviewsTable();
    }

    @Override
    public DbTable<RowComment> getCommentsTable() {
        return mTables.getCommentsTable();
    }

    @Override
    public DbTable<RowFact> getFactsTable() {
        return mTables.getFactsTable();
    }

    @Override
    public DbTable<RowLocation> getLocationsTable() {
        return mTables.getLocationsTable();
    }

    @Override
    public DbTable<RowImage> getImagesTable() {
        return mTables.getImagesTable();
    }

    @Override
    public DbTable<RowAuthor> getAuthorsTable() {
        return mTables.getAuthorsTable();
    }

    @Override
    public DbTable<RowTag> getTagsTable() {
        return mTables.getTagsTable();
    }

    @Override
    public ArrayList<DbTable<? extends DbTableRow>> getTables() {
        return mTables.getTables();
    }

    @Override
    public ArrayList<String> getTableNames() {
        return mTables.getTableNames();
    }

    @Override
    public SQLiteDatabase beginReadTransaction() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        db.beginTransaction();
        return db;
    }

    @Override
    public void endTransaction(SQLiteDatabase db) {
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    @Override
    public ArrayList<Review> loadReviewsFromDbWhere(SQLiteDatabase db, String col, @Nullable String val) {
        TableRowList<RowReview> reviewsList = getRowsWhere(db, getReviewsTable(), col, val);

        ArrayList<Review> reviews = new ArrayList<>();
        for (RowReview reviewRow : reviewsList) {
            reviews.add(loadReview(reviewRow, db));
        }

        return reviews;
    }

    @Override
    public <T extends DbTableRow> T getRowWhere(SQLiteDatabase db, DbTable<T> table,
                                                String col, String val) {
        Cursor cursor = getCursorWhere(db, table.getName(), col, val);

        if (cursor == null || cursor.getCount() == 0) {
            return mRowFactory.emptyRow(table.getRowClass());
        }

        cursor.moveToFirst();
        T row = mRowFactory.newRow(cursor, table.getRowClass());
        cursor.close();

        return row;
    }

    @Override
    public <T extends DbTableRow> TableRowList<T> getRowsWhere(DbTable<T> table, String col,
                                                               String val) {
        SQLiteDatabase db = beginReadTransaction();

        TableRowList<T> rowList = getRowsWhere(db, table, col, val);
        endTransaction(db);
        return rowList;
    }

    @Override
    public <T1 extends DbTableRow> ArrayList<T1>
    loadFromDataTable(SQLiteDatabase db, DbTable<T1> table, String reviewId) {
        TableRowList<T1> rows = getRowsWhere(db, table, getColumnNameReviewId(), reviewId);
        ArrayList<T1> results = new ArrayList<>();
        for (T1 row : rows) {
            results.add(row);
        }

        return results;
    }


    private void loadTags(SQLiteDatabase db) {
        for (RowTag tag : getRowsWhere(db, getTagsTable(), null, null)) {
            ArrayList<String> reviews = tag.getReviewIds();
            for (String reviewId : reviews) {
                mTagsManager.tagItem(reviewId, tag.getTag());
            }
        }
    }

    private void setTags(String id, SQLiteDatabase db) {
        ItemTagCollection tags = mTagsManager.getTags(id);
        if (tags.size() == 0) loadTags(db);
    }

    @Nullable
    public Review loadReview(RowReview reviewRow, SQLiteDatabase db) {
        if (!reviewRow.hasData(mDataValidator)) return null;
        Review review = mReviewLoader.loadReview(reviewRow, this, db);
        setTags(reviewRow.getReviewId().toString(), db);

        return review;
    }

    private <T extends DbTableRow> TableRowList<T> getRowsWhere(SQLiteDatabase db,
                                                                DbTable<T> table,
                                                                @Nullable String col,
                                                                @Nullable String val) {
        Cursor cursor = getFromTableWhere(db, table.getName(), col, val);

        TableRowList<T> list = new TableRowList<>();
        while (cursor.moveToNext()) {
            list.add(mRowFactory.newRow(cursor, table.getRowClass()));
        }
        cursor.close();

        return list;
    }

    @Nullable
    private Cursor getCursorWhere(SQLiteDatabase db, String table, String pkColumn, String
            pkValue) {
        Cursor cursor = getFromTableWhere(db, table, pkColumn, pkValue);
        if (cursor != null && cursor.getCount() > 1) {
            cursor.close();
            throw new IllegalStateException("Cannot have more than 1 row with same primary key!");
        }

        return cursor;
    }

    private Cursor getFromTableWhere(SQLiteDatabase db, String table,
                                     @Nullable String column, @Nullable String value) {
        boolean isNull = value == null;
        String val = isNull ? SQL.SPACE + SQL.IS_NULL : SQL.SPACE + SQL.BIND_STRING;
        String whereClause = column != null ? " " + SQL.WHERE + column + val : "";
        String query = SQL.SELECT + SQL.ALL + SQL.FROM + table + whereClause;
        String[] args = isNull ? null : new String[]{value};
        return db.rawQuery(query, args);
    }
}
