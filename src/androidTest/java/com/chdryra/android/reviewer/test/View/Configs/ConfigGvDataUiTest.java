/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.View.Configs;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Using ActivityFeed as my testingActivity to avoid having to declare a separate test activity
 * in the manifest.
 */
public class ConfigGvDataUiTest extends AndroidTestCase {
    private static final GvDataType[] DATATYPES = GvDataMocker.DATATYPES;
    private static final GvDataType[] NULLADDS  = {GvImageList.TYPE};

    @SmallTest
    public void testGetConfigAndConfigClass() {
        for (GvDataType dataType : DATATYPES) {
            ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(dataType);
            assertNotNull(config);
            assertNotNull(config.getAdderConfig());
            assertNotNull(config.getEditorConfig());
        }
    }

    @SmallTest
    public void testReviewDataUIConfigs() {
        ArrayList<Integer> requestCodes = new ArrayList<Integer>();
        ArrayList<String> tags = new ArrayList<String>();
        for (GvDataType dataType : DATATYPES) {
            ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(dataType);
            assertNotNull(config);

            //Add
            ConfigGvDataUi.LaunchableConfig uiConfig = config.getAdderConfig();
            assertEquals(dataType.getElementType(), uiConfig.getGVType().getElementType());

            String tag = uiConfig.getTag(); //tags make sense
            assertNotNull(tag);
            assertTrue(tag.length() > 0);
            tags.add(uiConfig.getTag()); //for testing uniqueness later

            if (Arrays.asList(NULLADDS).contains(dataType)) {
                assertNull(uiConfig.getLaunchable());
            } else {
                LaunchableUi ui = uiConfig.getLaunchable();
                assertNotNull(ui);
                assertEquals(ConfigGvDataAddEditView.getAddClass(dataType).getName(),
                        ui.getClass().getName());
            }

            requestCodes.add(uiConfig.getRequestCode());

            //Edit
            uiConfig = config.getEditorConfig();
            assertEquals(dataType.getElementType(), uiConfig.getGVType().getElementType());

            tag = uiConfig.getTag();
            assertNotNull(tag);
            assertTrue(tag.length() > 0);
            tags.add(uiConfig.getTag());

            LaunchableUi ui = uiConfig.getLaunchable();
            assertNotNull(ui);
            assertEquals(ConfigGvDataAddEditView.getEditClass(dataType).getName(),
                    ui.getClass().getName());

            requestCodes.add(uiConfig.getRequestCode());
        }

        Set<Integer> uniqueRequestCodes = new LinkedHashSet<Integer>();
        uniqueRequestCodes.addAll(requestCodes);
        assertEquals(2, uniqueRequestCodes.size()); //one for add, one for edit

        Set<String> uniqueTags = new LinkedHashSet<String>();
        uniqueTags.addAll(tags);
        assertEquals(tags.size(), uniqueTags.size()); //should be unique
    }

    @SmallTest
    public void testGetReviewDataUI() {
        for (GvDataType dataType : DATATYPES) {
            ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(dataType);
            assertNotNull(config);

            //Add
            if (!Arrays.asList(NULLADDS).contains(dataType)) {
                LaunchableUi fromConfig = config.getAdderConfig().getLaunchable();
                assertNotNull(fromConfig);
            }

            //Edit
            LaunchableUi fromConfig = config.getEditorConfig().getLaunchable();
            assertNotNull(fromConfig);
        }
    }
}
