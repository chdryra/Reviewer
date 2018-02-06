/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ImageData;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.ReviewItemConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterImage implements SnapshotConverter<DataImage>, ReviewItemConverter<DataImage> {
    private ReviewId mId;

    public ConverterImage() {
    }

    public ConverterImage(ReviewId id) {
        mId = id;
    }

    @Override
    @Nullable
    public DataImage convert(DataSnapshot snapshot) {
        return mId != null ? convert(mId, snapshot) : null;
    }

    @Override
    public DataImage convert(ReviewId id, DataSnapshot snapshot) {
        ImageData value = snapshot.getValue(ImageData.class);
        if(value == null) return null;
        Bitmap bitmap = ImageData.asBitmap(value.getBitmap());
        return new DatumImage(id, bitmap,
                new DatumDate(id, value.getDate()), value.getCaption(), value.toLatLng(), value.isCover());
    }
}
