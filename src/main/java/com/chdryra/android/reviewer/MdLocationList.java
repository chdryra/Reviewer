/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdLocationList extends MdDataList<MdLocationList.MdLocation> {

    public MdLocationList(Review holdingReview) {
        super(holdingReview);
    }

    /**
     * Review Data: location
     * <p>
     * {@link #hasData()}: A LatLng plus a name at least 1 character in length.
     * </p>
     */
    public static class MdLocation implements MdData, DataLocation {
        static final String LOCATION_DELIMITER = ",|";
        private final LatLng mLatLng;
        private final String mName;
        private       Review mHoldingReview;

        public MdLocation(LatLng latLng, String name, Review holdingReview) {
            mLatLng = latLng;
            mName = name;
            mHoldingReview = holdingReview;
        }

        @Override
        public Review getHoldingReview() {
            return mHoldingReview;
        }

        @Override
        public boolean hasData() {
            return mLatLng != null && mName != null && mName.length() > 0;
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

            if (mHoldingReview != null ? !mHoldingReview.equals(that.mHoldingReview) : that
                    .mHoldingReview != null) {
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
            result = 31 * result + (mHoldingReview != null ? mHoldingReview.hashCode() : 0);
            return result;
        }
    }
}
