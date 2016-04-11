/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin.DataAggregatorsDefault.FactoryDataAggregatorDefault.Implementation.CanonicalLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin.DataAggregatorsDefault.FactoryDataAggregatorDefault.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 10/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalLocationTest extends CanonicalGvDataTest<GvLocation> {
    private static final String NYC = "New York";
    private static final double NYC_LAT = 40.7143528;
    private static final double NYC_LNG = -74.0059731;

    private static final String CHI = "Chicago";
    private static final double CHI_LAT = 41.8781136;
    private static final double CHI_LNG = -87.6297982;

    private static final String ATL = "Atalanta";
    private static final double ATL_LAT = 33.7489954;
    private static final double ATL_LNG = -84.3879824;

    private static final double MID_LAT = 39.423527;
    private static final double MID_LNG = -80.077744;

//protected methods
    @Override
    protected GvLocation getTestDatum() {
        return new GvLocation(new LatLng(NYC_LAT, NYC_LNG), NYC);
    }

    @Override
    protected CanonicalDatumMaker<GvLocation> getCanonicalMaker() {
        return new CanonicalLocation();
    }

    private void checkMidpoint() {
        LatLng nycLL = new LatLng(NYC_LAT, NYC_LNG);
        LatLng chiLL = new LatLng(CHI_LAT, CHI_LNG);
        LatLng atlLL = new LatLng(ATL_LAT, ATL_LNG);
        LatLng midLL = new LatLng(MID_LAT, MID_LNG);

        GvLocation nyc = new GvLocation(nycLL, NYC);
        GvLocation chi = new GvLocation(chiLL, CHI);
        GvLocation atl = new GvLocation(atlLL, ATL);
        GvLocation mid = new GvLocation(midLL, NYC + " + 2");

        mData = newDataList();
        mData.add(chi);
        mData.add(nyc);
        mData.add(atl);
        mData.add(nyc);

        GvLocation canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(mid.getName(), canon.getName());
        assertEquals(mid.getLatLng().latitude, canon.getLatLng().latitude, 0.01);
        assertEquals(mid.getLatLng().longitude, canon.getLatLng().longitude, 0.01);
    }

    //Overridden
    @Override
    protected void additionalTests() {
        checkMidpoint();
    }
}
