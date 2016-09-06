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

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.ItemBindersDelegate;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbListReference<T, C extends Collection<T>> extends FbRefData<C> implements
        ListReference<T, C>, ItemBindersDelegate.BindableListReference<T, C> {
    private final Map<ListItemBinder<T>, ChildEventListener> mItemBinders;
    private SnapshotConverter<T> mItemConverter;
    private final ItemBindersDelegate<T> mManager;

    public FbListReference(Firebase reference,
                    SnapshotConverter<C> listConverter,
                    SnapshotConverter<T> itemConverter) {
        super(reference, listConverter);
        mItemConverter = itemConverter;
        mItemBinders = new HashMap<>();
        mManager = new ItemBindersDelegate<>(this);
    }

    @Override
    public void unbindFromItems(ListItemBinder<T> binder) {
        mManager.unbindFromItems(binder);
    }

    @Override
    public void bindToItems(final ListItemBinder<T> binder) {
        mManager.bindToItems(binder);
    }

    @Override
    public Iterable<? extends ListItemBinder<T>> getItemBinders() {
        return mItemBinders.keySet();
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        for (Map.Entry<ListItemBinder<T>, ChildEventListener> binding : mItemBinders.entrySet()) {
            getReference().removeEventListener(binding.getValue());
        }
        mManager.notifyBinders();
        mItemBinders.clear();
        mItemConverter = null;
    }

    @Override
    public void removeItemBinder(ListItemBinder<T> binder) {
        getReference().removeEventListener(mItemBinders.remove(binder));
    }

    @Override
    public void bindItemBinder(ListItemBinder<T> binder) {
        ChildEventListener listener = newChildListener(binder);
        mItemBinders.put(binder, listener);
        getReference().addChildEventListener(listener);
    }

    @Override
    public boolean containsItemBinder(ListItemBinder<T> binder) {
        return mItemBinders.containsKey(binder);
    }

    @NonNull
    private ChildEventListener newChildListener(final ListItemBinder<T> binder) {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                T convert = mItemConverter.convert(dataSnapshot);
                if (convert != null) binder.onItemAdded(convert);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                T convert = mItemConverter.convert(dataSnapshot);
                if (convert != null) binder.onItemRemoved(convert);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                invalidate();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

    public static class AuthorList extends FbListReference<AuthorId, List<AuthorId>> implements RefAuthorList{
        public AuthorList(Firebase reference,
                          SnapshotConverter<List<AuthorId>> listConverter,
                          SnapshotConverter<AuthorId> itemConverter) {
            super(reference, listConverter, itemConverter);
        }
    }
}
