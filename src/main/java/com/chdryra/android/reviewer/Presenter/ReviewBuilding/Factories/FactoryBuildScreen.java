/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenModifier;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemClickObserved;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.RatingBarBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectEditBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBuildScreen {
    private static final int SCREEN_TITLE = R.string.activity_title_build_review;
    public static final int BUTTON_TITLE = R.string.button_add_review_data;

    public <GC extends GvDataList<?>> BuildScreen newScreen(Context context,
                                 ConfigUi uiConfig,
                                 ReviewBuilderAdapter<GC> builder,
                                 LaunchableUiLauncher launcher,
                                 FactoryReviewEditor editorFactory) {
        ReviewEditor<GC> editor = newEditor(context, builder, launcher,
                uiConfig.getShareReviewConfig().getLaunchable(), editorFactory);

        return new BuildScreen<>(editor, uiConfig, launcher);
    }

    private <GC extends GvDataList<?>> ReviewEditor<GC> newEditor(Context context,
                                                                  ReviewBuilderAdapter<GC> builder,
                                                                  LaunchableUiLauncher launcher,
                                                                  LaunchableUi shareScreenUi,
                                                                  FactoryReviewEditor factory) {
        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

        String screenTitle = context.getResources().getString(SCREEN_TITLE);
        String buttonTitle = context.getResources().getString(BUTTON_TITLE);
        ReviewViewActions<GC> actions = new ReviewViewActions<>(new SubjectEditBuildScreen<GC>(),
                new RatingBarBuildScreen<GC>(), new BannerButtonActionNone<GC>(buttonTitle),
                new GridItemClickObserved<GC>(), new MenuBuildScreen<GC>(screenTitle));

        return factory.newEditor(builder, params, actions,
                new BuildScreenModifier(launcher, shareScreenUi));
    }
}
