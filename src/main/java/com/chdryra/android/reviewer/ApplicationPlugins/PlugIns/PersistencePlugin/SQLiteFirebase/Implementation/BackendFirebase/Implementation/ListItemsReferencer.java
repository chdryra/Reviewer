/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ListItemsReferencer<T extends HasReviewId, Reference extends ReviewItemReference<T>> {
    private ItemReferenceFactory<T, Reference> mFactory;

    public interface Callback<T extends HasReviewId, Reference extends ReviewItemReference<T>> {
        void onItemReferences(IdableList<Reference> references);
    }

    public interface ItemReferenceFactory<T extends HasReviewId, Reference extends ReviewItemReference<T>> {
        Reference newReference(ReviewId id, Firebase child, int index);
    }

    public ListItemsReferencer(ItemReferenceFactory<T, Reference> factory) {
        mFactory = factory;
    }

    public ItemReferenceFactory<T, Reference> getFactory() {
        return mFactory;
    }

    public void toItemReferences(final Firebase root,
                                 final ReviewItemReference<DataSize> sizeReference,
                                 final Callback<T, Reference> callback) {
        final ReviewId id = sizeReference.getReviewId();
        final IdableList<Reference> refs = new IdableDataList<>(id);
        sizeReference.dereference(new DataReference.DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(@Nullable DataSize data, CallbackMessage message) {
                if (data != null && !message.isError()) {
                    for (int i = 0; i < data.getSize(); ++i) {
                        refs.add(toItemReference(root, id, i));
                    }
                }

                callback.onItemReferences(refs);
            }
        });
    }

    public Reference toItemReference(Firebase root, ReviewId id, int index) {
        return mFactory.newReference(id, root.child(String.valueOf(index)), index);
    }

    public ListItemsReferencer<T, ReviewItemReference<T>> asBaseReferencer() {
        return new ListItemsReferencer<>(new ItemReferenceFactory<T, ReviewItemReference<T>>() {
            @Override
            public ReviewItemReference<T> newReference(ReviewId id, Firebase child, int index) {
                return mFactory.newReference(id, child, index);
            }
        });
    }
}
