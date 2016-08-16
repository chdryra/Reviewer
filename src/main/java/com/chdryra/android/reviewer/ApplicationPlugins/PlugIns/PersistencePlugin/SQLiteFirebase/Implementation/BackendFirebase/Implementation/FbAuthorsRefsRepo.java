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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorsRefsRepo extends FbReferencesRepositoryBasic {
    private FbAuthorsReviews mStructure;

    public FbAuthorsRefsRepo(Firebase dataBase,
                             FbAuthorsReviews structure,
                             SnapshotConverter<ReviewListEntry> entryConverter,
                             FactoryFbReviewReference referencer) {
        super(dataBase, entryConverter, structure, referencer);
        mStructure = structure;
    }

    protected FbAuthorsReviews getStructure() {
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
}
