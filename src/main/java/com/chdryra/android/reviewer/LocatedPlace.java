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
    private String mDescription;
    private String mId;

    public LocatedPlace(LatLng latLng, String description, String id) {
        mLatLng = latLng;
        mDescription = description;
        mId = id;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getId() {
        return mId;
    }

    public boolean isValid() {
        return mLatLng != null && DataValidator.validateString(mDescription) && DataValidator
                .validateString(mId);
    }
}
