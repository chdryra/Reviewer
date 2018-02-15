/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Factories;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.DataLoader;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.DbItemDereferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.DbCommentListRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.DbDataListRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewerDbReference;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewerDbRepo;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SimpleItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewInfo;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbReference {
    private final FactoryReferences mReferenceFactory;

    public FactoryDbReference(FactoryReferences referenceFactory) {
        mReferenceFactory = referenceFactory;
    }

    public FactoryReferences getReferenceFactory() {
        return mReferenceFactory;
    }

    public ReviewReference newReference(RowReview review, ReviewerDbRepo repo) {
        ReviewId id = review.getReviewId();
        ReviewInfo info = new ReviewInfo(id, review, review, new DatumAuthorId(id, review.getAuthorId()), review);
        return new ReviewerDbReference(info, repo, this);
    }

    public ReviewReference newReference(Review review, ReviewerDbRepo repo) {
        ReviewId id = review.getReviewId();
        ReviewInfo info = new ReviewInfo(id, review.getSubject(), review.getRating(), review.getAuthorId(), review.getPublishDate());
        return new ReviewerDbReference(info, repo, this);
    }

    public CommentRef newReference(DataLoader.RowLoader<RowComment> loader, boolean isHeadline) {
        return mReferenceFactory.newCommentReference(isHeadline, new DbItemDereferencer<>(loader,
                new DbItemDereferencer.Converter<RowComment, DataComment>() {
            @Override
            public DataComment convert(RowComment data) {
                return data;
            }
        }));
    }

    public <T extends ReviewDataRow<T>, R extends HasReviewId> DataListRef<R>
    newListReference(ReviewId id, ReviewerDbReadable db, DbTable<T> table,
                     ColumnInfo<String> idCol, DbDataListRef.Converter<T, R> converter) {
        return new DbDataListRef<>(new DataLoader<>(id, id.toString(),db, table, idCol), this, converter);
    }

    public DbCommentListRef
    newCommentListReference(ReviewId id, ReviewerDbReadable db,
                     ColumnInfo<String> idCol, DbDataListRef.Converter<RowComment, DataComment> converter) {
        return new DbCommentListRef(new DataLoader<>(id, id.toString(),db, db.getCommentsTable(), idCol), this, converter);
    }

    public <T extends ReviewDataRow<T>, R extends HasReviewId> ReviewItemReference<R>
    newItemReference(DataLoader.RowLoader<T> loader, DbItemDereferencer.Converter<T, R> converter) {
        return newItemReference(new DbItemDereferencer<>(loader, converter));
    }

    public ReviewItemReference<DataSize> newSizeReference(SimpleItemReference.Dereferencer<DataSize> dereferencer) {
        return new SimpleItemReference<>(dereferencer);
    }

    public <T extends HasReviewId> ReviewItemReference<T> newItemReference(SimpleItemReference.Dereferencer<T> dereferencer) {
        return new SimpleItemReference<>(dereferencer);
    }
}
