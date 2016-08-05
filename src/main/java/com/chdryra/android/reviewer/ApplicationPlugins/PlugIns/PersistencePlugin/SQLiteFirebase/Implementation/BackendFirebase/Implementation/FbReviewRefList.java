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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewRefList<T extends HasReviewId> extends FbRefListData<T, IdableList<T>> implements ReviewListReference<T> {
    private ReviewId mId;
    private ReviewItemReference<DataSize> mSizeReference;

    public FbReviewRefList(ReviewId id,
                           Firebase reference,
                           ReviewItemReference<DataSize> sizeReference,
                           SnapshotConverter<IdableList<T>> converter,
                           ListItemConverter<T> itemConverter) {
        super(reference, converter, new ItemConverter<>(id, itemConverter));
        mId = id;
        mSizeReference = sizeReference;
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public ReviewItemReference<DataSize> getSizeReference() {
        return mSizeReference;
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<T> callback) {
        mSizeReference.dereference(new DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(@Nullable DataSize data, CallbackMessage message) {
                if(data != null && !message.isError()) {
                    ReviewId id = getReviewId();
                    IdableList<ReviewItemReference<T>> refs = new IdableDataList<>(id);
                    for(int i = 0; i < data.getSize(); ++i) {
                        Firebase child = getReference().child(String.valueOf(i));
                        refs.add(new FbReviewRefItem<>(id, child, getItemConverter()));
                    }

                    callback.onItemReferences(refs);
                }
            }
        });
    }

    private static class ItemConverter<T extends HasReviewId> implements SnapshotConverter<T> {
        private ReviewId mId;
        private ListItemConverter<T> mConverter;

        public ItemConverter(ReviewId id, ListItemConverter<T> converter) {
            mId = id;
            mConverter = converter;
        }

        @Nullable
        @Override
        public T convert(DataSnapshot snapshot) {
            return mConverter.convert(mId, snapshot);
        }
    }
}
