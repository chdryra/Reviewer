/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.Models.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.MdData;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdLocationList extends MdDataList<MdLocationList.MdLocation> {

    //Constructors
    public MdLocationList(MdReviewId reviewId) {
        super(reviewId);
    }

//Classes

    /**
     * Review Data: location
     */
    public static class MdLocation implements MdData, DataLocation {
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
}
