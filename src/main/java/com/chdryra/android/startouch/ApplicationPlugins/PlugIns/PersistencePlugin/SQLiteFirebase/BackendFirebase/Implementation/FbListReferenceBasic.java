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

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.Size;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SubscribersManager;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.CollectionReference;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FbListReferenceBasic<T, C extends Collection<T>, S extends Size> extends
        FbRefData<C> implements
        CollectionReference<T, C, S>, SubscribersManager.SubscribableCollectionReference<T, C, S> {
    private final Map<ItemSubscriber<T>, ChildEventListener> mItemBinders;
    private final SubscribersManager<T, S> mManager;
    private SnapshotConverter<T> mItemConverter;

    public FbListReferenceBasic(Firebase reference,
                                SnapshotConverter<C> listConverter,
                                SnapshotConverter<T> itemConverter) {
        super(reference, listConverter);
        mItemConverter = itemConverter;
        mItemBinders = new HashMap<>();
        mManager = new SubscribersManager<>(this);
    }

    protected void doBinding(ChildEventListener listener) {
        getFbReference().addChildEventListener(listener);
    }

    protected void doUnbinding(ChildEventListener listener) {
        getFbReference().removeEventListener(listener);
    }

    @Override
    public void unsubscribe(ItemSubscriber<T> binder) {
        mManager.unsubscribe(binder);
    }

    @Override
    public void subscribe(final ItemSubscriber<T> binder) {
        mManager.subscribe(binder);
    }

    @Override
    public Collection<ItemSubscriber<T>> getItemSubscribers() {
        return mItemBinders.keySet();
    }

    SnapshotConverter<T> getItemConverter() {
        return mItemConverter;
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        for (Map.Entry<ItemSubscriber<T>, ChildEventListener> binding : mItemBinders.entrySet()) {
            doUnbinding(binding.getValue());
        }
        mManager.notifyOnInvalidated();
        mItemBinders.clear();
        mItemConverter = null;
    }

    @Override
    public void unbindSubscriber(ItemSubscriber<T> subscriber) {
        ChildEventListener listener = mItemBinders.remove(subscriber);
        doUnbinding(listener);
    }

    @Override
    public void bindSubscriber(ItemSubscriber<T> subscriber) {
        ChildEventListener listener = newChildListener(subscriber);
        mItemBinders.put(subscriber, listener);
        doBinding(listener);
    }

    @Override
    public boolean containsSubscriber(ItemSubscriber<T> subscriber) {
        return mItemBinders.containsKey(subscriber);
    }

    @NonNull
    private ChildEventListener newChildListener(final ItemSubscriber<T> binder) {
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

}
