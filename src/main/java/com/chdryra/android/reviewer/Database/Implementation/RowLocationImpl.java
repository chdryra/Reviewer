package com.chdryra.android.reviewer.Database.Implementation;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocationImpl implements RowLocation {
    private static final String SEPARATOR = ":";

    private String mLocationId;
    private ReviewId mReviewId;
    private double mLatitude;
    private double mLongitude;
    private String mName;

    //Constructors
    public RowLocationImpl(DataLocation location, int index) {
        mReviewId = location.getReviewId();
        mLocationId = mReviewId + SEPARATOR + "l" + String.valueOf(index);
        mLatitude = location.getLatLng().latitude;
        mLongitude = location.getLatLng().longitude;
        mName = location.getName();
    }

    //Via reflection
    public RowLocationImpl() {
    }

    public RowLocationImpl(Cursor cursor) {
        mLocationId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mLatitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE));
        mLongitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE));
        mName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
    }


    //Overridden

    @Override
    public ReviewId getReviewId() {
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
