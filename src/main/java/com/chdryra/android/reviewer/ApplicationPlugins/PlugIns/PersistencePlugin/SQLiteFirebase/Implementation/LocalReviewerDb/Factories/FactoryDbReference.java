/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.DataLoader;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.DbItemDereferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.DbRefComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.DbRefCommentList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.DbRefDataList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbRepository;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.SimpleItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.StaticListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbReference {

    public ReviewReference newReference(RowReview review, ReviewerDbRepository repo) {
        ReviewId id = review.getReviewId();
        ReviewInfo info = new ReviewInfo(id, review, review, new DatumAuthorId(id, review.getAuthorId()), review);
        return new ReviewerDbReference(info, repo, this);
    }

    public ReviewReference newReference(Review review, ReviewerDbRepository repo) {
        ReviewId id = review.getReviewId();
        ReviewInfo info = new ReviewInfo(id, review.getSubject(), review.getRating(), review.getAuthorId(), review.getPublishDate());
        return new ReviewerDbReference(info, repo, this);
    }

    public RefComment newReference(DataLoader.RowLoader<RowComment> loader, boolean isHeadline) {
        return new DbRefComment(isHeadline, new DbItemDereferencer<>(loader,
                new DbItemDereferencer.Converter<RowComment, DataComment>() {
            @Override
            public DataComment convert(RowComment data) {
                return data;
            }
        }));
    }

    public <T extends ReviewDataRow<T>, R extends HasReviewId> RefDataList<R>
    newListReference(ReviewId id, ReviewerDbReadable db, DbTable<T> table,
                     ColumnInfo<String> idCol, DbRefDataList.Converter<T, R> converter) {
        return new DbRefDataList<>(new DataLoader<>(id, id.toString(),db, table, idCol), this, converter);
    }

    public DbRefCommentList
    newCommentListReference(ReviewId id, ReviewerDbReadable db,
                     ColumnInfo<String> idCol, DbRefDataList.Converter<RowComment, DataComment> converter) {
        return new DbRefCommentList(new DataLoader<>(id, id.toString(),db, db.getCommentsTable(), idCol), this, converter);
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

    public <T extends HasReviewId> RefDataList<T> newStaticReference(IdableList<T> data) {
        return new StaticListReference<>(data);
    }

}
