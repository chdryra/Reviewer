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

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FbReviewReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewDereferencer;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorReviewsReadable extends FbReviewsArchiveBasic {
    private final FbAuthorsReviews mStructure;

    public FbAuthorReviewsReadable(Firebase dataBase,
                                   FbAuthorsReviews structure,
                                   SnapshotConverter<ReviewListEntry> entryConverter,
                                   FbReviewReferencer referencer,
                                   ReviewDereferencer dereferencer) {
        super(dataBase, structure, entryConverter, referencer, dereferencer);
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
}
