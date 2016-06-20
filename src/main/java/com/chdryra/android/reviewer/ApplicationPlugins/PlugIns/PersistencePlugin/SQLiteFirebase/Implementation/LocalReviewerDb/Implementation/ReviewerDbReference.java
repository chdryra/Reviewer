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

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewInfo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ValueBinder;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbReference implements ReviewReference {
    private ReviewInfo mInfo;
    private ReviewerDbRepository mRepo;
    private ArrayList<ValueBinder<?>> mBinders;

    public ReviewerDbReference(ReviewInfo info, ReviewerDbRepository repo) {
        mInfo = info;
        mRepo = repo;
        mBinders = new ArrayList<>();
    }

    @Override
    public ReviewInfo getInfo() {
        return mInfo;
    }

    @Override
    public void bind(ReferenceBinders.SubjectBinder binder) {
        registerIfNecessary(binder);
        binder.onValue(mInfo.getSubject());
    }

    @Override
    public void bind(ReferenceBinders.RatingBinder binder) {
        registerIfNecessary(binder);
        binder.onValue(mInfo.getRating());
    }

    @Override
    public void bind(ReferenceBinders.AuthorBinder binder) {
        registerIfNecessary(binder);
        binder.onValue(mInfo.getAuthor());
    }

    @Override
    public void bind(ReferenceBinders.DateBinder binder) {
        registerIfNecessary(binder);
        binder.onValue(mInfo.getPublishDate());
    }

    @Override
    public void bind(final ReferenceBinders.CoverBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getImagesTable(), RowImage.REVIEW_ID,
                new DataListener<RowImage>() {
                    @Override
                    public void onLoaded(IdableList<RowImage> data) {
                        for (RowImage image : data) {
                            if (image.isCover()) {
                                binder.onValue(image);
                                break;
                            }
                        }
                    }
                }).execute();
    }

    @Override
    public void bind(final ReferenceBinders.CriteriaBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getCriteriaTable(), RowCriterion.REVIEW_ID, 
                new DataListener<RowCriterion>() {
                    @Override
                    public void onLoaded(IdableList<RowCriterion> data) {
                        binder.onValue(data);
                    }
                }).execute();
    }

    @Override
    public void bind(final ReferenceBinders.CommentsBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getCommentsTable(), RowComment.REVIEW_ID,
                new DataListener<RowComment>() {
                    @Override
                    public void onLoaded(IdableList<RowComment> data) {
                        binder.onValue(data);    
                    }
                }).execute();
    }

    @Override
    public void bind(final ReferenceBinders.FactsBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getFactsTable(), RowFact.REVIEW_ID,
                new DataListener<RowFact>() {
                    @Override
                    public void onLoaded(IdableList<RowFact> data) {
                        binder.onValue(data);
                    }
                }).execute();
    }

    @Override
    public void bind(final ReferenceBinders.ImagesBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getImagesTable(), RowImage.REVIEW_ID,
                new DataListener<RowImage>() {
                    @Override
                    public void onLoaded(IdableList<RowImage> data) {
                        binder.onValue(data);
                    }
                }).execute();
    }

    @Override
    public void bind(final ReferenceBinders.LocationsBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getLocationsTable(), RowLocation.REVIEW_ID,
                new DataListener<RowLocation>() {
                    @Override
                    public void onLoaded(IdableList<RowLocation> data) {
                        binder.onValue(data);
                    }
                }).execute();
    }

    @Override
    public void bind(ReferenceBinders.TagsBinder binder) {
        registerIfNecessary(binder);
        observeTags(binder);
    }

    @Override
    public void bindToCriteria(final ReferenceBinders.SizeBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getCriteriaTable(), RowCriterion.REVIEW_ID,
                new DataListener<RowCriterion>() {
                    @Override
                    public void onLoaded(IdableList<RowCriterion> data) {
                        binder.onValue(data.size());
                    }
                }).execute();
    }

    @Override
    public void bindToComments(final ReferenceBinders.SizeBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getCommentsTable(), RowComment.REVIEW_ID,
                new DataListener<RowComment>() {
                    @Override
                    public void onLoaded(IdableList<RowComment> data) {
                        binder.onValue(data.size());
                    }
                }).execute();
    }

    @Override
    public void bindToFacts(final ReferenceBinders.SizeBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getFactsTable(), RowFact.REVIEW_ID,
                new DataListener<RowFact>() {
                    @Override
                    public void onLoaded(IdableList<RowFact> data) {
                        binder.onValue(data.size());
                    }
                }).execute();
    }

    @Override
    public void bindToImages(final ReferenceBinders.SizeBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getFactsTable(), RowFact.REVIEW_ID,
                new DataListener<RowFact>() {
                    @Override
                    public void onLoaded(IdableList<RowFact> data) {
                        binder.onValue(data.size());
                    }
                }).execute();
    }

    @Override
    public void bindToLocations(final ReferenceBinders.SizeBinder binder) {
        registerIfNecessary(binder);
        new DataLoader<>(getDb().getLocationsTable(), RowLocation.REVIEW_ID,
                new DataListener<RowLocation>() {
                    @Override
                    public void onLoaded(IdableList<RowLocation> data) {
                        binder.onValue(data.size());
                    }
                }).execute();
    }

    @Override
    public void bindToTags(ReferenceBinders.SizeBinder binder) {
        registerIfNecessary(binder);
        observeNumTags(binder);
    }

    @Override
    public void unbind(ReferenceBinders.SubjectBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.RatingBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.AuthorBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.DateBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CoverBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CriteriaBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CommentsBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.FactsBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.ImagesBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.LocationsBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbind(ReferenceBinders.TagsBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbindFromCriteria(ReferenceBinders.SizeBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbindFromComments(ReferenceBinders.SizeBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbindFromFacts(ReferenceBinders.SizeBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbindFromImages(ReferenceBinders.SizeBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbindFromLocations(ReferenceBinders.SizeBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void unbindFromTags(ReferenceBinders.SizeBinder binder) {
        unregisterIfNecessary(binder);
    }

    @Override
    public void dereference(final DereferenceCallback callback) {
        mRepo.getReview(mInfo.getReviewId(), new ReviewsRepository.RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                callback.onDereferenced(result.getReview(), result.getMessage());
            }
        });
    }

    @Override
    public boolean isValid() {
        return mInfo.isValid();
    }

    private ItemTagCollection getTags() {
        return mRepo.getTagsManager().getTags(mInfo.getReviewId().toString());
    }

    private ReviewerDbReadable getDb() {
        return mRepo.getReadableDatabase();
    }

    private void observeTags(final ReferenceBinders.TagsBinder binder) {
        ItemTagCollection tags = getTags();
        if (tags.size() == 0) {
            mRepo.getReview(getInfo().getReviewId(), new ReviewsRepository.RepositoryCallback() {
                @Override
                public void onRepositoryCallback(RepositoryResult result) {
                    observeTags(binder, getTags());
                }
            });
            return;
        }

        observeTags(binder, tags);
    }

    private void observeNumTags(final ReferenceBinders.SizeBinder binder) {
        ItemTagCollection tags = getTags();
        if (tags.size() == 0) {
            mRepo.getReview(getInfo().getReviewId(), new ReviewsRepository.RepositoryCallback() {
                @Override
                public void onRepositoryCallback(RepositoryResult result) {
                    binder.onValue(getTags().size());
                }
            });
        }
    }

    private void observeTags(ReferenceBinders.TagsBinder binder, ItemTagCollection tags) {
        IdableList<DataTag> dataTags = new IdableDataList<>(mInfo.getReviewId());
        for (ItemTag tag : tags) {
            dataTags.add(new DatumTag(mInfo.getReviewId(), tag.getTag()));
        }

        binder.onValue(dataTags);
    }

    private <T> void registerIfNecessary(ValueBinder<T> binder) {
        if (!mBinders.contains(binder)) mBinders.add(binder);
    }

    private <T> void unregisterIfNecessary(ValueBinder<T> binder) {
        if (mBinders.contains(binder)) mBinders.remove(binder);
    }

    private interface DataListener<T extends ReviewDataRow> {
        void onLoaded(IdableList<T> data);
    }
    private class DataLoader<T extends ReviewDataRow> extends AsyncTask<Void, Void, IdableRowList<T>> {
        private DbTable<T> mTable;
        private ColumnInfo<String> mIdCol;
        private final DataListener<T> mListener;

        public DataLoader(DbTable<T> table, ColumnInfo<String> idCol, DataListener<T>
                listener) {
            mTable = table;
            mIdCol = idCol;
            mListener = listener;
        }

        @Override
        protected IdableRowList<T> doInBackground(Void... params) {
            RowEntry<T, String> clause = new RowEntryImpl<>(mTable.getRowClass(), mIdCol,
                    mInfo.getReviewId().toString());

            ArrayList<T> data = new ArrayList<>();
            ReviewerDbReadable db = getDb();
            TableTransactor transactor = db.beginReadTransaction();
            data.addAll(db.getRowsWhere(mTable, clause, transactor));
            db.endTransaction(transactor);

            return new IdableRowList<>(mInfo.getReviewId(), data);
        }

        @Override
        protected void onPostExecute(IdableRowList<T> data) {
            mListener.onLoaded(data);
        }
    }
}
