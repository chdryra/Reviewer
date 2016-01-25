/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocationImpl extends RowTableBasic implements RowLocation {
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
    protected RowEntry<?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(LOCATION_ID, mLocationId);
        } else if(position == 1) {
            return new RowEntryImpl<>(REVIEW_ID, mReviewId);
        } else if(position == 2) {
            return new RowEntryImpl<>(LATITUDE, mLatitude);
        } else if(position == 3) {
            return new RowEntryImpl<>(LONGITUDE, mLongitude);
        } else if(position == 4) {
            return new RowEntryImpl<>(NAME, mName);
        } else {
            throw noElement();
        }
    }
}
