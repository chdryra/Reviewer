/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.startouch.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin.DataAggregatorsDefault.FactoryDataAggregatorDefault.Implementation.ComparitorImageBitmap;
import com.chdryra.android.startouch.Algorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorImageBitmapTest extends TestCase {
    @SmallTest
    public void testCompare() {
        GvImage lhsImage = GvDataMocker.newImage(null);
        GvImage rhsImage = GvDataMocker.newImage(null);

        ComparitorImageBitmap comparitor = new ComparitorImageBitmap();
        DifferenceBoolean same = new DifferenceBoolean(false);
        DifferenceBoolean notSame = new DifferenceBoolean(true);

        GvImage lhs = new GvImage(lhsImage);
        GvImage rhs = new GvImage(lhsImage);

        DifferenceBoolean difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));

        rhs = new GvImage(rhsImage);
        difference = comparitor.compare(lhs, rhs);
        assertFalse(difference.lessThanOrEqualTo(same));
        assertTrue(difference.lessThanOrEqualTo(notSame));
        difference = comparitor.compare(rhs, lhs);
        assertFalse(difference.lessThanOrEqualTo(same));
        assertTrue(difference.lessThanOrEqualTo(notSame));

        rhs = new GvImage(lhs.getBitmap(), rhs.getDate(), rhs.getLatLng(), rhs
                .getCaption(), rhs.isCover());
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));
    }
}
