/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Factories.FactoryDbReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SimpleItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbReference extends SimpleItemReference<Review> implements ReviewReference {
    private final DataReview mInfo;
    private final ReviewerDbRepo mRepo;
    private final FactoryDbReference mReferenceFactory;

    public ReviewerDbReference(DataReview info, ReviewerDbRepo repo, FactoryDbReference
            referenceFactory) {
        super(new ReviewDereferencer(info.getReviewId(), repo));
        mInfo = info;
        mRepo = repo;
        mReferenceFactory = referenceFactory;
    }

    @Override
    public void registerObserver(ReviewReferenceObserver observer) {
        //TODO implement
    }

    @Override
    public void unregisterObserver(ReviewReferenceObserver observer) {
        //TODO implement
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
    public ReviewItemReference<DataImage> getCover() {
        return mReferenceFactory.newItemReference(new CoverDereferencer());
    }

    @Override
    public DataListRef<DataCriterion> getCriteria() {
        return newListReference(getDb().getCriteriaTable(), RowCriterion.REVIEW_ID,
                new DbDataListRef.Converter<RowCriterion, DataCriterion>() {
                    @Override
                    public IdableList<DataCriterion> convert(IdableList<RowCriterion> data) {
                        return newList(DataCriterion.class, data);
                    }
                });
    }

    @Override
    public CommentListRef getComments() {
        return newCommentListReference(RowComment.REVIEW_ID);
    }

    @Override
    public DataListRef<DataFact> getFacts() {
        return newListReference(getDb().getFactsTable(), RowFact.REVIEW_ID,
                new DbDataListRef.Converter<RowFact, DataFact>() {
                    @Override
                    public IdableList<DataFact> convert(IdableList<RowFact> data) {
                        return newList(DataFact.class, data);
                    }
                });
    }

    @Override
    public DataListRef<DataImage> getImages() {
        return newListReference(getDb().getImagesTable(), RowImage.REVIEW_ID,
                new DbDataListRef.Converter<RowImage, DataImage>() {
                    @Override
                    public IdableList<DataImage> convert(IdableList<RowImage> data) {
                        return newList(DataImage.class, data);
                    }
                });
    }

    @Override
    public DataListRef<DataLocation> getLocations() {
        return newListReference(getDb().getLocationsTable(), RowLocation.REVIEW_ID,
                new DbDataListRef.Converter<RowLocation, DataLocation>() {
                    @Override
                    public IdableList<DataLocation> convert(IdableList<RowLocation> data) {
                        return newList(DataLocation.class, data);
                    }
                });
    }

    @Override
    public DataListRef<DataTag> getTags() {
        return newListReference(getDb().getTagsTable(), RowTag.REVIEW_ID,
                new DbDataListRef.Converter<RowTag, DataTag>() {
                    @Override
                    public IdableList<DataTag> convert(IdableList<RowTag> data) {
                        return newList(DataTag.class, data);
                    }
                });
    }

    private ReviewerDbReadable getDb() {
        return mRepo.getReadableDatabase();
    }

    @NonNull
    private <T extends HasReviewId> IdableList<T> newList(Class<T> clazz,
                                                          IdableList<? extends T> data) {
        IdableDataList<T> list = new IdableDataList<>(getReviewId());
        list.addAll(data);
        return list;
    }

    @NonNull
    private <T extends ReviewDataRow<T>, R extends HasReviewId> DataListRef<R>
    newListReference(DbTable<T> table, ColumnInfo<String> idCol, DbDataListRef.Converter<T, R>
            converter) {
        return mReferenceFactory.newListReference(getReviewId(), getDb(), table, idCol, converter);
    }

    @NonNull
    private DbCommentListRef
    newCommentListReference(ColumnInfo<String> idCol) {
        return mReferenceFactory.newCommentListReference(getReviewId(), getDb(), idCol,
                new DbListReferenceBasic.Converter<RowComment, DataComment>() {
                    @Override
                    public IdableList<DataComment> convert(IdableList<RowComment> data) {
                        return newList(DataComment.class, data);
                    }
                });
    }

    private static class ReviewDereferencer implements Dereferencer<Review> {
        private final ReviewId mId;
        private final ReviewerDbRepo mRepo;

        private ReviewDereferencer(ReviewId id, ReviewerDbRepo repo) {
            mId = id;
            mRepo = repo;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public void dereference(final DereferenceCallback<Review> callback) {
            mRepo.getReview(mId, new RepoCallback() {
                @Override
                public void onRepoCallback(RepoResult result) {
                    DataValue<Review> value = result.isReview() ?
                            new DataValue<>(result.getReview()) :
                            new DataValue<Review>(result.getMessage());
                    callback.onDereferenced(value);
                }
            });
        }
    }

    private class CoverDereferencer implements Dereferencer<DataImage> {
        @Override
        public ReviewId getReviewId() {
            return mInfo.getReviewId();
        }

        @Override
        public void dereference(final DereferenceCallback<DataImage> callback) {
            getImages().dereference(new DereferenceCallback<IdableList<DataImage>>() {
                @Override
                public void onDereferenced(DataValue<IdableList<DataImage>> value) {
                    DataValue<DataImage> cover
                            = new DataValue<DataImage>(new DatumImage(getReviewId()));
                    if (value.hasValue()) {
                        for (DataImage image : value.getData()) {
                            if (image.isCover()) {
                                cover = new DataValue<>(image);
                                break;
                            }
                        }
                    }

                    callback.onDereferenced(cover);
                }
            });
        }
    }
}
