/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 April, 2015
 */

package com.chdryra.android.startouch.test.Database;

import android.content.ContentValues;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.startouch.test.TestUtils.MdDataMocker;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowImageTest extends TestCase {
    private static final int INDEX = 314;
    private MdImage mImage;

    @SmallTest
    public void testDataConstructor() {
        testRow(new RowImage(mImage, INDEX));
    }

    @SmallTest
    public void testCursorConstructor() {
        String[] cols = new String[]{RowImage.IMAGE_ID, RowImage.REVIEW_ID, RowImage
                .BITMAP, RowImage.DATE, RowImage.CAPTION, RowImage.IS_COVER};

        MatrixCursor cursor = new MatrixCursor(cols);
        ReviewId reviewId = mImage.getReviewId().toString();
        String datumId = getDatumId();
        cursor.addRow(new Object[]{datumId, reviewId, getByteArray(), mImage.getDate().getTime(),
                mImage.getCaption(), mImage.isCover() ? 1 : 0});
        cursor.moveToFirst();
        testRow(new RowImage(cursor));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        MdDataMocker mocker = new MdDataMocker();
        mImage = mocker.newImage();
    }

    //private methods
    private String getDatumId() {
        return mImage.getReviewId().toString() + FactoryReviewerDbTableRow.SEPARATOR + "i" +
                String.valueOf
                (INDEX);
    }

    private byte[] getByteArray() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mImage.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    private void testRow(RowImage row) {
        ContentValues values = row.getContentValues();
        assertEquals(getDatumId(), values.getAsString(RowImage.IMAGE_ID));
        assertEquals(mImage.getReviewId().toString(), values.getAsString(RowImage.REVIEW_ID));
        assertTrue(Arrays.equals(getByteArray(), (byte[]) values.get(RowImage.BITMAP)));
        assertEquals(mImage.getDate().getTime(), (long) values.getAsLong(RowImage.DATE));
        assertEquals(mImage.getCaption(), values.getAsString(RowImage.CAPTION));
        assertTrue(mImage.isCover() == values.getAsBoolean(RowImage.IS_COVER));
        assertEquals(mImage, row.toMdData());
    }
}
