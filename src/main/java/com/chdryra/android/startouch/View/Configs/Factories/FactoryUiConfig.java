/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.View.Configs.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.View.Configs.Implementation.DataConfigs;
import com.chdryra.android.startouch.View.Configs.Implementation.DataLaunchables;
import com.chdryra.android.startouch.View.Configs.Implementation.LaunchableConfigImpl;
import com.chdryra.android.startouch.View.Configs.Implementation.UiConfigImpl;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchablesList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryUiConfig {
    private static final int LOGIN = code("Login");
    private static final int SIGN_UP = code("SignUp");
    private static final int FEED = code("Feed");
    private static final int BUILD = code("Build");
    private static final int FORMATTED = code("Formatted");
    private static final int EDIT_MAP = code("EditMap");
    private static final int LOC_MAP = code("LocationMap");
    private static final int NODE_MAP = code("NodeMap");
    private static final int IMAGE_VIEW = code("ImageView");
    private static final int IMAGES_VIEW = code("ImagesView");
    private static final int PUBLISH = code("Publish");
    private static final int OPTIONS = code("Options");

    private static int code(String login) {
        return RequestCodeGenerator.getCode(FactoryUiConfig.class, login);
    }

    public UiConfig newUiConfig(LaunchablesList classes) {
        ArrayList<DataConfigs<?>> dataConfigs = new ArrayList<>();

        for (DataLaunchables<?> uiClasses : classes.getDataLaunchables()) {
            dataConfigs.add(new DataConfigs<>(uiClasses));
        }

        Map<String, LaunchableConfig> bespokeEditors = new HashMap<>();
        bespokeEditors.put(Review.TYPE_NAME, config(classes.getReviewBuild(), BUILD));
        bespokeEditors.put(DataLocation.TYPE_NAME, config(classes.getMapperEdit(), EDIT_MAP));

        Map<String, LaunchableConfig> bespokeDataViewers = new HashMap<>();
        bespokeDataViewers.put(Review.TYPE_NAME, config(classes.getReviewFormatted(), FORMATTED));
        bespokeDataViewers.put(DataLocation.TYPE_NAME, config(classes.getMapperNode(), NODE_MAP));
        bespokeDataViewers.put(DataImage.TYPE_NAME, config(classes.getImagesViewer(), IMAGES_VIEW));

        Map<String, LaunchableConfig> bespokeDatumViewers = new HashMap<>();
        bespokeDatumViewers.put(DataLocation.TYPE_NAME, config(classes.getMapperLocation(), LOC_MAP));
        bespokeDatumViewers.put(DataImage.TYPE_NAME, config(classes.getImagesViewer(), IMAGE_VIEW));

        LaunchableConfig login = config(classes.getLogin(), LOGIN);
        LaunchableConfig signUp = config(classes.getSignUp(), SIGN_UP);
        LaunchableConfig feed = config(classes.getFeed(), FEED);
        LaunchableConfig publish = config(classes.getPublish(), PUBLISH);
        LaunchableConfig options = config(classes.getOptions(), OPTIONS);

        return new UiConfigImpl(dataConfigs, bespokeEditors, bespokeDataViewers,
                bespokeDatumViewers, login, signUp, feed, publish, options);
    }

    @NonNull
    private LaunchableConfigImpl config(Class<? extends LaunchableUi> reviewBuild, int build) {
        return new LaunchableConfigImpl(reviewBuild, build);
    }
}
