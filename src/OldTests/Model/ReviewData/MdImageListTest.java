/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdImageListTest extends TestCase {
    private static final MdReviewId ID = RandomReviewId.nextId();

    @SmallTest
    public void testGetCovers() {
        MdDataMocker mocker = new MdDataMocker(ID);

        MdImageList covers = new MdImageList(ID);
        MdImageList data = new MdImageList(ID);
        while (!covers.hasData()) {
            data = mocker.newImageList(10);
            for (MdImage image : data) {
                if (image.isCover()) covers.add(image);
            }
        }

        MdImageList dataCovers = data.getCovers();
        assertEquals(covers, dataCovers);
    }

    @SmallTest
    public void testMdImageHasData() {
        Bitmap bitmap = BitmapMocker.nextBitmap();
        Date date = RandomDate.nextDate();
        String caption = RandomString.nextSentence();

        MdImage image = new MdImage(ID, null, date, caption, false);
        assertFalse(image.hasData());
        image = new MdImage(ID, bitmap, date, caption, false);
        assertTrue(image.hasData());
    }

    @SmallTest
    public void testMdImageGetters() {
        Bitmap bitmap = BitmapMocker.nextBitmap();
        Date date = RandomDate.nextDate();
        String caption = RandomString.nextSentence();

        MdImage image = new MdImage(ID, bitmap, date, caption, false);

        assertTrue(bitmap.sameAs(image.getBitmap()));
        assertEquals(date, image.getDate());
        assertEquals(caption, image.getCaption());
        assertEquals(ID, image.getReviewId());
    }

    @SmallTest
    public void testMdCommentIsCover() {
        Bitmap bitmap = BitmapMocker.nextBitmap();
        Date date = RandomDate.nextDate();
        String caption = RandomString.nextSentence();

        MdImage image = new MdImage(ID, bitmap, date, caption, false);
        assertFalse(image.isCover());
        image = new MdImage(ID, bitmap, date, caption, true);
        assertTrue(image.isCover());
    }

    @SmallTest
    public void testMdImageEqualsHash() {
        Bitmap bitmap1 = BitmapMocker.nextBitmap();
        Date date1 = RandomDate.nextDate();
        String caption1 = RandomString.nextSentence();
        Bitmap bitmap2 = BitmapMocker.nextBitmap();
        Date date2 = RandomDate.nextDate();
        String caption2 = RandomString.nextSentence();
        MdReviewId id2 = RandomReviewId.nextId();

        MdImage image1 = new MdImage(ID, bitmap1, date1, caption1, false);

        MdImage image2;
        image2 = new MdImage(ID, bitmap2, date1, caption1, false);
        MdDataUtils.testEqualsHash(image1, image2, false);
        image2 = new MdImage(ID, bitmap1, date2, caption1, false);
        MdDataUtils.testEqualsHash(image1, image2, false);
        image2 = new MdImage(ID, bitmap1, date1, caption2, false);
        MdDataUtils.testEqualsHash(image1, image2, false);
        image2 = new MdImage(ID, bitmap1, date1, caption1, true);
        MdDataUtils.testEqualsHash(image1, image2, false);
        image2 = new MdImage(id2, bitmap1, date1, caption1, false);
        MdDataUtils.testEqualsHash(image1, image2, false);
        image2 = new MdImage(ID, bitmap1, date1, caption1, false);
        MdDataUtils.testEqualsHash(image1, image2, true);
    }
}
