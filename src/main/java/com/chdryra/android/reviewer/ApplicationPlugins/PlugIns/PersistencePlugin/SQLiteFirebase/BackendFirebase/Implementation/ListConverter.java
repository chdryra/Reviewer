/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ListConverter<Value> implements SnapshotConverter<List<Value>> {
    private final SnapshotConverter<Value> mItemConverter;

    public ListConverter(SnapshotConverter<Value> itemConverter) {
        mItemConverter = itemConverter;
    }

    @Override
    @Nullable
    public List<Value> convert(DataSnapshot snapshot) {
        List<Value> data = new ArrayList<>();
        for(DataSnapshot item : snapshot.getChildren()) {
            Value converted = mItemConverter.convert(item);
            if(converted != null) data.add(converted);
        }

        return data;
    }
}
