/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalImage;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalImageTest extends CanonicalGvDataTest<GvImageList.GvImage> {
    private static final GvImageList.GvImage IMAGE1 = GvDataMocker.newImage(null);
    private static final GvImageList.GvImage IMAGE2 = GvDataMocker.newImage(null);
    private static final GvImageList.GvImage IMAGE3 = GvDataMocker.newImage(null);
    private static final GvImageList.GvImage IMAGE4 = GvDataMocker.newImage(null);

    //protected methods
    private void checkDifferentBitmapsNotValid() {
        mData = newDataList();
        mData.add(IMAGE1);
        mData.add(IMAGE2);
        mData.add(IMAGE3);
        mData.add(IMAGE4);
        GvImageList.GvImage canon = mCanonical.getCanonical(mData);
        assertFalse(canon.isValidForDisplay());
    }

    private void checkSameBitmapsDifferentDatesNoCaptions() {
        Calendar c = Calendar.getInstance();
        Date imageDate1 = IMAGE1.getDate();
        c.setTime(imageDate1);
        c.add(Calendar.DATE, 1);
        Date imageDate2 = c.getTime();
        c.add(Calendar.DATE, 1);
        Date imageDate3 = c.getTime();
        c.add(Calendar.DATE, 1);
        Date imageDate4 = c.getTime();

        Bitmap bitmap = IMAGE1.getBitmap();
        LatLng ll = IMAGE1.getLatLng();
        GvImageList.GvImage image1 = new GvImageList.GvImage(bitmap, imageDate1, ll, null, false);
        GvImageList.GvImage image2 = new GvImageList.GvImage(bitmap, imageDate4, ll, null, false);
        GvImageList.GvImage image3 = new GvImageList.GvImage(bitmap, imageDate2, ll, null, false);
        GvImageList.GvImage image4 = new GvImageList.GvImage(bitmap, imageDate3, ll, null, false);

        mData = newDataList();
        mData.add(image1);
        mData.add(image2);
        mData.add(image3);
        mData.add(image4);

        GvImageList.GvImage canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertTrue(bitmap.sameAs(canon.getBitmap()));
        assertEquals(imageDate4, canon.getDate());
        assertNull(canon.getCaption());
        assertTrue(canon.isCover());
    }

    private void checkSameBitmapsDifferentCaptions() {
        Calendar c = Calendar.getInstance();
        Date imageDate1 = IMAGE1.getDate();
        String cap1 = IMAGE1.getCaption();
        String cap2 = IMAGE2.getCaption();
        String cap3 = IMAGE3.getCaption();

        Bitmap bitmap = IMAGE1.getBitmap();
        LatLng ll = IMAGE1.getLatLng();
        GvImageList.GvImage image1 = new GvImageList.GvImage(bitmap, imageDate1, ll, cap1, false);
        GvImageList.GvImage image2 = new GvImageList.GvImage(bitmap, imageDate1, ll, cap2, false);
        GvImageList.GvImage image3 = new GvImageList.GvImage(bitmap, imageDate1, ll, cap1, false);
        GvImageList.GvImage image4 = new GvImageList.GvImage(bitmap, imageDate1, ll, cap2, false);
        GvImageList.GvImage image5 = new GvImageList.GvImage(bitmap, imageDate1, ll, cap3, false);

        mData = newDataList();
        mData.add(image1);
        mData.add(image2);
        mData.add(image3);
        mData.add(image4);
        mData.add(image5);

        GvImageList.GvImage canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertTrue(bitmap.sameAs(canon.getBitmap()));
        assertEquals(imageDate1, canon.getDate());
        assertEquals("3 captions", canon.getCaption());
        assertTrue(canon.isCover());
    }

    //Overridden
    @Override
    protected void additionalTests() {
        checkDifferentBitmapsNotValid();
        checkSameBitmapsDifferentDatesNoCaptions();
        checkSameBitmapsDifferentCaptions();
    }

    @Override
    protected GvImageList.GvImage getTestDatum() {
        return IMAGE1;
    }

    @Override
    protected CanonicalDatumMaker<GvImageList.GvImage> getCanonicalMaker() {
        return new CanonicalImage();
    }

    @Override
    protected void checkEquality(GvImageList.GvImage lhs, GvImageList.GvImage rhs) {
        assertTrue(lhs.getBitmap().sameAs(rhs.getBitmap()));
        assertTrue(lhs.getCaption().equals(rhs.getCaption()));
        assertTrue(lhs.getDate().equals(rhs.getDate()));
        assertEquals(lhs.getLatLng(), rhs.getLatLng());
    }
}
