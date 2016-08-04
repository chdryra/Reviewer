/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;
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
public class FbRefListData<T, C extends Collection<T>> extends FbRefData<C> implements ListReference<T, C> {
    private Map<ListItemBinder<T>, ChildEventListener> mChildBindings;
    private SnapshotConverter<T> mItemConverter;

    public FbRefListData(Firebase reference, SnapshotConverter<C> listConverter, SnapshotConverter<T> itemConverter) {
        super(reference, listConverter);
        mItemConverter = itemConverter;
        mChildBindings = new HashMap<>();
    }

    @Override
    public void unbindFromItems(ListItemBinder<T> binder) {
        if (isValidReference() && mChildBindings.containsKey(binder)) {
            getReference().removeEventListener(mChildBindings.get(binder));
        }
    }

    @Override
    public void bindToItems(final ListItemBinder<T> binder) {
        if (isValidReference() && !mChildBindings.containsKey(binder)) {
            ChildEventListener listener = newChildListener(binder);
            mChildBindings.put(binder, listener);
            getReference().addChildEventListener(listener);
        }
    }

    @Override
    protected void onInvalidate() {
        for (Map.Entry<ListItemBinder<T>, ChildEventListener> binding : mChildBindings.entrySet()) {
            binding.getKey().onInvalidated(this);
            getReference().removeEventListener(binding.getValue());
        }
        mChildBindings.clear();
        mItemConverter = null;
    }

    @NonNull
    private ChildEventListener newChildListener(final ListItemBinder<T> binder) {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                T convert = mItemConverter.convert(dataSnapshot);
                if (convert != null) binder.onItemValue(convert);
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

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }
}
