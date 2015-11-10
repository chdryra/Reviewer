package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocation implements ReviewDataRow, DataLocation {
    public static final String COLUMN_LOCATION_ID = "location_id";
    public static final String COLUMN_REVIEW_ID = "review_id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_NAME = "name";

    private static final String SEPARATOR = ":";

    private String mLocationId;
    private String mReviewId;
    private double mLatitude;
    private double mLongitude;
    private String mName;

    //Constructors
    public RowLocation(DataLocation location, int index) {
        mReviewId = location.getReviewId();
        mLocationId = mReviewId + SEPARATOR + "l" + String.valueOf(index);
        mLatitude = location.getLatLng().latitude;
        mLongitude = location.getLatLng().longitude;
        mName = location.getName();
    }

    //Via reflection
    public RowLocation() {
    }

    public RowLocation(Cursor cursor) {
        mLocationId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mLatitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE));
        mLongitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE));
        mName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
    }


    //Overridden

    @Override
    public String getReviewId() {
        return mReviewId;
    }

    @Override
    public LatLng getLatLng() {
        return new LatLng(mLatitude, mLongitude);
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getRowId() {
        return mLocationId;
    }

    @Override
    public String getRowIdColumnName() {
        return COLUMN_LOCATION_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION_ID, mLocationId);
        values.put(COLUMN_REVIEW_ID, mReviewId);
        values.put(COLUMN_LATITUDE, mLatitude);
        values.put(COLUMN_LONGITUDE, mLongitude);
        values.put(COLUMN_NAME, mName);

        return values;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
