/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorsRepository implements AuthorsRepository {
    private final Firebase mDataRoot;
    private final FbUsersStructure mStructure;
    private final FactoryFbReference mReferenceFactory;
    private final SnapshotConverter<NamedAuthor> mConverter;

    public FbAuthorsRepository(Firebase dataRoot,
                               FbUsersStructure structure,
                               SnapshotConverter<NamedAuthor> converter,
                               FactoryFbReference referenceFactory) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mReferenceFactory = referenceFactory;
        mConverter = converter;
    }

    @Override
    public AuthorReference getReference(AuthorId authorId) {
        Firebase db = mStructure.getAuthorNameMappingDb(mDataRoot, authorId);
        return mReferenceFactory.newNamedAuthor(db, authorId);
    }

    @Override
    public void getAuthorId(String name, AuthorIdCallback callback) {
        Firebase db = mStructure.getNameAuthorMappingDb(mDataRoot, name);
        doSingleEvent(db, getAuthorIdIfNameExists(db, callback));
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }

    @NonNull
    private ValueEventListener getAuthorIdIfNameExists(final Firebase db,
                                                       final AuthorIdCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    callback.onAuthorId(mReferenceFactory.newNullReference(AuthorId.class),
                            CallbackMessage.error(Error.NAME_NOT_FOUND.name()));
                } else {
                    callback.onAuthorId(mReferenceFactory.newAuthorId(db), CallbackMessage.ok());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onAuthorId(mReferenceFactory.newNullReference(AuthorId.class),
                        CallbackMessage.error(Error.NETWORK_ERROR.name()));
            }
        };
    }

    @Override
    public void search(String nameQuery, final SearchAuthorsCallback callback) {
        if(nameQuery.length() == 0) {
            callback.onAuthors(new ArrayList<NamedAuthor>(), CallbackMessage.ok());
            return;
        }

        Firebase db = mStructure.getNameAuthorMapDb(mDataRoot);
        Query query = db.orderByKey().startAt(nameQuery).endAt(nameQuery + "\uf8ff").limitToFirst(SEARCH_LIMIT);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<NamedAuthor> authors = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    authors.add(mConverter.convert(child));
                }
                callback.onAuthors(authors, CallbackMessage.ok());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onAuthors(new ArrayList<NamedAuthor>(), CallbackMessage.error(Error.CANCELLED.name()));
            }
        });
    }
}
