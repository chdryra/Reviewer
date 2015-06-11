/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 March, 2015
 */

package com.chdryra.android.reviewer.test.View;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.Utils.GpAutoCompleter;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 18/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GpAutoCompleterTest extends TestCase {
    private static final LatLng                    TAYYABS_LL   = new LatLng(51.517264, -0.063484);
    private static final String                    TAYYABS_NAME = "Tayyabs";
    private static final GvLocationList.GvLocation TAYYABS      = new GvLocationList.GvLocation
            (TAYYABS_LL, TAYYABS_NAME);

    @SmallTest
    public void testFilter() {
        GpAutoCompleter filter = new GpAutoCompleter(TAYYABS.getLatLng());
        ViewHolderDataList filtered = filter.filter("Ta");
        assertTrue(filtered.size() > 2);
        filtered = filter.filter("Tayyabs");
        assertTrue(filtered.size() == 2);
        filtered = filter.filter("Tayyabsqewhuaudsu");
        assertTrue(filtered.size() == 0);
    }
}
