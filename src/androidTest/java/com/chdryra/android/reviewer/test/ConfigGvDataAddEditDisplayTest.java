/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.LaunchableIU;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 03/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ConfigGvDataAddEditDisplayTest extends TestCase {
    private static final GvDataList.GvType[] NULLADDS  = {GvDataList.GvType.IMAGES,
            GvDataList.GvType.REVIEW, GvDataList.GvType.SOCIAL};
    private static final GvDataList.GvType[] NULLEDITS = {GvDataList.GvType.REVIEW,
            GvDataList.GvType.SOCIAL};

    @SmallTest
    public void testGetAddClass() {
        for (GvDataList.GvType dataType : GvDataList.GvType.values()) {
            Class<? extends LaunchableIU> addClass = ConfigGvDataAddEditDisplay.getAddClass
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
            Class<? extends LaunchableIU> editClass = ConfigGvDataAddEditDisplay.getEditClass
                    (dataType);
            if (Arrays.asList(NULLEDITS).contains(dataType)) {
                assertNull(editClass);
            } else {
                assertNotNull(editClass);
            }
        }
    }

    @SmallTest
    public void testGetDisplayClass() {
        for (GvDataList.GvType dataType : GvDataList.GvType.values()) {
            Class<? extends Activity> addClass = ConfigGvDataAddEditDisplay.getDisplayClass
                    (dataType);
            assertNotNull(addClass);
        }
    }
}
