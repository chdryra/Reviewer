/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfigAlertable;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchablesList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryConfigUi {
    private static final String SIGN_UP_TAG = TagKeyGenerator.getTag(FactoryConfigUi.class, "SignUpScreen");
    private static final int SIGN_UP = RequestCodeGenerator.getCode(SIGN_UP_TAG);
    private static final String FEED_SCREEN__TAG = TagKeyGenerator.getTag(FactoryConfigUi.class, "FeedScreen");
    private static final int FEED_SCREEN = RequestCodeGenerator.getCode(FEED_SCREEN__TAG);
    private static final String REVIEW_BUILD_TAG = TagKeyGenerator.getTag(FactoryConfigUi.class, "ReviewBuilderScreen");
    private static final int REVIEW_BUILD = RequestCodeGenerator.getCode(REVIEW_BUILD_TAG);
    private static final String EDIT_ON_MAP_TAG = TagKeyGenerator.getTag(FactoryConfigUi.class, "EditOnMap");
    private static final int EDIT_ON_MAP = RequestCodeGenerator.getCode(EDIT_ON_MAP_TAG);
    private static final String SHARE_REVIEW_TAG = TagKeyGenerator.getTag(FactoryConfigUi.class, "ShareReview");
    private static final int SHARE_REVIEW = RequestCodeGenerator.getCode(SHARE_REVIEW_TAG);
    private static final String SHARE_EDIT_REVIEW_TAG = TagKeyGenerator.getTag(FactoryConfigUi.class, "ShareEditReview");
    private static final int SHARE_EDIT_REVIEW = RequestCodeGenerator.getCode(SHARE_EDIT_REVIEW_TAG);

    public ConfigUi newUiConfig(LaunchablesList launchables) {
        ArrayList<LaunchableConfigsHolder<?>> dataConfigs = new ArrayList<>();

        for (AddEditViewClasses<?> uiClasses : launchables.getDataLaunchableUis()) {
            dataConfigs.add(new LaunchableConfigsHolder<>(uiClasses));
        }

        LaunchableConfig signUp = getSignUpConfig(launchables);
        LaunchableConfig feed = getFeedScreenConfig(launchables);
        LaunchableConfig builder = getReviewBuilderConfig(launchables);
        LaunchableConfig mapper = getEditOnMapConfig(launchables);
        LaunchableConfig sharer = getShareReviewConfig(launchables);
        LaunchableConfigAlertable shareEditer = getShareEditReviewConfig(launchables);
        return new ConfigUiImpl(dataConfigs, signUp, feed, builder, mapper, sharer, shareEditer);
    }

    private LaunchableConfig getSignUpConfig(LaunchablesList classes) {
        return new LaunchableConfigImpl(classes.getSignUpUi(), SIGN_UP, SIGN_UP_TAG);
    }

    private LaunchableConfig getFeedScreenConfig(LaunchablesList classes) {
        return new LaunchableConfigImpl(classes.getFeedUi(), FEED_SCREEN, FEED_SCREEN__TAG);
    }

    private LaunchableConfig getReviewBuilderConfig(LaunchablesList classes) {
        return new LaunchableConfigImpl(classes.getReviewBuilderUi(), REVIEW_BUILD, REVIEW_BUILD_TAG);
    }

    private LaunchableConfig getEditOnMapConfig(LaunchablesList classes) {
        return new LaunchableConfigImpl(classes.getMapEditorUi(), EDIT_ON_MAP, EDIT_ON_MAP_TAG);
    }

    private LaunchableConfig getShareReviewConfig(LaunchablesList classes) {
        return new LaunchableConfigImpl(classes.getShareReviewUi(), SHARE_REVIEW, SHARE_REVIEW_TAG);
    }

    private LaunchableConfigAlertable getShareEditReviewConfig(LaunchablesList classes) {
        return new LaunchableConfigAlertableImpl(classes.getShareEditReviewUi(), SHARE_EDIT_REVIEW,
                SHARE_EDIT_REVIEW_TAG);
    }
}
