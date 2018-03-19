/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Utils.RatingFormatter;
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
        if (!reference.getReviewId().equals(location.getReviewId())) {
            throw new IllegalArgumentException("Reference and location must have same ReviewId");
        }

        mReference = reference;
        mLocation = location;
    }

    public ReviewId getReviewId() {
        return mReference.getReviewId();
    }

    public ReviewReference getReference() {
        return mReference;
    }

    public DataLocation getLocation() {
        return mLocation;
    }

    public float getRating() {
        return mReference.getRating().getRating();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewClusterItem)) return false;

        ReviewClusterItem that = (ReviewClusterItem) o;

        return mLocation != null ? mLocation.equals(that.mLocation) : that.mLocation == null;
    }

    @Override
    public int hashCode() {
        return mLocation != null ? mLocation.hashCode() : 0;
    }
}
