/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by: Rizwan Choudrey
 * On: 16/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorsRepository implements AuthorsRepository {
    private final Firebase mDataRoot;
    private final FbUsersStructure mStructure;
    private final FactoryFbReference mReferenceFactory;

    public FbAuthorsRepository(Firebase dataRoot, FbUsersStructure structure, FactoryFbReference referenceFactory) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mReferenceFactory = referenceFactory;
    }

    @Override
    public DataReference<NamedAuthor> getName(AuthorId authorId) {
        Firebase db = mStructure.getAuthorNameMappingDb(mDataRoot, authorId);
        return mReferenceFactory.newNamedAuthor(db);
    }

    @Override
    public void getAuthorId(String name, GetAuthorIdCallback callback) {
        Firebase db = mStructure.getNameAuthorMappingDb(mDataRoot, name);
        doSingleEvent(db, getAuthorIdIfNameExists(db, callback));
    }


    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }

    @NonNull
    private ValueEventListener getAuthorIdIfNameExists(final Firebase db,
                                                       final GetAuthorIdCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    callback.onAuthorId(mReferenceFactory.newNullReference(AuthorId.class),
                            Error.NAME_NOT_FOUND);
                } else {
                    callback.onAuthorId(mReferenceFactory.newAuthorId(db), null);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onAuthorId(mReferenceFactory.newNullReference(AuthorId.class),
                        Error.NETWORK_ERROR);
            }
        };
    }
}
