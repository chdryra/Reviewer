/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalLocation;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 10/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalLocationTest extends CanonicalGvDataTest<GvLocationList.GvLocation> {
    private static final String NYC     = "New York";
    private static final double NYC_LAT = 40.7143528;
    private static final double NYC_LNG = -74.0059731;

    private static final String CHI     = "Chicago";
    private static final double CHI_LAT = 41.8781136;
    private static final double CHI_LNG = -87.6297982;

    private static final String ATL     = "Atalanta";
    private static final double ATL_LAT = 33.7489954;
    private static final double ATL_LNG = -84.3879824;

    private static final double MID_LAT = 39.423527;
    private static final double MID_LNG = -80.077744;

    @Override
    protected GvLocationList.GvLocation getTestDatum() {
        return new GvLocationList.GvLocation(new LatLng(NYC_LAT, NYC_LNG), NYC);
    }

    @Override
    protected CanonicalDatumMaker<GvLocationList.GvLocation> getCanonicalMaker() {
        return new CanonicalLocation();
    }

    @Override
    protected void additionalTests() {
        checkMidpoint();
    }

    private void checkMidpoint() {
        LatLng nycLL = new LatLng(NYC_LAT, NYC_LNG);
        LatLng chiLL = new LatLng(CHI_LAT, CHI_LNG);
        LatLng atlLL = new LatLng(ATL_LAT, ATL_LNG);
        LatLng midLL = new LatLng(MID_LAT, MID_LNG);

        GvLocationList.GvLocation nyc = new GvLocationList.GvLocation(nycLL, NYC);
        GvLocationList.GvLocation chi = new GvLocationList.GvLocation(chiLL, CHI);
        GvLocationList.GvLocation atl = new GvLocationList.GvLocation(atlLL, ATL);
        GvLocationList.GvLocation mid = new GvLocationList.GvLocation(midLL, NYC + " + 2");

        mData = newDataList();
        mData.add(chi);
        mData.add(nyc);
        mData.add(atl);
        mData.add(nyc);

        GvLocationList.GvLocation canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(mid.getName(), canon.getName());
        assertEquals(mid.getLatLng().latitude, canon.getLatLng().latitude, 0.01);
        assertEquals(mid.getLatLng().longitude, canon.getLatLng().longitude, 0.01);
    }
}
