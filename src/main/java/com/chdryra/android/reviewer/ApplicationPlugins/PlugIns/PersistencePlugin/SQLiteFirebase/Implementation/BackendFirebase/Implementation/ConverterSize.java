/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterSize implements SnapshotConverter<DataSize> {
    private ReviewId mId;

    public ConverterSize(ReviewId id) {
        mId = id;
    }

    @Override
    @Nullable
    public DataSize convert(DataSnapshot snapshot) {
        Integer value = snapshot.getValue(Integer.class);
        return value == null ? new DatumSize(mId, 0) : new DatumSize(mId, value);
    }
}
