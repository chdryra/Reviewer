/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ImageData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterImage implements SnapshotConverter<DataImage>, ListItemConverter<DataImage> {
    private ReviewId mId;

    public ConverterImage() {
    }

    public ConverterImage(ReviewId id) {
        mId = id;
    }

    @Override
    @Nullable
    public DataImage convert(DataSnapshot snapshot) {
        return mId != null ? convert(mId, snapshot) : new DatumImage();
    }

    @Override
    public DataImage convert(ReviewId id, DataSnapshot snapshot) {
        ImageData value = snapshot.getValue(ImageData.class);
        return value == null ? new DatumImage(id) : new DatumImage(id, ImageData.asBitmap(value.getBitmap()),
                new DatumDate(id, value.getDate()), value.getCaption(), value.isCover());
    }
}
