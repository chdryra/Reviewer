/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbAuthorsRepository;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorsRepo {
    private FactoryFbReference mReferenceFactory;

    public FactoryAuthorsRepo(FactoryFbReference referenceFactory) {
        mReferenceFactory = referenceFactory;
    }

    public AuthorsRepository newRepository(Firebase dataRoot, FbUsersStructure structure) {
        return new FbAuthorsRepository(dataRoot, structure, mReferenceFactory);
    }
}
