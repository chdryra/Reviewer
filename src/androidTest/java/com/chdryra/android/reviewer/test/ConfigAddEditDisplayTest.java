/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;

import com.chdryra.android.reviewer.ConfigAddEditDisplay;
import com.chdryra.android.reviewer.GVReviewDataList;
import com.chdryra.android.reviewer.LaunchableUI;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 03/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ConfigAddEditDisplayTest extends TestCase {
    private static final GVReviewDataList.GVType[] NULLADDS  = {GVReviewDataList.GVType.IMAGES,
            GVReviewDataList.GVType.REVIEW, GVReviewDataList.GVType.SOCIAL};
    private static final GVReviewDataList.GVType[] NULLEDITS = {GVReviewDataList.GVType.REVIEW,
            GVReviewDataList.GVType.SOCIAL};

    public void testGetAddClass() {
        for (GVReviewDataList.GVType dataType : GVReviewDataList.GVType.values()) {
            Class<? extends LaunchableUI> addClass = ConfigAddEditDisplay.getAddClass(dataType);
            if (Arrays.asList(NULLADDS).contains(dataType)) {
                assertNull(addClass);
            } else {
                assertNotNull(addClass);
            }
        }
    }

    public void testGetEditClass() {
        for (GVReviewDataList.GVType dataType : GVReviewDataList.GVType.values()) {
            Class<? extends LaunchableUI> editClass = ConfigAddEditDisplay.getEditClass(dataType);
            if (Arrays.asList(NULLEDITS).contains(dataType)) {
                assertNull(editClass);
            } else {
                assertNotNull(editClass);
            }
        }
    }

    public void testGetDisplayClass() {
        for (GVReviewDataList.GVType dataType : GVReviewDataList.GVType.values()) {
            Class<? extends Activity> addClass = ConfigAddEditDisplay.getDisplayClass(dataType);
            assertNotNull(addClass);
        }
    }
}
