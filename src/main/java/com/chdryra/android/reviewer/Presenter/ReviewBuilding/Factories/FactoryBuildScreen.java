package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenModifier;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemClickObserved;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.RatingBarBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectEditBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Configs.ConfigDataUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBuildScreen {
    private static final int SCREEN_TITLE = R.string.screen_title_build_review;

    public <GC extends GvDataList<?>> BuildScreen newScreen(Context context,
                                 ConfigDataUi uiConfig,
                                 ReviewBuilderAdapter<GC> builder,
                                 LaunchableUiLauncher launchablefactory,
                                 FactoryReviewEditor editorFactory) {
        ReviewEditor<GC> editor = editorFactory.newEditor(builder,
                getReviewViewParams(),
                getReviewViewActions(context, builder),
                getModifier(launchablefactory, uiConfig.getShareReviewConfig()));

        return new BuildScreen(editor, uiConfig, launchablefactory);
    }

    @NonNull
    private ReviewViewParams getReviewViewParams() {
        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);
        return params;
    }

    @NonNull
    private BuildScreenModifier getModifier(LaunchableUiLauncher launchablefactory,
                                            LaunchableConfig shareScreenConfig) {
        return new BuildScreenModifier(launchablefactory, shareScreenConfig);
    }

    @NonNull
    private <GC extends GvDataList<?>> ReviewViewActions<GC> getReviewViewActions(Context context,
                                                                                  ReviewBuilderAdapter<GC> builder) {
        String screenTitle = context.getResources().getString(SCREEN_TITLE);
        String buttonTitle = context.getResources().getString(R.string.button_add_review_data);
        return new ReviewViewActions<>(new SubjectEditBuildScreen<GC>(),
                new RatingBarBuildScreen<GC>(), new BannerButtonActionNone<GC>(buttonTitle),
                new GridItemClickObserved<GC>(), new MenuBuildScreen<GC>(screenTitle));
    }
}
