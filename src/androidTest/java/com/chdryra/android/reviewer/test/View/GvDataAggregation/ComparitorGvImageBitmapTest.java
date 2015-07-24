/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvImageBitmap;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferenceBoolean;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvImageBitmapTest extends TestCase {
    @SmallTest
    public void testCompare() {
        GvImageList.GvImage lhsImage = GvDataMocker.newImage(null);
        GvImageList.GvImage rhsImage = GvDataMocker.newImage(null);

        ComparitorGvImageBitmap comparitor = new ComparitorGvImageBitmap();
        DifferenceBoolean same = new DifferenceBoolean(false);
        DifferenceBoolean notSame = new DifferenceBoolean(true);

        GvImageList.GvImage lhs = new GvImageList.GvImage(lhsImage);
        GvImageList.GvImage rhs = new GvImageList.GvImage(lhsImage);

        DifferenceBoolean difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));

        rhs = new GvImageList.GvImage(rhsImage);
        difference = comparitor.compare(lhs, rhs);
        assertFalse(difference.lessThanOrEqualTo(same));
        assertTrue(difference.lessThanOrEqualTo(notSame));
        difference = comparitor.compare(rhs, lhs);
        assertFalse(difference.lessThanOrEqualTo(same));
        assertTrue(difference.lessThanOrEqualTo(notSame));

        rhs = new GvImageList.GvImage(lhs.getBitmap(), rhs.getDate(), rhs.getLatLng(), rhs
                .getCaption(), rhs.isCover());
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));
    }
}