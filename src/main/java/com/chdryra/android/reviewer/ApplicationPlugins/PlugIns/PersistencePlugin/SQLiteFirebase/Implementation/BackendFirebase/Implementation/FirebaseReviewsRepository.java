/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase
        .Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseReviewsRepository extends FirebaseRepositoryBasic implements ReviewsRepository {
    private FactoryAuthorsDb mAuthorsDbFactory;
    private FbReviews mStructure;

    public FirebaseReviewsRepository(Firebase dataBase,
                                     FbReviews structure,
                                     FbReferencer referencer,
                                     FactoryAuthorsDb authorsDbFactory) {
        super(dataBase, structure, referencer);
        mStructure = structure;
        mAuthorsDbFactory = authorsDbFactory;
    }

    @Override
    public AuthorsRepository getRepository(DataAuthor dataAuthor) {
        Author author = new Author(dataAuthor);
        return mAuthorsDbFactory.newAuthorReviews(mDataBase, mStructure.getAuthorsDb(author));
    }

    @Override
    public MutableRepository getMutableRepository(UserSession session) {
        Author author = new Author(session.getSessionAuthor());
        return mAuthorsDbFactory.newAuthorsDb(mDataBase, mStructure.getAuthorsDb(author));
    }

    @Override
    protected Firebase getAggregatesDb(ReviewListEntry entry) {
        return mStructure.getAggregatesDb(mDataBase, entry.getAuthor(), entry.getReviewId());
    }

    @Override
    protected Firebase getReviewDb(ReviewListEntry entry) {
        return mStructure.getReviewDb(mDataBase, entry.getAuthor(), entry.getReviewId());
    }
}
