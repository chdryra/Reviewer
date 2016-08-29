/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Factories.FactoryDbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowLocation;
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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.SimpleItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbReference extends SimpleItemReference<Review> implements ReviewReference {
    private ReviewInfo mInfo;
    private ReviewerDbRepository mRepo;
    private FactoryDbReference mReferenceFactory;

    public ReviewerDbReference(ReviewInfo info, ReviewerDbRepository repo, FactoryDbReference referenceFactory) {
        super(new ReviewDereferencer(info.getReviewId(), repo));
        mInfo = info;
        mRepo = repo;
        mReferenceFactory = referenceFactory;
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
    public RefDataList<DataCriterion> getCriteria() {
        return newListReference(getDb().getCriteriaTable(), RowCriterion.REVIEW_ID,
                new DbRefDataList.Converter<RowCriterion, DataCriterion>() {
                    @Override
                    public IdableList<DataCriterion> convert(IdableList<RowCriterion> data) {
                        return newList(DataCriterion.class, data);
                    }
                });
    }

    @Override
    public RefCommentList getComments() {
        return newCommentListReference(RowComment.REVIEW_ID);
    }

    @Override
    public RefDataList<DataFact> getFacts() {
        return newListReference(getDb().getFactsTable(), RowFact.REVIEW_ID,
                new DbRefDataList.Converter<RowFact, DataFact>() {
                    @Override
                    public IdableList<DataFact> convert(IdableList<RowFact> data) {
                        return newList(DataFact.class, data);
                    }
                });
    }

    @Override
    public RefDataList<DataImage> getImages() {
        return newListReference(getDb().getImagesTable(), RowImage.REVIEW_ID,
                new DbRefDataList.Converter<RowImage, DataImage>() {
                    @Override
                    public IdableList<DataImage> convert(IdableList<RowImage> data) {
                        return newList(DataImage.class, data);
                    }
                });
    }

    @Override
    public RefDataList<DataLocation> getLocations() {
        return newListReference(getDb().getLocationsTable(), RowLocation.REVIEW_ID,
                new DbRefDataList.Converter<RowLocation, DataLocation>() {
                    @Override
                    public IdableList<DataLocation> convert(IdableList<RowLocation> data) {
                        return newList(DataLocation.class, data);
                    }
                });
    }

    @Override
    public RefDataList<DataTag> getTags() {
        //TODO need something more optimal and sophisticated.
        ItemTagCollection tags = mRepo.getTagsManager().getTags(mInfo.getReviewId().toString());
        IdableList<DataTag> list = new IdableDataList<>(getReviewId());
        for(ItemTag tag : tags) {
            list.add(new DatumTag(getReviewId(), tag.getTag()));
        }
        return mReferenceFactory.newStaticReference(list);
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
    private <T extends ReviewDataRow<T>, R extends HasReviewId> RefDataList<R>
    newListReference(DbTable<T> table, ColumnInfo<String> idCol, DbRefDataList.Converter<T, R> converter) {
        return mReferenceFactory.newListReference(getReviewId(), getDb(), table, idCol, converter);
    }

    @NonNull
    private DbRefCommentList
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
        private ReviewId mId;
        private ReviewerDbRepository mRepo;

        public ReviewDereferencer(ReviewId id, ReviewerDbRepository repo) {
            mId = id;
            mRepo = repo;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public void dereference(final DereferenceCallback<Review> callback) {
            mRepo.getReview(mId, new RepositoryCallback() {
                @Override
                public void onRepositoryCallback(RepositoryResult result) {
                    callback.onDereferenced(result.getReview(), result.getMessage());
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
                public void onDereferenced(@Nullable IdableList<DataImage> data, CallbackMessage message) {
                    DataImage cover = null;
                    if(data != null && !message.isError()) {
                        for(DataImage image : data) {
                            if(image.isCover()) {
                                cover = image;
                                break;
                            }
                        }
                    }
                    callback.onDereferenced(cover, message);
                }
            });
        }
    }
}
