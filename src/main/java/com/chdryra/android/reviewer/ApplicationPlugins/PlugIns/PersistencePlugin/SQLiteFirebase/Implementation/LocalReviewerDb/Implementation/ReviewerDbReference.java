/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.DataReferenceBasic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbReference extends DataReferenceBasic<Review> implements ReviewReference {
    private static final CallbackMessage OK = CallbackMessage.ok();
    private static final Random RANDOM = new Random();

    private DataReviewInfo mInfo;
    private ReviewerDbRepository mRepo;
    private ArrayList<ReferenceBinder<Review>> mBinders;

    private interface LoadListener<T extends ReviewDataRow> {
        void onLoaded(IdableList<T> data);
    }

    public ReviewerDbReference(DataReviewInfo info, ReviewerDbRepository repo) {
        mInfo = info;
        mRepo = repo;
        mBinders = new ArrayList<>();
    }

    @Override
    public ReviewId getReviewId() {
        return mInfo.getReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return mInfo.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mInfo.getRating();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mInfo.getAuthorId();
    }

    @Override
    public DataDate getPublishDate() {
        return mInfo.getPublishDate();
    }

    @Override
    public void bindToValue(final ReferenceBinder<Review> binder) {
        if (!mBinders.contains(binder)) mBinders.add(binder);
        dereference(new DereferenceCallback<Review>() {
            @Override
            public void onDereferenced(@Nullable Review data, CallbackMessage message) {
                if (!message.isError() && data != null) binder.onReferenceValue(data);
            }
        });
    }

    @Override
    public void unbindFromValue(ReferenceBinder<Review> binder) {
        if (mBinders.contains(binder)) mBinders.remove(binder);
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return;
    }

    @Override
    public ReviewListReference<DataCriterion> getCriteria() {
        return newListReference(mRepo.getReadableDatabase().getCriteriaTable(),
                RowCriterion.REVIEW_ID, getConverter());
    }

    @NonNull
    private <R extends ReviewDataRow<R>, T extends HasReviewId> Converter<R, T> getConverter(Class<R> classR, Class<T> classT) {
        return new Converter<R, T>() {
            @Override
            public IdableList<T> convert(IdableRowList<R> data) {
                IdableDataList<T> list = new IdableDataList<>(getReviewId());
                list.addAll(data);
                return list;
            }
        };
    }

    @NonNull
    private <T extends HasReviewId> IdableList<T> newList(Class<T> clazz, IdableRowList<? extends T> data) {
        IdableDataList<T> list = new IdableDataList<>(getReviewId());
        list.addAll(data);
        return list;
    }

    @Override
    public ReviewListReference<DataComment> getComments() {
        return newListReference(mRepo.getReadableDatabase().getCommentsTable(), RowComment
                .REVIEW_ID);
    }

    @Override
    public ReviewListReference<DataFact> getFacts() {
        return newListReference(mRepo.getReadableDatabase().getFactsTable(), RowFact
                .REVIEW_ID);
    }

    @Override
    public ReviewListReference<DataImage> getImages() {
        return newListReference(mRepo.getReadableDatabase().getImagesTable(), RowImage
                .REVIEW_ID);
    }

    @Override
    public ReviewListReference<DataLocation> getLocations() {
        return newListReference(mRepo.getReadableDatabase().getLocationsTable(), RowLocation
                .REVIEW_ID);
    }

    @Override
    public ReviewListReference<DataTag> getTags() {
        return null;
    }

    @Override
    public void dereference(final DereferenceCallback<Review> callback) {
        mRepo.getReview(mInfo.getReviewId(), new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                callback.onDereferenced(result.getReview(), result.getMessage());
            }
        });
    }

    @Override
    public boolean isValidReference() {
        return mInfo != null;
    }

    private ItemTagCollection getItemTags() {
        return mRepo.getTagsManager().getTags(mInfo.getReviewId().toString());
    }

    private ReviewerDbReadable getDb() {
        return mRepo.getReadableDatabase();
    }

    @NonNull
    private <T extends ReviewDataRow<T>, R extends HasReviewId> ReviewListReference<R>
    newListReference(DbTable<T> table, ColumnInfo<String> idCol, Converter<T, R> converter) {
        return new DbListReference<>(new DataLoader<>(getReviewId(),
                mRepo.getReadableDatabase(), table, idCol), converter);
    }

    private void observeTags(TagsCallback callback, ItemTagCollection tags) {
        IdableList<DataTag> dataTags = new IdableDataList<>(mInfo.getReviewId());
        for (ItemTag tag : tags) {
            dataTags.add(new DatumTag(mInfo.getReviewId(), tag.getTag()));
        }

        callback.onTags(dataTags, OK);
    }

    private interface Converter<T extends ReviewDataRow<T>, R extends HasReviewId> {
        IdableList<R>  convert(IdableRowList<T> data);
    }

    public static class DbListReference<T extends ReviewDataRow<T>, R extends HasReviewId>
            extends DataReferenceBasic<IdableList<R>>
            implements ReviewListReference<R> {
        private DataLoader<T> mLoader;
        private ArrayList<ReferenceBinder<IdableList<R>>> mBinders;
        private Converter<T, R> mConverter;

        public DbListReference(DataLoader<T> loader, Converter<T, R> converter) {
            mLoader = loader;
            mConverter = converter;
            mBinders = new ArrayList<>();
        }

        @Override
        public ReviewItemReference<DataSize> getSize() {
            return null;
        }

        @Override
        public ReviewId getReviewId() {
            return mLoader.getReviewId();
        }

        @Override
        public void dereference(final DereferenceCallback<IdableList<R>> callback) {
            mLoader.onLoaded(new LoadListener<T>() {
                @Override
                public void onLoaded(IdableList<T> data) {
                    callback.onDereferenced(mConverter.convert(data), CallbackMessage.ok());
                }
            }).execute();
        }

        @Override
        public void bindToValue(final ReferenceBinder<IdableList<R>> binder) {
            if (!mBinders.contains(binder)) mBinders.add(binder);
            dereference(new DereferenceCallback<IdableList<R>>() {
                @Override
                public void onDereferenced(@Nullable IdableList<R> data, CallbackMessage message) {
                    if (data != null && !message.isError()) binder.onReferenceValue(data);
                }
            });
        }

        @Override
        public void unbindFromValue(ReferenceBinder<IdableList<R>> binder) {
            if (mBinders.contains(binder)) mBinders.remove(binder);
        }
    }

    private static class DataLoader<T extends ReviewDataRow<T>> extends AsyncTask<Void, Void,
            IdableRowList<T>> {
        private ReviewId mId;
        private ReviewerDbReadable mDb;
        private LoadListener<T> mListener;
        private DbTable<T> mTable;
        private ColumnInfo<String> mIdCol;

        public DataLoader(ReviewId id,
                          ReviewerDbReadable db,
                          DbTable<T> table,
                          ColumnInfo<String> idCol) {
            mId = id;
            mDb = db;
            mTable = table;
            mIdCol = idCol;
        }

        public ReviewId getReviewId() {
            return mId;
        }

        public DataLoader<T> onLoaded(LoadListener<T> listener) {
            mListener = listener;
            return this;
        }

        @Override
        protected final IdableRowList<T> doInBackground(Void... params) {
            RowEntry<T, String> clause = new RowEntryImpl<>(mTable.getRowClass(), mIdCol, mId
                    .toString());

            ArrayList<T> data = new ArrayList<>();
            TableTransactor transactor = mDb.beginReadTransaction();
            data.addAll(mDb.getRowsWhere(mTable, clause, transactor));
            mDb.endTransaction(transactor);

            return new IdableRowList<>(mId, data);
        }

        @Override
        protected void onPostExecute(IdableRowList<T> data) {
            if (mListener != null) mListener.onLoaded(data);
        }
    }
}
