/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseAuthorsRepository extends FirebaseRepositoryBasic implements AuthorsRepository {
    private FbAuthorsReviews mStructure;

    public FirebaseAuthorsRepository(Firebase dataBase,
                                     FbAuthorsReviews structure,
                                     FbReferencer referencer) {
        super(dataBase, structure, referencer);
        mStructure = structure;
    }

    protected FbAuthorsReviews getStructure() {
        return mStructure;
    }

    @Override
    public DataAuthor getAuthor() {
        return mStructure.getAuthor().toDataAuthor();
    }

    @Override
    protected Firebase getAggregatesDb(ReviewListEntry entry) {
        return mStructure.getAggregatesDb(mDataBase, entry.getReviewId());
    }

    @Override
    protected Firebase getReviewDb(ReviewListEntry entry) {
        return mStructure.getReviewDb(mDataBase, entry.getReviewId());
    }
}
