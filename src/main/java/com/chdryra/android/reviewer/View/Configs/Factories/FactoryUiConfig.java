/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs.Factories;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.Implementation.DataConfigs;
import com.chdryra.android.reviewer.View.Configs.Implementation.DataLaunchables;
import com.chdryra.android.reviewer.View.Configs.Implementation.LaunchableConfigImpl;
import com.chdryra.android.reviewer.View.Configs.Implementation.UiConfigImpl;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchablesList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryUiConfig {
    private static final int LOGIN = RequestCodeGenerator.getCode(FactoryUiConfig.class, "Login");
    private static final int SIGN_UP = RequestCodeGenerator.getCode(FactoryUiConfig.class, "SignUp");
    private static final int FEED = RequestCodeGenerator.getCode(FactoryUiConfig.class, "Feed");
    private static final int BUILD = RequestCodeGenerator.getCode(FactoryUiConfig.class, "Build");
    private static final int FORMATTED = RequestCodeGenerator.getCode(FactoryUiConfig.class, "Formatted");
    private static final int EDIT_MAP = RequestCodeGenerator.getCode(FactoryUiConfig.class, "EditMap");
    private static final int NODE_MAP = RequestCodeGenerator.getCode(FactoryUiConfig.class, "NodeMap");
    private static final int PUBLISH = RequestCodeGenerator.getCode(FactoryUiConfig.class, "Publish");
    private static final int OPTIONS = RequestCodeGenerator.getCode(FactoryUiConfig.class, "Options");

    public UiConfig newUiConfig(LaunchablesList classes) {
        ArrayList<DataConfigs<?>> dataConfigs = new ArrayList<>();

        for (DataLaunchables<?> uiClasses : classes.getDataLaunchables()) {
            dataConfigs.add(new DataConfigs<>(uiClasses));
        }

        LaunchableConfig login = new LaunchableConfigImpl(classes.getLogin(), LOGIN);
        LaunchableConfig signUp = new LaunchableConfigImpl(classes.getSignUp(), SIGN_UP);
        LaunchableConfig feed = new LaunchableConfigImpl(classes.getFeed(), FEED);
        LaunchableConfig build = new LaunchableConfigImpl(classes.getReviewBuild(), BUILD);
        LaunchableConfig formatted = new LaunchableConfigImpl(classes.getReviewFormatted(), FORMATTED);
        LaunchableConfig editMap = new LaunchableConfigImpl(classes.getMapperEdit(), EDIT_MAP);
        LaunchableConfig nodeMap = new LaunchableConfigImpl(classes.getMapperNode(), NODE_MAP);
        LaunchableConfig publish = new LaunchableConfigImpl(classes.getPublish(), PUBLISH);
        LaunchableConfig options = new LaunchableConfigImpl(classes.getReviewOptions(), OPTIONS);

        return new UiConfigImpl(dataConfigs, login, signUp, feed, build, formatted, editMap,
                nodeMap, publish, options);
    }
}
