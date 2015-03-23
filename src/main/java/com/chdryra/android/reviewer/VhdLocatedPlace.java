/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhdLocatedPlace implements ViewHolderData {
    private final LocatedPlace mPlace;

    public VhdLocatedPlace(LocatedPlace place) {
        mPlace = place;
    }

    public LocatedPlace getPlace() {
        return mPlace;
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
        if (!(o instanceof VhdLocatedPlace)) return false;

        VhdLocatedPlace that = (VhdLocatedPlace) o;

        if (mPlace != null ? !mPlace.equals(that.mPlace) : that.mPlace != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mPlace != null ? mPlace.hashCode() : 0;
    }
}
