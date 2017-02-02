/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Utils.RatingFormatter;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by: Rizwan Choudrey
 * On: 24/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewClusterItem implements ClusterItem {
    private final ReviewReference mReference;
    private final DataLocation mLocation;

    public ReviewClusterItem(ReviewReference reference, DataLocation location) {
        if(!reference.getReviewId().equals(location.getReviewId())) {
            throw new IllegalArgumentException("Reference and location must have same ReviewId");
        }

        mReference = reference;
        mLocation = location;
    }

    public ReviewReference getReference() {
        return mReference;
    }

    public DataLocation getLocation() {
        return mLocation;
    }

    @Override
    public LatLng getPosition() {
        return mLocation.getLatLng();
    }

    @Override
    public String getTitle() {
        return mReference.getSubject().getSubject() + " "
                + RatingFormatter.upToTwoSignificantDigits(mReference.getRating().getRating())
                + "*";
    }

    @Override
    public String getSnippet() {
        return mLocation.getAddress();
    }
}
