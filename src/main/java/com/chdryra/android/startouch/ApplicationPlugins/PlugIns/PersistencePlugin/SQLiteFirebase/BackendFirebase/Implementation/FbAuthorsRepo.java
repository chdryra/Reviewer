/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories.FactoryFbProfile;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories.FbDataReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorRef;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
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
public class FbAuthorsRepo implements AuthorsRepo {
    private final Firebase mDataRoot;
    private final FbUsersStructure mStructure;
    private final FbDataReferencer mReferenceFactory;
    private final SnapshotConverter<AuthorName> mConverter;
    private final FactoryFbProfile mProfileFactory;

    public FbAuthorsRepo(Firebase dataRoot,
                         FbUsersStructure structure,
                         SnapshotConverter<AuthorName> converter,
                         FbDataReferencer referenceFactory,
                         FactoryFbProfile profileFactory) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mReferenceFactory = referenceFactory;
        mConverter = converter;
        mProfileFactory = profileFactory;
    }

    @Override
    public AuthorRef getAuthor(AuthorId authorId) {
        return getAuthorProfile(authorId).getAuthor();
    }

    @Override
    public void getAuthorId(String name, AuthorIdCallback callback) {
        Firebase db = mStructure.getNameAuthorMappingDb(mDataRoot, name);
        doSingleEvent(db, getAuthorIdIfNameExists(db, callback));
    }

    @Override
    public AuthorProfileRef getAuthorProfile(AuthorId authorId) {
        return mProfileFactory.newAuthorProfile(authorId);
    }

    @Override
    public SocialProfileRef getSocialProfile(AuthorId authorId) {
        return mProfileFactory.newSocialProfile(authorId);
    }

    @Override
    public void search(String nameQuery, final SearchAuthorsCallback callback) {
        if (nameQuery.length() == 0) {
            callback.onAuthors(new ArrayList<AuthorName>(), CallbackMessage.ok());
            return;
        }

        Firebase db = mStructure.getNameAuthorMapDb(mDataRoot);
        Query query = db.orderByKey().startAt(nameQuery).endAt(nameQuery + "\uf8ff").limitToFirst
                (SEARCH_LIMIT);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<AuthorName> authors = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    authors.add(mConverter.convert(child));
                }
                callback.onAuthors(authors, CallbackMessage.ok());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onAuthors(new ArrayList<AuthorName>(), CallbackMessage.error(Error
                        .CANCELLED.name()));
            }
        });
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
}
