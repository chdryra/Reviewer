/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase
        .Implementation;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FactoryFbReviewsRepo;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbAuthorsDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewsRepo extends FbReviewsRepoBasic implements ReviewsRepo {
    private final FactoryFbReviewsRepo mAuthorsDbFactory;
    private final FbReviewsStructure mStructure;

    public FbReviewsRepo(Firebase dataBase,
                         FbReviewsStructure structure,
                         SnapshotConverter<ReviewReference> converter,
                         ReviewDereferencer dereferencer,
                         SizeReferencer sizeReferencer,
                         FactoryFbReviewsRepo authorsDbFactory) {
        super(dataBase, structure, converter, dereferencer, sizeReferencer);
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

    private FbAuthorsDb getAuthorsDb(AuthorId authorId) {
        return mStructure.getAuthorsDb(authorId);
    }
}
