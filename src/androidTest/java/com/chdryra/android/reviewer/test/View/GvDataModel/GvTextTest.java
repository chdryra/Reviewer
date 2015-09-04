/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 June, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvText;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTextTest extends TestCase {
    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newText(null));
        ParcelableTester.testParcelable(GvDataMocker.newText(RandomReviewId.nextGvReviewId
                ()));
    }

    @SmallTest
    public void testGvText() {
        String text1 = GvDataMocker.newText(null).get();
        String text2 = GvDataMocker.newText(null).get();

        GvText gvText = new GvText(text1);
        GvText gvTextEquals = new GvText(text1);
        GvText gvTextEquals2 = new GvText(gvText);
        GvText gvTextNotEquals = new GvText(text2);
        GvText gvTextNotEquals2 = new GvText(RandomReviewId.nextGvReviewId(), text1);
        GvText gvTextNull = new GvText();
        GvText gvTextEmpty = new GvText("");

        assertNotNull(gvText.getViewHolder());
        assertTrue(gvText.isValidForDisplay());

        assertEquals(text1, gvText.get());

        assertTrue(gvText.equals(gvTextEquals));
        assertTrue(gvText.equals(gvTextEquals2));
        assertFalse(gvText.equals(gvTextNotEquals));
        assertFalse(gvText.equals(gvTextNotEquals2));

        assertFalse(gvTextNull.isValidForDisplay());
        assertFalse(gvTextEmpty.isValidForDisplay());
    }
}
