package com.chdryra.android.reviewer.View.Configs;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchablesList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryConfigDataUi {
    private static final String REVIEW_BUILD_TAG = "ReviewBuilderScreen";
    private static final int REVIEW_BUILD = RequestCodeGenerator.getCode(REVIEW_BUILD_TAG);
    private static final String EDIT_ON_MAP_TAG = "EditOnMap";
    private static final int EDIT_ON_MAP = RequestCodeGenerator.getCode(EDIT_ON_MAP_TAG);
    private static final String SHARE_REVIEW_TAG = "ShareReview";
    private static final int SHARE_REVIEW = RequestCodeGenerator.getCode(SHARE_REVIEW_TAG);

    public ConfigDataUi getDefaultConfig(LaunchablesList launchables) {
        return getConfigDataUi(launchables);
    }

    @NonNull
    private ConfigDataUi getConfigDataUi(LaunchablesList classes) {
        ArrayList<LaunchableConfigsHolder<?>> dataConfigs = new ArrayList<>();

        for (AddEditViewClasses<?> uiClasses : classes.getDataLaunchableUis()) {
            dataConfigs.add(new LaunchableConfigsHolder<>(uiClasses));
        }

        LaunchableConfig builder = getReviewBuilderConfig(classes);
        LaunchableConfig mapper = getEditOnMapConfig(classes);
        LaunchableConfig sharer = getShareReviewConfig(classes);
        return new ConfigDataUiImpl(dataConfigs, builder, mapper, sharer);
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
}
