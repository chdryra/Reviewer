/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvImageListTest extends TestCase {
    private static final int NUM = 50;
    private GvImageList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvImageList.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testGvImage() {
        GvImageList.GvImage image1 = GvDataMocker.newImage();
        GvImageList.GvImage image2 = GvDataMocker.newImage();

        Bitmap bitmap1 = image1.getBitmap();
        LatLng latLng1 = image1.getLatLng();
        String caption1 = image1.getCaption();
        boolean isCover1 = image1.isCover();

        Bitmap bitmap2 = image2.getBitmap();
        LatLng latLng2 = image2.getLatLng();
        String caption2 = image2.getCaption();
        boolean isCover2 = !isCover1;

        GvImageList.GvImage gvImage = new GvImageList.GvImage(bitmap1, latLng1, caption1, isCover1);
        GvImageList.GvImage gvImageEquals = new GvImageList.GvImage(bitmap1, latLng1, caption1,
                isCover1);

        GvImageList.GvImage gvImageNotEquals1 = new GvImageList.GvImage(bitmap1, latLng2,
                caption2, isCover2);
        GvImageList.GvImage gvImageNotEquals2 = new GvImageList.GvImage(bitmap2, latLng1,
                caption2, isCover2);
        GvImageList.GvImage gvImageNotEquals3 = new GvImageList.GvImage(bitmap2, latLng2,
                caption1, isCover2);
        GvImageList.GvImage gvImageNotEquals4 = new GvImageList.GvImage(bitmap2, latLng2,
                caption2, isCover2);

        GvImageList.GvImage gvImageNull = new GvImageList.GvImage();
        GvImageList.GvImage gvImageEmpty = new GvImageList.GvImage(null, latLng1, caption1,
                isCover1);
        GvImageList.GvImage gvImageNotEmpty = new GvImageList.GvImage(bitmap1, null, null,
                isCover1);


        assertNotNull(gvImage.newViewHolder());
        assertTrue(gvImage.isValidForDisplay());

        assertTrue(bitmap1.sameAs(gvImage.getBitmap()));
        assertEquals(caption1, gvImage.getCaption());
        assertEquals(latLng1, gvImage.getLatLng());
        assertEquals(isCover1, gvImage.isCover());
        gvImage.setIsCover(!isCover1);
        assertEquals(!isCover1, gvImage.isCover());
        gvImage.setIsCover(isCover1);

        assertTrue(gvImage.equals(gvImageEquals));
        assertFalse(gvImage.equals(gvImageNotEquals1));
        assertFalse(gvImage.equals(gvImageNotEquals2));
        assertFalse(gvImage.equals(gvImageNotEquals3));
        assertFalse(gvImage.equals(gvImageNotEquals4));

        assertFalse(gvImageNull.isValidForDisplay());
        assertFalse(gvImageEmpty.isValidForDisplay());
        assertTrue(gvImageNotEmpty.isValidForDisplay());
    }

    @SmallTest
    public void testContainsBitmap() {
        GvImageList.GvImage image = GvDataMocker.newImage();
        assertFalse(mList.contains(image.getBitmap()));
        mList.add(image);
        assertTrue(mList.contains(image.getBitmap()));

        GvImageList.GvImage image2 = GvDataMocker.newImage();
        assertFalse(mList.contains(image2.getBitmap()));
        mList.add(image2);
        assertTrue(mList.contains(image2.getBitmap()));

        mList.remove(image);
        assertFalse(mList.contains(image.getBitmap()));
    }

    @SmallTest
    public void testGetCovers() {
        GvImageList covers = new GvImageList();
        for (int i = 0; i < 100; ++i) {
            GvImageList.GvImage image = GvDataMocker.newImage();
            if (image.isCover()) covers.add(image);
            mList.add(image);
        }

        GvImageList coversFound = mList.getCovers();
        assertEquals(covers.size(), coversFound.size());

        for (int i = 0; i < covers.size(); ++i) {
            GvImageList.GvImage cover = coversFound.getItem(i);
            assertTrue(cover.isCover());
            assertEquals(covers.getItem(i), cover);
        }
    }

    @SmallTest
    public void testGetRandomCover() {
        GvImageList covers = new GvImageList();
        for (int i = 0; i < 100; ++i) {
            GvImageList.GvImage image = GvDataMocker.newImage();
            if (image.isCover()) covers.add(image);
            mList.add(image);
        }

        for (int i = 0; i < 100; ++i) {
            GvImageList.GvImage cover = mList.getRandomCover();
            assertTrue(cover.isCover());
        }
    }

    @SmallTest
    public void testEquals() {
        mList.add(GvDataMocker.newImageList(NUM));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.CHILDREN, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.TAGS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.LOCATIONS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.COMMENTS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.FACTS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.IMAGES, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.URLS, NUM)));

        GvImageList list = new GvImageList();
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        list.add(mList);
        assertFalse(mList.equals(list));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvImageList();
    }
}
