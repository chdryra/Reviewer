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

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryAuthorsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewsRepository extends FbReferencesRepositoryBasic implements
        ReviewsRepository {
    private final FactoryAuthorsRepo mAuthorsDbFactory;
    private final FbReviewsStructure mStructure;

    public FbReviewsRepository(Firebase dataBase,
                               FbReviewsStructure structure,
                               ConverterEntry entryConverter,
                               FactoryFbReviewReference referencer,
                               FactoryAuthorsRepo authorsDbFactory) {
        super(dataBase, entryConverter, structure, referencer);
        mStructure = structure;
        mAuthorsDbFactory = authorsDbFactory;
    }

    @Override
    public ReferencesRepository getRepositoryForAuthor(AuthorId authorId) {
        return mAuthorsDbFactory.newAuthorReviews(getDataBase(), mStructure.getAuthorsDb(authorId));
    }

    @Override
    public MutableRepository getMutableRepository(UserSession session) {
        return mAuthorsDbFactory.newAuthorsDb(getDataBase(), mStructure.getAuthorsDb(session
                .getAuthorId()));
    }

    @Override
    protected Firebase getAggregatesDb(ReviewListEntry entry) {
        return mStructure.getAggregatesDb(getDataBase(), toAuthorId(entry), toReviewId(entry));
    }

    @Override
    protected Firebase getReviewDb(ReviewListEntry entry) {
        return mStructure.getReviewDb(getDataBase(), toAuthorId(entry), toReviewId(entry));
    }

    @NonNull
    private AuthorId toAuthorId(ReviewListEntry entry) {
        return new AuthorIdParcelable(entry.getAuthorId());
    }

    @NonNull
    private ReviewId toReviewId(ReviewListEntry entry) {
        return new DatumReviewId(entry.getReviewId());
    }
}
