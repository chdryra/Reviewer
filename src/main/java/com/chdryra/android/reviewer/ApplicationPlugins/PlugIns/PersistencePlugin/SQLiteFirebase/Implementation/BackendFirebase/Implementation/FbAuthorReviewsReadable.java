/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorReviewsReadable extends FbReferencesRepositoryBasic {
    private final FbAuthorsReviews mStructure;

    public FbAuthorReviewsReadable(Firebase dataBase,
                                   FbAuthorsReviews structure,
                                   SnapshotConverter<ReviewListEntry> entryConverter,
                                   FactoryFbReviewReference referencer) {
        super(dataBase, structure, entryConverter, referencer);
        mStructure = structure;
    }

    FbAuthorsReviews getStructure() {
        return mStructure;
    }

    @Override
    protected Firebase getAggregatesDb(ReviewListEntry entry) {
        return mStructure.getAggregatesDb(getDataBase(), getReviewId(entry));
    }

    @Override
    protected Firebase getReviewDb(ReviewListEntry entry) {
        return mStructure.getReviewDb(getDataBase(), getReviewId(entry));
    }

    @NonNull
    private DatumReviewId getReviewId(ReviewListEntry entry) {
        return new DatumReviewId(entry.getReviewId());
    }

    public static class MostRecent extends FbAuthorReviewsReadable {
        public MostRecent(Firebase dataBase,
                          FbAuthorsReviews structure,
                          SnapshotConverter<ReviewListEntry> entryConverter,
                          FactoryFbReviewReference referencer) {
            super(dataBase, structure, entryConverter, referencer);
        }

        @Override
        protected Query getQuery(Firebase entriesDb) {
            return entriesDb.orderByChild(ReviewListEntry.DATE).limitToFirst(1);
        }
    }
}
