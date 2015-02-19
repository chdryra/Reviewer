/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.LaunchableUi;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 03/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ConfigGvDataAddEditTest extends TestCase {
    private static final GvDataList.GvType[] NULLADDS  = {GvDataList.GvType.IMAGES,
            GvDataList.GvType.FEED, GvDataList.GvType.SHARE};
    private static final GvDataList.GvType[] NULLEDITS = {GvDataList.GvType.FEED,
            GvDataList.GvType.SHARE};

    @SmallTest
    public void testGetAddClass() {
        for (GvDataList.GvType dataType : GvDataList.GvType.values()) {
            Class<? extends LaunchableUi> addClass = ConfigGvDataAddEdit.getAddClass
                    (dataType);
            if (Arrays.asList(NULLADDS).contains(dataType)) {
                assertNull(addClass);
            } else {
                assertNotNull(addClass);
            }
        }
    }

    @SmallTest
    public void testGetEditClass() {
        for (GvDataList.GvType dataType : GvDataList.GvType.values()) {
            Class<? extends LaunchableUi> editClass = ConfigGvDataAddEdit.getEditClass
                    (dataType);
            if (Arrays.asList(NULLEDITS).contains(dataType)) {
                assertNull(editClass);
            } else {
                assertNotNull(editClass);
            }
        }
    }
}
