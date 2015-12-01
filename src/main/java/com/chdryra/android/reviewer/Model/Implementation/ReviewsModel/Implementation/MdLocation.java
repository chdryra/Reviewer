package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Review Data: location
 */
public class MdLocation implements DataLocation {
    private final LatLng mLatLng;
    private final String mName;
    private final MdReviewId mReviewId;

    //Constructors
    public MdLocation(MdReviewId reviewId, LatLng latLng, String name) {
        mLatLng = latLng;
        mName = name;
        mReviewId = reviewId;
    }

    //Overridden
    @Override
    public String getReviewId() {
        return mReviewId.toString();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdLocation)) return false;

        MdLocation that = (MdLocation) o;

        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that
                .mReviewId != null) {
            return false;
        }
        if (mLatLng != null ? !mLatLng.equals(that.mLatLng) : that.mLatLng != null) {
            return false;
        }
        if (mName != null ? !mName.equals(that.mName) : that.mName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mLatLng != null ? mLatLng.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }
}
