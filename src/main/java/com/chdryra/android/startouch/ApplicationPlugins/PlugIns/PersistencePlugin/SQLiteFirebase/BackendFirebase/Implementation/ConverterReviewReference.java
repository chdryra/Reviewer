/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FbReviewReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterReviewReference implements SnapshotConverter<ReviewReference> {
    private final Firebase mDataBase;
    private final FbReviewsStructure mStructure;
    private final FbReviewReferencer mReferencer;

    public ConverterReviewReference(Firebase dataBase, FbReviewsStructure structure, FbReviewReferencer
            referencer) {
        mDataBase = dataBase;
        mStructure = structure;
        mReferencer = referencer;
    }

    @Override
    @Nullable
    public ReviewReference convert(DataSnapshot snapshot) {
        ReviewListEntry value = snapshot.getValue(ReviewListEntry.class);
        return value == null ? null :
                mReferencer.newReference(value.toInverseDate(), getReviewDb(value), getAggregatesDb(value));
    }

    private Firebase getAggregatesDb(ReviewListEntry entry) {
        return mStructure.getAggregatesDb(mDataBase, getAuthorId(entry), getReviewId(entry));
    }


    private Firebase getReviewDb(ReviewListEntry entry) {
        return mStructure.getReviewDb(mDataBase, getAuthorId(entry), getReviewId(entry));
    }

    @NonNull
    private AuthorId getAuthorId(ReviewListEntry entry) {
        return new AuthorIdParcelable(entry.getAuthorId());
    }

    @NonNull
    private ReviewId getReviewId(ReviewListEntry entry) {
        return new DatumReviewId(entry.getReviewId());
    }
}
