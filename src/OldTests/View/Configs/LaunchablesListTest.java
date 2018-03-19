/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 December, 2014
 */

package com.chdryra.android.startouch.test.View.Configs;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchablesList;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 03/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchablesListTest extends TestCase {
    private static final ArrayList<GvDataType> TYPES = GvDataMocker.TYPES;
    private static final GvDataType[] NULLADDS = {GvImage.TYPE};

    @SmallTest
    public void testGetAddClass() {
        for (GvDataType dataType : TYPES) {
            Class<? extends LaunchableUi> addClass = LaunchablesList.getAddClass
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
        for (GvDataType dataType : TYPES) {
            Class<? extends LaunchableUi> editClass = LaunchablesList.getEditClass
                    (dataType);
            assertNotNull(editClass);
        }
    }
}
