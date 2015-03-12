/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer;

import android.location.Location;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhdLocatedPlaceDistance implements ViewHolderData {
    private LocatedPlace mPlace;
    private float[] mDistanceAndBearings = new float[3];

    public VhdLocatedPlaceDistance(LocatedPlace place, LatLng origin) {
        mPlace = place;
        LatLng latLng = mPlace.getLatLng();
        if (origin != null) {
            Location.distanceBetween(origin.latitude, origin.longitude, latLng.latitude,
                    latLng.longitude, mDistanceAndBearings);
        } else {
            mDistanceAndBearings[0] = -1f;
        }
    }

    public LocatedPlace getPlace() {
        return mPlace;
    }

    public int getDistance() {
        return (int) mDistanceAndBearings[0];
    }

    @Override
    public ViewHolder newViewHolder() {
        return new VhLocatedPlaceDistance();
    }

    @Override
    public boolean isValidForDisplay() {
        return mPlace.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VhdLocatedPlaceDistance)) return false;

        VhdLocatedPlaceDistance that = (VhdLocatedPlaceDistance) o;

        if (!Arrays.equals(mDistanceAndBearings, that.mDistanceAndBearings)) return false;
        if (mPlace != null ? !mPlace.equals(that.mPlace) : that.mPlace != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mPlace != null ? mPlace.hashCode() : 0;
        result = 31 * result + (mDistanceAndBearings != null ? Arrays.hashCode
                (mDistanceAndBearings) : 0);
        return result;
    }
}
