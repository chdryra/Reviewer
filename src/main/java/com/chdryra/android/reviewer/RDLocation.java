/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.google.android.gms.maps.model.LatLng;

/**
 * Review Data: location
 * <p>
 * {@link #hasData()}: A LatLng plus a name at least 1 character in length.
 * </p>
 */
class RDLocation implements RData {
    static final String LOCATION_DELIMITER = ",|";
    private final LatLng mLatLng;
    private final String mName;
    private       Review mHoldingReview;

    RDLocation(LatLng latLng, String name, Review holdingReview) {
        mLatLng = latLng;
        mName = name;
        mHoldingReview = holdingReview;
    }

    @Override
    public Review getHoldingReview() {
        return mHoldingReview;
    }

    @Override
    public void setHoldingReview(Review review) {
        mHoldingReview = review;
    }

    @Override
    public boolean hasData() {
        return mLatLng != null && mName != null && mName.length() > 0;
    }

    LatLng getLatLng() {
        return mLatLng;
    }

    String getName() {
        return mName;
    }
}
