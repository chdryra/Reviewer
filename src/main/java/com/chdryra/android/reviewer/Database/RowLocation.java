package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocation implements MdDataRow<MdLocationList.MdLocation> {
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
    private DataValidator mValidator;

    //Constructors
    public RowLocation(MdLocationList.MdLocation location, int index, DataValidator validator) {
        mReviewId = location.getReviewId().toString();
        mLocationId = mReviewId + SEPARATOR + "l" + String.valueOf(index);
        mLatitude = location.getLatLng().latitude;
        mLongitude = location.getLatLng().longitude;
        mName = location.getName();
        mValidator = validator;
    }

    //Via reflection
    public RowLocation() {
    }

    public RowLocation(Cursor cursor, DataValidator validator) {
        mLocationId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mLatitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE));
        mLongitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE));
        mName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
        mValidator = validator;
    }


    //Overridden
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
    public boolean hasData() {
        return mValidator != null && mValidator.validateString(getRowId());
    }

    @Override
    public MdLocationList.MdLocation toMdData() {
        LatLng latlng = new LatLng(mLatitude, mLongitude);
        ReviewId id = ReviewId.fromString(mReviewId);
        return new MdLocationList.MdLocation(latlng, mName, id);
    }
}
