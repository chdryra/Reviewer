/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.os.Bundle;

import com.chdryra.android.reviewer.View.GvData;

import junit.framework.Assert;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataParcelableTester {
    public static void testParcelable(GvData datum) {
        Bundle args = new Bundle();
        String key = "key";
        args.putParcelable(key, datum);
        GvData ret = args.getParcelable(key);
        Assert.assertEquals(datum, ret);
    }
}
