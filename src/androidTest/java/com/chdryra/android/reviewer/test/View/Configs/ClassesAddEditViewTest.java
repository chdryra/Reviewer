/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 December, 2014
 */

package com.chdryra.android.reviewer.test.View.Configs;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.Configs.Interfaces.ClassesAddEditView;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 03/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ClassesAddEditViewTest extends TestCase {
    private static final ArrayList<GvDataType> TYPES = GvDataMocker.TYPES;
    private static final GvDataType[] NULLADDS = {GvImageList.GvImage.TYPE};

    @SmallTest
    public void testGetAddClass() {
        for (GvDataType dataType : TYPES) {
            Class<? extends LaunchableUi> addClass = ClassesAddEditView.getAddClass
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
            Class<? extends LaunchableUi> editClass = ClassesAddEditView.getEditClass
                    (dataType);
            assertNotNull(editClass);
        }
    }
}
