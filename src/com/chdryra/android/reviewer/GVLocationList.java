/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.google.android.gms.maps.model.LatLng;

import java.util.StringTokenizer;

/**
 * GVReviewDataList: GVLocation
 * ViewHolder: VHLocationView
 *
 * @see com.chdryra.android.reviewer.FragmentReviewLocations
 * @see com.chdryra.android.reviewer.VHLocationView
 */
class GVLocationList extends GVReviewDataList<GVLocationList.GVLocation> {

    GVLocationList() {
        super(GVType.LOCATIONS);
    }

    void add(LatLng latLng, String name) {
        add(new GVLocation(latLng, name));
    }

    void remove(LatLng latLng, String name) {
        remove(new GVLocation(latLng, name));
    }

    boolean contains(LatLng latLng, String name) {
        return contains(new GVLocation(latLng, name));
    }

    /**
     * GVData version of: RDLocation
     * ViewHolder: VHLocationView
     * <p/>
     * <p>
     * Methods for getting the LatLng, name and shortened version of name.
     * </p>
     *
     * @see com.chdryra.android.mygenerallibrary.GVData
     * @see com.chdryra.android.reviewer.RDLocation
     * @see com.chdryra.android.reviewer.VHLocationView
     */
    class GVLocation implements GVData {
        private final LatLng mLatLng;
        private final String mName;

        GVLocation(LatLng latLng, String name) {
            mLatLng = latLng;
            mName = name;
        }

        LatLng getLatLng() {
            return mLatLng;
        }

        String getName() {
            return mName;
        }

        String getShortenedName() {
            if (mName != null) {
                StringTokenizer tokens = new StringTokenizer(mName, RDLocation.LOCATION_DELIMITER);
                String shortened = tokens.nextToken();
                return shortened != null ? shortened.trim() : null;
            } else {
                return null;
            }
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHLocationView(false);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            GVLocation other = (GVLocation) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (mLatLng == null) {
                if (other.mLatLng != null) {
                    return false;
                }
            } else if (!mLatLng.equals(other.mLatLng)) {
                return false;
            }
            if (mName == null) {
                if (other.mName != null) {
                    return false;
                }
            } else if (!mName.equals(other.mName)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result
                    + ((mLatLng == null) ? 0 : mLatLng.hashCode());
            result = prime * result + ((mName == null) ? 0 : mName.hashCode());
            return result;
        }

        private GVLocationList getOuterType() {
            return GVLocationList.this;
        }
    }
}
