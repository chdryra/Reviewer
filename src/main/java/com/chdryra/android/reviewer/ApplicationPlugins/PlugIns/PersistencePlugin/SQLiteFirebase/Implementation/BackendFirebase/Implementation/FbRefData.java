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

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.BindableReferenceBasic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbRefData<T> extends BindableReferenceBasic<T> {
    private Firebase mReference;
    private Map<ReferenceBinder<T>, ValueEventListener> mBindings;
    private SnapshotConverter<T> mConverter;

    public FbRefData(Firebase reference, SnapshotConverter<T> converter) {
        mReference = reference;
        mConverter = converter;
        mBindings = new HashMap<>();
        mListeners = new ArrayList<>();
    }

    protected Firebase getReference() {
        return mReference;
    }

    protected SnapshotConverter<T> getConverter() {
        return mConverter;
    }

    protected void onDereferenced(T value) {

    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        for (Map.Entry<ReferenceBinder<T>, ValueEventListener> binding : mBindings.entrySet()) {
            mReference.removeEventListener(binding.getValue());
        }

        mBindings.clear();
        mConverter = null;
        mReference = null;
    }

    @Override
    protected void removeBinder(ReferenceBinder<T> binder) {
        mReference.removeEventListener(mBindings.get(binder));
    }

    @Override
    protected boolean contains(ReferenceBinder<T> binder) {
        return mBindings.containsKey(binder);
    }

    @Override
    protected void doDereferencing(DereferenceCallback<T> callback) {
        mReference.addListenerForSingleValueEvent(getDereferencer(callback));
    }

    @Override
    protected Collection<ReferenceBinder<T>> getBinders() {
        return mBindings.keySet();
    }

    @Override
    protected void bind(ReferenceBinder<T> binder) {
        ValueEventListener listener = newListener(binder);
        mBindings.put(binder, listener);
        mReference.addValueEventListener(mBindings.get(binder));
    }

    @NonNull
    private ValueEventListener getDereferencer(final DereferenceCallback<T> callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                T value = mConverter.convert(dataSnapshot);
                if (value != null) {
                    onDereferenced(value);
                    callback.onDereferenced(value, CallbackMessage.ok());
                } else {
                    invalidReference(callback);
                    invalidate();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onDereferenced(null, CallbackMessage.error("Call to Firebase cancelled"));
            }
        };
    }

    @NonNull
    private ValueEventListener newListener(final ReferenceBinder<T> binder) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                T convert = mConverter.convert(dataSnapshot);
                if (convert != null) {
                    binder.onReferenceValue(convert);
                } else {
                    invalidate();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }
}
