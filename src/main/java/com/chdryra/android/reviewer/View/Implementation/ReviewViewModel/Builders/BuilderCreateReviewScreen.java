package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Builders;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories
        .FactoryReviewEditor;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.BuildScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation
        .BuildScreenModifier;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GridItemClickObserved;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation
        .MenuBuildScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation
        .RatingBarBuildScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation
        .SubjectEditBuildScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Interfaces.ReviewEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderCreateReviewScreen {
    public BuildScreen newScreen(Context context,
                                 ConfigDataUi uiConfig,
                                 ReviewBuilderAdapter<?> builder,
                                 FactoryLaunchableUi launchablefactory,
                                 FactoryReviewEditor editorFactory) {
        String screenTitle = context.getResources().getString(R.string.screen_title_build_review);
        String buttonTitle = context.getResources().getString(R.string.button_add_review_data);
        ReviewViewActions<?> actions = new ReviewViewActions<>(new SubjectEditBuildScreen<>(),
                new RatingBarBuildScreen<>(), new BannerButtonActionNone<>(buttonTitle),
                new GridItemClickObserved<>(), new MenuBuildScreen<>(screenTitle));

        //Parameters
        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

        ReviewEditor<?> editor = editorFactory.newEditor(builder, params, actions, new BuildScreenModifier());

        return new BuildScreen(editor, uiConfig, launchablefactory);
    }
}
