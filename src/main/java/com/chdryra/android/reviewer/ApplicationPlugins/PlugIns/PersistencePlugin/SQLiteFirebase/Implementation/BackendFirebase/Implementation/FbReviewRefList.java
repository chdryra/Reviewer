/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
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

    public FbReviewRefList(final ReviewId id, Firebase reference, SnapshotConverter<IdableList<T>> converter, ListItemConverter<T> itemConverter) {
        super(reference, converter, new ItemConverter<T>(id, itemConverter));
        mId = id;
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
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
