/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumLocation implements DataLocation {
    private final ReviewId mReviewId;
    private final LatLng mLatLng;
    private final String mName;
    private final String mAddress;
    private final LocationId mLocationId;

    public DatumLocation(ReviewId reviewId) {
        mReviewId = reviewId;
        mLatLng = new LatLng(0,0);
        mName = "";
        mAddress = "";
        mLocationId = LocationId.withProviderName(reviewId.toString(), mLatLng);
    }

    public DatumLocation(ReviewId reviewId, LatLng latLng, String name, String address, LocationId locationId) {
        mReviewId = reviewId;
        mLatLng = latLng;
        mName = name;
        mAddress = address;
        mLocationId = locationId;
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
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
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public String getAddress() {
        return mAddress;
    }

    @Override
    public LocationId getLocationId() {
        return mLocationId;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumLocation)) return false;

        DatumLocation that = (DatumLocation) o;

        if (!mReviewId.equals(that.mReviewId)) return false;
        if (!mLatLng.equals(that.mLatLng)) return false;
        if (!mName.equals(that.mName)) return false;
        if (!mAddress.equals(that.mAddress)) return false;
        return mLocationId.equals(that.mLocationId);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + mLatLng.hashCode();
        result = 31 * result + mName.hashCode();
        result = 31 * result + mAddress.hashCode();
        result = 31 * result + mLocationId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
