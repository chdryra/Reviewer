/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase
        .Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Authentication.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReviewsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FbReviewReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbAuthorsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoWriteable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepo;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewsRepo extends FbReviewsRepoBasic implements
        ReviewsRepo {
    private final FactoryFbReviewsRepo mAuthorsDbFactory;
    private final FbReviewsStructure mStructure;

    public FbReviewsRepo(Firebase dataBase,
                         FbReviewsStructure structure,
                         ConverterEntry entryConverter,
                         FbReviewReferencer referencer,
                         ReviewDereferencer dereferencer,
                         FactoryFbReviewsRepo authorsDbFactory) {
        super(dataBase, structure, entryConverter, referencer, dereferencer);
        mStructure = structure;
        mAuthorsDbFactory = authorsDbFactory;
    }

    @Override
    public ReviewsRepoReadable getReviewsByAuthor(AuthorId authorId) {
        return mAuthorsDbFactory.newRepoReadable(getDataBase(), getAuthorsDb(authorId));
    }

    @Override
    public ReviewsRepoWriteable getRepoForUser(UserSession session) {
        return mAuthorsDbFactory.newRepoWriteable(getDataBase(), getAuthorsDb(session
                .getAuthorId()));
    }

    @Override
    public ReviewCollection getCollectionForAuthor(AuthorId authorId, String name) {
        return mAuthorsDbFactory.getReviewCollection(getDataBase(), authorId, name);
    }

    @Override
    protected Firebase getAggregatesDb(ReviewListEntry entry) {
        return mStructure.getAggregatesDb(getDataBase(), getAuthorId(entry), getReviewId(entry));
    }

    @Override
    protected Firebase getReviewDb(ReviewListEntry entry) {
        return mStructure.getReviewDb(getDataBase(), getAuthorId(entry), getReviewId(entry));
    }

    private FbAuthorsDb getAuthorsDb(AuthorId authorId) {
        return mStructure.getAuthorsDb(authorId);
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
