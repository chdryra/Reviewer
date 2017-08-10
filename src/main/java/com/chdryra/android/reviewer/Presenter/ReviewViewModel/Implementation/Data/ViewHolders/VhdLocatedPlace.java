/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.mygenerallibrary.LocationServices.LocatedPlace;

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
    public ViewHolder getViewHolder() {
        return new VhLocatedPlace();
    }

    @Override
    public boolean isValidForDisplay() {
        return mPlace != null && mPlace.getLatLng() != null && mPlace.getDescription() != null
                && mPlace.getDescription().length() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VhdLocatedPlace)) return false;

        VhdLocatedPlace that = (VhdLocatedPlace) o;

        return mPlace != null ? mPlace.equals(that.mPlace) : that.mPlace == null;

    }

    @Override
    public int hashCode() {
        return mPlace != null ? mPlace.hashCode() : 0;
    }
}
