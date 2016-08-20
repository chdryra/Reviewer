/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterList<T extends HasReviewId> implements SnapshotConverter<IdableList<T>> {
    protected ReviewId mId;
    protected ListItemConverter<T> mItemConverter;

    public ConverterList(ReviewId id, ListItemConverter<T> itemConverter) {
        mId = id;
        mItemConverter = itemConverter;
    }

    @Override
    @Nullable
    public IdableList<T> convert(DataSnapshot snapshot) {
        IdableList<T> data = new IdableDataList<>(mId);
        for(DataSnapshot item : snapshot.getChildren()) {
            T converted = mItemConverter.convert(mId, item);
            if(converted != null) data.add(converted);
        }

        return data;
    }
}
