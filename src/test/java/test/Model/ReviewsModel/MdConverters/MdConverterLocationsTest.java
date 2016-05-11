/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters.MdConverterLocations;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import test.TestUtils.DataEquivalence;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterLocationsTest extends MdConverterBasicTest<DataLocation, MdLocation> {
    public MdConverterLocationsTest() {
        super(new MdConverterLocations());
    }

    @Override
    protected DataLocation newDatum() {
        return new Location();
    }

    @Override
    protected void checkDatumEquivalence(DataLocation datum, MdLocation mdDatum) {
        DataEquivalence.checkEquivalence(datum, mdDatum);
    }

    @Override
    protected void checkDatumEquivalence(DataLocation datum, MdLocation mdDatum, ReviewId mdDatumId) {
        DataEquivalence.checkEquivalence(datum, mdDatum, mdDatumId);
    }

    private static class Location implements DataLocation {
        private LatLng mLatLng;
        private String mName;
        private ReviewId mId;

        public Location() {
            mLatLng = RandomLatLng.nextLatLng();
            mName = RandomString.nextWord();
            mId = RandomReviewId.nextReviewId();
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
            return mId;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return true;
        }
    }
}
