package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocationImpl implements RowLocation {
    private static final String SEPARATOR = ":";

    private String mLocationId;
    private String mReviewId;
    private double mLatitude;
    private double mLongitude;
    private String mName;

    //Constructors
    public RowLocationImpl(DataLocation location, int index) {
        mReviewId = location.getReviewId().toString();
        mLocationId = mReviewId + SEPARATOR + "l" + String.valueOf(index);
        mLatitude = location.getLatLng().latitude;
        mLongitude = location.getLatLng().longitude;
        mName = location.getName();
    }

    //Via reflection
    public RowLocationImpl() {
    }

    public RowLocationImpl(RowValues values) {
        mLocationId = values.getString(COLUMN_LOCATION_ID);
        mReviewId = values.getString(COLUMN_REVIEW_ID);
        mLatitude = values.getDouble(COLUMN_LATITUDE);
        mLongitude = values.getDouble(COLUMN_LONGITUDE);
        mName = values.getString(COLUMN_NAME);
    }


    //Overridden

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId(mReviewId);
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
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
