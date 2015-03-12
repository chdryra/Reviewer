/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocatedPlace {
    private LatLng mLatLng;
    private String mName;
    private String mAddress;
    private String mId;

    public LocatedPlace(LatLng latLng, String name, String address, String id) {
        mLatLng = latLng;
        mName = name;
        mAddress = address;
        mId = id;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getId() {
        return mId;
    }

    public boolean isValid() {
        return mLatLng != null && DataValidator.validateString(mName) && DataValidator
                .validateString(mId);
    }
}
