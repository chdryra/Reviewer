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

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ClassesAddEditView;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfigsHolder;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
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
public class LaunchableConfigsHolderGvDataUiTest extends AndroidTestCase {
    private static final ArrayList<GvDataType> TYPES = GvDataMocker.TYPES;
    private static final GvDataType[] NULLADDS = {GvImageList.GvImage.TYPE};

    @SmallTest
    public void testGetConfigAndConfigClass() {
        for (GvDataType dataType : TYPES) {
            LaunchableConfigsHolder launchableConfigsHolder = ApplicationInstance.ConfigGvDataUi.getConfig
                    (dataType);
            assertNotNull(launchableConfigsHolder);
            assertNotNull(launchableConfigsHolder.getAdderConfig());
            assertNotNull(launchableConfigsHolder.getEditorConfig());
        }
    }

    @SmallTest
    public void testReviewDataUIConfigs() {
        ArrayList<Integer> requestCodes = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        for (GvDataType dataType : TYPES) {
            LaunchableConfigsHolder launchableConfigsHolder = ApplicationInstance.ConfigGvDataUi.getConfig(dataType);
            assertNotNull(launchableConfigsHolder);

            //Add
            LaunchableConfig uiConfig = launchableConfigsHolder.getAdderConfig();
            assertEquals(dataType, uiConfig.getGvDataType());

            String tag = uiConfig.getTag(); //tags make sense
            assertNotNull(tag);
            assertTrue(tag.length() > 0);
            tags.add(uiConfig.getTag()); //for testing uniqueness later

            if (Arrays.asList(NULLADDS).contains(dataType)) {
                assertNull(uiConfig.getLaunchable());
            } else {
                LaunchableUi ui = uiConfig.getLaunchable();
                assertNotNull(ui);
                assertEquals(ClassesAddEditView.getAddClass(dataType).getName(),
                        ui.getClass().getName());
            }

            requestCodes.add(uiConfig.getRequestCode());

            //Edit
            uiConfig = launchableConfigsHolder.getEditorConfig();
            assertEquals(dataType, uiConfig.getGvDataType());

            tag = uiConfig.getTag();
            assertNotNull(tag);
            assertTrue(tag.length() > 0);
            tags.add(uiConfig.getTag());

            LaunchableUi ui = uiConfig.getLaunchable();
            assertNotNull(ui);
            assertEquals(ClassesAddEditView.getEditClass(dataType).getName(),
                    ui.getClass().getName());

            requestCodes.add(uiConfig.getRequestCode());
        }

        Set<Integer> uniqueRequestCodes = new LinkedHashSet<>();
        uniqueRequestCodes.addAll(requestCodes);
        assertEquals(2, uniqueRequestCodes.size()); //one for add, one for edit

        Set<String> uniqueTags = new LinkedHashSet<>();
        uniqueTags.addAll(tags);
        assertEquals(tags.size(), uniqueTags.size()); //should be unique
    }

    @SmallTest
    public void testGetReviewDataUI() {
        for (GvDataType dataType : TYPES) {
            LaunchableConfigsHolder launchableConfigsHolder = ApplicationInstance.ConfigGvDataUi.getConfig(dataType);
            assertNotNull(launchableConfigsHolder);

            //Add
            if (!Arrays.asList(NULLADDS).contains(dataType)) {
                LaunchableUi fromConfig = launchableConfigsHolder.getAdderConfig().getLaunchable();
                assertNotNull(fromConfig);
            }

            //Edit
            LaunchableUi fromConfig = launchableConfigsHolder.getEditorConfig().getLaunchable();
            assertNotNull(fromConfig);
        }
    }
}
