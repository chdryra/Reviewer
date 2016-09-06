/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.ReviewItemConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableListConverter<Value extends HasReviewId> implements SnapshotConverter<IdableList<Value>> {
    private final ReviewId mId;
    private final ReviewItemConverter<Value> mReviewItemConverter;
    private final ItemConverter<Value> mItemConverter;

    public IdableListConverter(ReviewId id, ReviewItemConverter<Value> reviewItemConverter) {
        mId = id;
        mReviewItemConverter = reviewItemConverter;
        mItemConverter = new ItemConverter<>(id, reviewItemConverter);
    }

    protected ReviewId getId() {
        return mId;
    }

    public SnapshotConverter<Value> getItemConverter() {
        return mItemConverter;
    }

    @Override
    @Nullable
    public IdableList<Value> convert(DataSnapshot snapshot) {
        IdableList<Value> data = new IdableDataList<>(mId);
        for(DataSnapshot item : snapshot.getChildren()) {
            Value converted = mReviewItemConverter.convert(mId, item);
            if(converted != null) data.add(converted);
        }

        return data;
    }

    public static class ItemConverter<T extends HasReviewId> implements SnapshotConverter<T> {
        private final ReviewId mId;
        private final ReviewItemConverter<T> mConverter;

        public ItemConverter(ReviewId id, ReviewItemConverter<T> converter) {
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
