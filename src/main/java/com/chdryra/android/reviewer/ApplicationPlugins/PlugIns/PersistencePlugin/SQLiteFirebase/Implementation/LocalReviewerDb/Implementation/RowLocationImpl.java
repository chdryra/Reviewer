/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowValues;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocationImpl extends RowTableBasic<RowLocation> implements RowLocation {
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
        mLocationId = values.getValue(LOCATION_ID.getName(), LOCATION_ID.getType());
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());
        Double lat = values.getValue(LATITUDE.getName(), LATITUDE.getType());
        mLatitude = lat != null ? lat : -91.;
        Double lng = values.getValue(LONGITUDE.getName(), LONGITUDE.getType());
        mLongitude = lng !=  null ? lng : -181.;
        mName = values.getValue(NAME.getName(), NAME.getType());
    }

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
        return LOCATION_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this)
                && validator.validateString(mReviewId)
                && validator.validateString(mLocationId);
    }

    @Override
    protected int size() {
        return 5;
    }

    @Override
    protected RowEntry<RowLocation, ?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(RowLocation.class, LOCATION_ID, mLocationId);
        } else if(position == 1) {
            return new RowEntryImpl<>(RowLocation.class, REVIEW_ID, mReviewId);
        } else if(position == 2) {
            return new RowEntryImpl<>(RowLocation.class, LATITUDE, mLatitude);
        } else if(position == 3) {
            return new RowEntryImpl<>(RowLocation.class, LONGITUDE, mLongitude);
        } else if(position == 4) {
            return new RowEntryImpl<>(RowLocation.class, NAME, mName);
        } else {
            throw noElement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowLocationImpl)) return false;

        RowLocationImpl that = (RowLocationImpl) o;

        if (Double.compare(that.mLatitude, mLatitude) != 0) return false;
        if (Double.compare(that.mLongitude, mLongitude) != 0) return false;
        if (mLocationId != null ? !mLocationId.equals(that.mLocationId) : that.mLocationId != null)
            return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null)
            return false;
        return !(mName != null ? !mName.equals(that.mName) : that.mName != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mLocationId != null ? mLocationId.hashCode() : 0;
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        temp = Double.doubleToLongBits(mLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        return result;
    }
}
