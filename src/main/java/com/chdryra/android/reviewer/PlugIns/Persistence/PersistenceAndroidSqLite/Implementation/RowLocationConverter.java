package com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation;

import android.content.ContentValues;

import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Interfaces.RowConverter;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocationConverter implements RowConverter<RowLocation> {
    @Override
    public ContentValues convert(RowLocation row) {
        ContentValues values = new ContentValues();
        values.put(RowLocation.COLUMN_LOCATION_ID, row.getRowId());
        values.put(RowLocation.COLUMN_REVIEW_ID, row.getReviewId().toString());
        LatLng latLng = row.getLatLng();
        values.put(RowLocation.COLUMN_LATITUDE, latLng.latitude);
        values.put(RowLocation.COLUMN_LONGITUDE, latLng.longitude);
        values.put(RowLocation.COLUMN_NAME, row.getName());

        return values;
    }
}