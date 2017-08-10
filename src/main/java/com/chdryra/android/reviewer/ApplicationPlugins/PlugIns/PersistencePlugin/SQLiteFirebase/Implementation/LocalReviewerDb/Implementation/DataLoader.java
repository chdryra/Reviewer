/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.TableTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataLoader<T extends ReviewDataRow<T>> extends AsyncTask<Void, Void,
        IdableRowList<T>> {
    private final ReviewId mReviewId;
    private final String mClauseId;
    private final ReviewerDbReadable mDb;
    private LoadedListener<T> mListener;
    private final DbTable<T> mTable;
    private final ColumnInfo<String> mIdCol;

    public interface LoadedListener<T extends ReviewDataRow> {
        void onLoaded(IdableList<T> data);
    }

    public DataLoader(ReviewId reviewId,
                      String clauseId,
                      ReviewerDbReadable db,
                      DbTable<T> table,
                      ColumnInfo<String> idCol) {
        mReviewId = reviewId;
        mClauseId = clauseId;
        mDb = db;
        mTable = table;
        mIdCol = idCol;
    }

    public ReviewId getReviewId() {
        return mReviewId;
    }

    ReviewerDbReadable getDb() {
        return mDb;
    }

    DbTable<T> getTable() {
        return mTable;
    }

    public ColumnInfo<String> getIdCol() {
        return mIdCol;
    }

    public DataLoader<T> onLoaded(LoadedListener<T> listener) {
        mListener = listener;
        return this;
    }

    @Override
    protected final IdableRowList<T> doInBackground(Void... params) {
        RowEntry<T, String> clause = new RowEntryImpl<>(mTable.getRowClass(), mIdCol, mClauseId);

        ArrayList<T> data = loadData(clause);

        return new IdableRowList<>(mReviewId, data);
    }

    @NonNull
    ArrayList<T> loadData(RowEntry<T, String> clause) {
        ArrayList<T> data = new ArrayList<>();
        TableTransactor transactor = mDb.beginReadTransaction();
        data.addAll(mDb.getRowsWhere(mTable, clause, transactor));
        mDb.endTransaction(transactor);
        return data;
    }

    @Override
    protected void onPostExecute(IdableRowList<T> data) {
        if (mListener != null) mListener.onLoaded(data);
    }

    public RowLoader<T> newRowLoader(String rowId) {
        return new RowLoader<>(getReviewId(), rowId, mDb, mTable, mIdCol);
    }

    public static class RowLoader<T extends ReviewDataRow<T>> extends DataLoader<T> {
        private RowLoadedListener<T> mListener;

        private RowLoader(ReviewId reviewId, String clauseId, ReviewerDbReadable db, DbTable<T>
                table, ColumnInfo<String> idCol) {
            super(reviewId, clauseId, db, table, idCol);
        }

        public interface RowLoadedListener<T extends ReviewDataRow> {
            void onLoaded(@Nullable T data);
        }

        @NonNull
        @Override
        protected ArrayList<T> loadData(RowEntry<T, String> clause) {
            ArrayList<T> data = new ArrayList<>();
            TableTransactor transactor = getDb().beginReadTransaction();
            data.add(getDb().getUniqueRowWhere(getTable(), clause, transactor));
            getDb().endTransaction(transactor);
            return data;
        }

        public RowLoader<T> onLoaded(RowLoadedListener<T> listener) {
            mListener = listener;
            return this;
        }

        @Override
        protected void onPostExecute(IdableRowList<T> data) {
            super.onPostExecute(data);
            if(mListener != null) {
                if(data.size() == 1) {
                    mListener.onLoaded(data.get(0));
                } else {
                    mListener.onLoaded(null);
                }
            }
        }
    }
}
