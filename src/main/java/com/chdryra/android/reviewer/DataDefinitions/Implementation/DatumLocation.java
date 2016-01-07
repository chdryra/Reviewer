package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
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

    public DatumLocation(ReviewId reviewId) {
        mReviewId = reviewId;
        mLatLng = null;
        mName = "";
    }

    public DatumLocation(ReviewId reviewId, LatLng latLng, String name) {
        mReviewId = reviewId;
        mLatLng = latLng;
        mName = name;
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
    public ReviewId getReviewId() {
        return mReviewId;
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
        if (mLatLng != null ? !mLatLng.equals(that.mLatLng) : that.mLatLng != null) return false;
        return mName.equals(that.mName);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
        result = 31 * result + mName.hashCode();
        return result;
    }
}
