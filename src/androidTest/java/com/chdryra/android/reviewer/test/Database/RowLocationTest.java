/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 April, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import android.content.ContentValues;
import android.database.MatrixCursor;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.FactoryTableRow;
import com.chdryra.android.reviewer.Database.RowLocation;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.test.TestUtils.MdDataMocker;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocationTest extends TestCase {
    private static final int INDEX = 314;
    private MdLocationList.MdLocation mLocation;

    @SmallTest
    public void testLocationConstructor() {
        testRow(new RowLocation(mLocation, INDEX));
    }

    @SmallTest
    public void testCursorConstructor() {
        String[] cols = new String[]{RowLocation.LOCATION_ID, RowLocation.REVIEW_ID, RowLocation
                .LAT, RowLocation.LNG, RowLocation.NAME};

        MatrixCursor cursor = new MatrixCursor(cols);
        String reviewId = mLocation.getReviewId().toString();
        String datumId = getDatumId();
        LatLng latlng = mLocation.getLatLng();
        cursor.addRow(new Object[]{datumId, reviewId, latlng.latitude, latlng.longitude,
                mLocation.getName()});
        cursor.moveToFirst();
        testRow(new RowLocation(cursor));
    }

    //private methods
    private String getDatumId() {
        return mLocation.getReviewId().toString() + FactoryTableRow.SEPARATOR + "l" + String.valueOf
                (INDEX);
    }

    private void testRow(RowLocation row) {
        LatLng latlng = mLocation.getLatLng();
        ContentValues values = row.getContentValues();
        assertEquals(getDatumId(), values.getAsString(RowLocation.LOCATION_ID));
        assertEquals(mLocation.getReviewId().toString(), values.getAsString(RowLocation.REVIEW_ID));
        assertEquals(latlng.latitude, values.getAsDouble(RowLocation.LAT));
        assertEquals(latlng.longitude, values.getAsDouble(RowLocation.LNG));
        assertEquals(mLocation.getName(), values.getAsString(RowLocation.NAME));
        assertEquals(mLocation, row.toMdData());
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        MdDataMocker mocker = new MdDataMocker();
        mLocation = mocker.newLocation();
    }
}
