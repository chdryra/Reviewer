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

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseAuthorsRepository extends FirebaseRepositoryBasic {
    private FbAuthorsReviews mStructure;

    public FirebaseAuthorsRepository(Firebase dataBase,
                                     ConverterEntry entryConverter,
                                     FbAuthorsReviews structure,
                                     FactoryFbReference referencer) {
        super(dataBase, entryConverter, structure, referencer);
        mStructure = structure;
    }

    protected FbAuthorsReviews getStructure() {
        return mStructure;
    }

    @Override
    protected Firebase getAggregatesDb(ReviewListEntry entry) {
        return mStructure.getAggregatesDb(getDataBase(), toReviewId(entry));
    }

    @Override
    protected Firebase getReviewDb(ReviewListEntry entry) {
        return mStructure.getReviewDb(getDataBase(), toReviewId(entry));
    }

    @NonNull
    private DatumReviewId toReviewId(ReviewListEntry entry) {
        return new DatumReviewId(entry.getReviewId());
    }
}
