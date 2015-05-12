/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.GvDataListWrapper;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataListWrapperTest extends TestCase {
    @SmallTest
    public void testGetGridData() {
        GvDataList data = GvDataMocker.getData(GvCommentList.TYPE, 10);
        GvDataListWrapper wrapper = new GvDataListWrapper(data);

        assertEquals(data, wrapper.getGridData());
    }
}
