/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvUrl;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import java.util.Date;

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
        assertEquals(GvImage.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newImage(null));
        ParcelableTester.testParcelable(RandomReviewId.nextGvReviewId());
        ParcelableTester.testParcelable(GvDataMocker.newImageList(2, false));
        ParcelableTester.testParcelable(GvDataMocker.newImageList(2, true));
    }

    @SmallTest
    public void testGvImage() {
        GvImage image1 = GvDataMocker.newImage(null);
        GvImage image2 = GvDataMocker.newImage(null);

        Bitmap bitmap1 = image1.getBitmap();
        Date date1 = image1.getDate();
        LatLng latLng1 = image1.getLatLng();
        String caption1 = image1.getCaption();
        boolean isCover1 = image1.isCover();

        Bitmap bitmap2 = image2.getBitmap();
        Date date2 = image2.getDate();
        LatLng latLng2 = image2.getLatLng();
        String caption2 = image2.getCaption();
        boolean isCover2 = !isCover1;

        GvImage gvImage = new GvImage(bitmap1, date1, latLng1, caption1,
                isCover1);
        GvImage gvImageEquals = new GvImage(bitmap1, date1, latLng1,
                caption1, isCover1);
        GvImage gvImageEquals2 = new GvImage(gvImage);

        GvImage gvImageNotEquals1 = new GvImage(bitmap1, date2, latLng2,
                caption2, isCover2);
        GvImage gvImageNotEquals2 = new GvImage(bitmap2, date1, latLng2,
                caption2, isCover2);
        GvImage gvImageNotEquals3 = new GvImage(bitmap2, date2, latLng1,
                caption2, isCover2);
        GvImage gvImageNotEquals4 = new GvImage(bitmap2, date2, latLng2,
                caption1, isCover2);
        GvImage gvImageNotEquals5 = new GvImage(bitmap2, date2, latLng2,
                caption2, isCover2);
        GvImage gvImageNotEquals6 = new GvImage(RandomReviewId
                .nextGvReviewId(),
                bitmap1, date1, caption1, isCover1);

        GvImage gvImageNull = new GvImage();
        GvImage gvImageEmpty = new GvImage(null, date1, latLng1, caption1,
                isCover1);
        GvImage gvImageNotEmpty = new GvImage(bitmap1, null, null, null,
                isCover1);


        assertNotNull(gvImage.getViewHolder());
        assertTrue(gvImage.isValidForDisplay());

        assertTrue(bitmap1.sameAs(gvImage.getBitmap()));
        assertEquals(date1, gvImage.getDate());
        assertEquals(caption1, gvImage.getCaption());
        assertEquals(latLng1, gvImage.getLatLng());
        assertEquals(isCover1, gvImage.isCover());
        gvImage.setIsCover(!isCover1);
        assertEquals(!isCover1, gvImage.isCover());
        gvImage.setIsCover(isCover1);

        assertTrue(gvImage.equals(gvImageEquals));
        assertTrue(gvImage.equals(gvImageEquals2));
        assertFalse(gvImage.equals(gvImageNotEquals1));
        assertFalse(gvImage.equals(gvImageNotEquals2));
        assertFalse(gvImage.equals(gvImageNotEquals3));
        assertFalse(gvImage.equals(gvImageNotEquals4));
        assertFalse(gvImage.equals(gvImageNotEquals5));
        assertFalse(gvImage.equals(gvImageNotEquals6));

        assertFalse(gvImageNull.isValidForDisplay());
        assertFalse(gvImageEmpty.isValidForDisplay());
        assertTrue(gvImageNotEmpty.isValidForDisplay());
    }

    @SmallTest
    public void testContainsBitmap() {
        GvImage image = GvDataMocker.newImage(null);
        assertFalse(mList.contains(image.getBitmap()));
        mList.add(image);
        assertTrue(mList.contains(image.getBitmap()));

        GvImage image2 = GvDataMocker.newImage(null);
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
            GvImage image = GvDataMocker.newImage(null);
            if (image.isCover()) covers.add(image);
            mList.add(image);
        }

        GvImageList coversFound = mList.getCovers();
        assertEquals(covers.size(), coversFound.size());

        for (int i = 0; i < covers.size(); ++i) {
            GvImage cover = coversFound.getItem(i);
            assertTrue(cover.isCover());
            assertEquals(covers.getItem(i), cover);
        }
    }

    @SmallTest
    public void testGetRandomCover() {
        GvImageList covers = new GvImageList();
        for (int i = 0; i < 100; ++i) {
            GvImage image = GvDataMocker.newImage(null);
            if (image.isCover()) covers.add(image);
            mList.add(image);
        }

        for (int i = 0; i < 100; ++i) {
            GvImage cover = mList.getRandomCover();
            assertTrue(cover.isCover());
        }
    }

    @SmallTest
    public void testSort() {
        mList.addList(GvDataMocker.newImageList(100, false));
        assertFalse(isSorted());
        mList.sort();
        assertTrue(isSorted());
    }

    @SmallTest
    public void testEquals() {
        mList.addList(GvDataMocker.newImageList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTag.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrl.TYPE, NUM)));

        GvImageList list = new GvImageList();
        GvImageList list2 = new GvImageList(mList);
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        assertTrue(mList.equals(list2));
        list.addList(mList);
        list2.addList(mList);
        assertFalse(mList.equals(list));
        assertFalse(mList.equals(list2));
    }

    //private methods
    private boolean isSorted() {
        assertTrue(mList.size() > 0);
        boolean isSorted = true;
        for (int i = 0; i < mList.size() - 1; ++i) {
            GvImage before = mList.getItem(i);
            GvImage after = mList.getItem(i + 1);

            if (!before.isCover() && after.isCover()) {
                isSorted = false;
                break;
            } else if (before.isCover() && !after.isCover()) {
                continue;
            }

            if (before.getDate().before(after.getDate())) {
                isSorted = false;
                break;
            }
        }

        return isSorted;
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvImageList();
    }
}
