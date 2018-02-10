/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;



import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowValues;



import com.chdryra.android.corelibrary.LocationServices.LocationId;
import com.chdryra.android.corelibrary.LocationServices.LocationProvider;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;
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
    private String mAddress;
    private LocationId mId;

    //Constructors
    public RowLocationImpl(DataLocation location, int index) {
        mReviewId = location.getReviewId().toString();
        mLocationId = mReviewId + SEPARATOR + "l" + String.valueOf(index);
        mLatitude = location.getLatLng().latitude;
        mLongitude = location.getLatLng().longitude;
        mName = location.getName();
        mAddress = location.getAddress();
        mId = location.getLocationId();
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
        mAddress = values.getValue(ADDRESS.getName(), ADDRESS.getType());
        String provider = values.getValue(PROVIDER.getName(), PROVIDER.getType());
        String providerId = values.getValue(PROVIDER_ID.getName(), PROVIDER_ID.getType());

        mId = provider != null ? new LocationId(new LocationProvider(provider), providerId) :
        LocationId.withProviderName(mName, new LatLng(mLatitude, mLongitude));
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
    public String getShortenedName() {
        return DataFormatter.getShortenedName(mName);
    }

    @Override
    public String getAddress() {
        return mAddress;
    }

    @Override
    public LocationId getLocationId() {
        return mId;
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
        return 8;
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
        } else if(position == 5) {
            return new RowEntryImpl<>(RowLocation.class, ADDRESS, mAddress);
        } else if(position == 6) {
            return new RowEntryImpl<>(RowLocation.class, PROVIDER, mId.getProvider().getProviderName());
        } else if(position == 7) {
            return new RowEntryImpl<>(RowLocation.class, PROVIDER_ID, mId.getId());
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
        if (!mLocationId.equals(that.mLocationId)) return false;
        if (!mReviewId.equals(that.mReviewId)) return false;
        if (!mName.equals(that.mName)) return false;
        if (!mAddress.equals(that.mAddress)) return false;
        return mId.equals(that.mId);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mLocationId.hashCode();
        result = 31 * result + mReviewId.hashCode();
        temp = Double.doubleToLongBits(mLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + mName.hashCode();
        result = 31 * result + mAddress.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
