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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
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
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.BindersManager;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewReferenceBasic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbReference extends ReviewReferenceBasic {
    private static final CallbackMessage OK = CallbackMessage.ok();
    private static final Random RANDOM = new Random();

    private DataReviewInfo mInfo;
    private ReviewerDbRepository mRepo;
    private ReviewNode mNode;

    private interface LoadListener<T extends ReviewDataRow> {
        void onLoaded(IdableList<T> data);
    }

    public ReviewerDbReference(DataReviewInfo info,
                               ReviewerDbRepository repo,
                               FactoryReviews reviewsFactory,
                               BindersManager bindersManager) {
        super(bindersManager);
        mInfo = info;
        mRepo = repo;
        mNode = reviewsFactory.createLeafNode(this);
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
    public ReviewNode asNode() {
        return mNode;
    }

    @Override
    public void getData(final CoverCallback callback) {
        new DataLoader<>(getDb().getImagesTable(), RowImage.REVIEW_ID,
                new LoadListener<RowImage>() {
                    @Override
                    public void onLoaded(IdableList<RowImage> data) {
                        IdableList<DataImage> covers = new IdableDataList<>(data.getReviewId());
                        for (RowImage image : data) {
                            if (image.isCover()) covers.add(image);
                        }
                        callback.onCover(covers.getItem(RANDOM.nextInt(covers.size())), OK);
                    }
                }).execute();
    }

    @Override
    public void getData(final TagsCallback callback) {
        ItemTagCollection tags = getTags();
        if (tags.size() == 0) {
            mRepo.getReview(getReviewId(), new RepositoryCallback() {
                @Override
                public void onRepositoryCallback(RepositoryResult result) {
                    observeTags(callback, getTags());
                }
            });
            return;
        }

        observeTags(callback, tags);
    }

    @Override
    public void getData(final CriteriaCallback callback) {
        new DataLoader<>(getDb().getCriteriaTable(), RowCriterion.REVIEW_ID,
                new LoadListener<RowCriterion>() {
                    @Override
                    public void onLoaded(IdableList<RowCriterion> data) {
                        callback.onCriteria(data, OK);
                    }
                }).execute();
    }

    @Override
    public void getData(final ImagesCallback callback) {
        new DataLoader<>(getDb().getImagesTable(), RowImage.REVIEW_ID,
                new LoadListener<RowImage>() {
                    @Override
                    public void onLoaded(IdableList<RowImage> data) {
                        callback.onImages(data, OK);
                    }
                }).execute();
    }

    @Override
    public void getData(final CommentsCallback callback) {
        new DataLoader<>(getDb().getCommentsTable(), RowComment.REVIEW_ID,
                new LoadListener<RowComment>() {
                    @Override
                    public void onLoaded(IdableList<RowComment> data) {
                        callback.onComments(data, OK);
                    }
                }).execute();
    }

    @Override
    public void getData(final LocationsCallback callback) {
        new DataLoader<>(getDb().getLocationsTable(), RowLocation.REVIEW_ID,
                new LoadListener<RowLocation>() {
                    @Override
                    public void onLoaded(IdableList<RowLocation> data) {
                        callback.onLocations(data, OK);
                    }
                }).execute();
    }

    @Override
    public void getData(final FactsCallback callback) {
        new DataLoader<>(getDb().getFactsTable(), RowFact.REVIEW_ID,
                new LoadListener<RowFact>() {
                    @Override
                    public void onLoaded(IdableList<RowFact> data) {
                        callback.onFacts(data, OK);
                    }
                }).execute();
    }

    @Override
    public void getSize(TagsSizeCallback callback) {
        observeNumTags(callback);
    }

    //TODO move to SQL
    @Override
    public void getSize(final CriteriaSizeCallback callback) {
        getData(new CriteriaCallback() {
            @Override
            public void onCriteria(IdableList<? extends DataCriterion> criteria, CallbackMessage message) {
                callback.onNumCriteria(size(criteria), message);
            }
        });
    }
    
    @Override
    public void getSize(final ImagesSizeCallback callback) {
        getData(new ImagesCallback() {
            @Override
            public void onImages(IdableList<? extends DataImage> images, CallbackMessage message) {
                callback.onNumImages(size(images), message);
            }
        });
    }

    @Override
    public void getSize(final CommentsSizeCallback callback) {
        getData(new CommentsCallback() {
            @Override
            public void onComments(IdableList<? extends DataComment> comments, CallbackMessage message) {
                callback.onNumComments(size(comments), message);
            }
        });
    }

    @Override
    public void getSize(final LocationsSizeCallback callback) {
        getData(new LocationsCallback() {
            @Override
            public void onLocations(IdableList<? extends DataLocation> locations, CallbackMessage message) {
                callback.onNumLocations(size(locations), message);
            }
        });
    }

    @Override
    public void getSize(final FactsSizeCallback callback) {
        getData(new FactsCallback() {
            @Override
            public void onFacts(IdableList<? extends DataFact> facts, CallbackMessage message) {
                callback.onNumFacts(size(facts), message);
            }
        });
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

    private ItemTagCollection getTags() {
        return mRepo.getTagsManager().getTags(mInfo.getReviewId().toString());
    }

    private ReviewerDbReadable getDb() {
        return mRepo.getReadableDatabase();
    }

    private <T extends HasReviewId> DataSize size(IdableList<T> facts) {
        return new DatumSize(facts.getReviewId(), facts.size());
    }

    private void observeNumTags(final TagsSizeCallback callback) {
        ItemTagCollection tags = getTags();
        if (tags.size() == 0) {
            mRepo.getReview(getReviewId(), new RepositoryCallback() {
                @Override
                public void onRepositoryCallback(RepositoryResult result) {
                    callback.onNumTags(new DatumSize(getReviewId(), getTags().size()), OK);
                }
            });
        }
    }

    private void observeTags(TagsCallback callback, ItemTagCollection tags) {
        IdableList<DataTag> dataTags = new IdableDataList<>(mInfo.getReviewId());
        for (ItemTag tag : tags) {
            dataTags.add(new DatumTag(mInfo.getReviewId(), tag.getTag()));
        }

        callback.onTags(dataTags, OK);
    }

    private class DataLoader<T extends ReviewDataRow> extends AsyncTask<Void, Void, 
            IdableRowList<T>> {
        private final LoadListener<T> mListener;
        private DbTable<T> mTable;
        private ColumnInfo<String> mIdCol;

        public DataLoader(DbTable<T> table, ColumnInfo<String> idCol, LoadListener<T> listener) {
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
