/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterLocations extends
        GvConverterReviewData.RefDataList<DataLocation, GvLocation, GvLocationList, GvLocation
                .Reference> {

    public GvConverterLocations() {
        super(GvLocationList.class, GvLocation.Reference.TYPE);
    }

    @Override
    public GvLocation convert(DataLocation datum, @Nullable ReviewId reviewId) {
        return new GvLocation(newId(reviewId), datum.getLatLng(), datum.getName(),
                datum.getAddress(), datum.getLocationId());
    }

    @Override
    protected GvLocation.Reference convertReference(ReviewItemReference<DataLocation> reference) {
        return new GvLocation.Reference(reference, this);
    }
}
