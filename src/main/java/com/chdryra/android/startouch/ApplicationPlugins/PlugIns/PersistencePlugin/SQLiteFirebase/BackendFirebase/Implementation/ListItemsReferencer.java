/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ListItemsReferencer<Value extends HasReviewId, Reference extends
        ReviewItemReference<Value>> {
    private final ItemReferenceFactory<Value, Reference> mFactory;

    public interface Callback<T extends HasReviewId, Reference extends ReviewItemReference<T>> {
        void onItemReferences(IdableList<Reference> references);
    }

    public interface ItemReferenceFactory<T extends HasReviewId, Reference extends
            ReviewItemReference<T>> {
        Reference newReference(ReviewId id, Firebase child, int index);
    }

    public ListItemsReferencer(ItemReferenceFactory<Value, Reference> factory) {
        mFactory = factory;
    }

    public ItemReferenceFactory<Value, Reference> getFactory() {
        return mFactory;
    }

    public void toItemReferences(final Firebase root,
                                 final ReviewItemReference<DataSize> sizeReference,
                                 final Callback<Value, Reference> callback) {
        final ReviewId id = sizeReference.getReviewId();
        final IdableList<Reference> refs = new IdableDataList<>(id);
        sizeReference.dereference(new DataReference.DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(DataValue<DataSize> value) {
                if (value.hasValue()) {
                    for (int i = 0; i < value.getData().getSize(); ++i) {
                        refs.add(toItemReference(id, root, i));
                    }
                }

                callback.onItemReferences(refs);
            }
        });
    }

    public Reference toItemReference(ReviewId id, Firebase root, int index) {
        return mFactory.newReference(id, root.child(String.valueOf(index)), index);
    }
}
