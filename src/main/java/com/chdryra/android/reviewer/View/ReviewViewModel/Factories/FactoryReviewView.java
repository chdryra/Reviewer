package com.chdryra.android.reviewer.View.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.SubjectAction;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private FactoryLauncherUi mLauncherFactory;
    private FactoryLaunchableUi mLaunchableFactory;
    private FactoryReviewViewAdapter mAdapterFactory;

    public ReviewView newReviewsListScreen(ReviewNode node, BuilderChildListView childListBuilder) {
        SubjectAction<GvReviewOverviewList.GvReviewOverview> subject = new SubjectActionNone<>();
        RatingBarAction<GvReviewOverviewList.GvReviewOverview> rb = new RatingBarExpandGrid<>(mLaunchableFactory);
        BannerButtonAction<GvReviewOverviewList.GvReviewOverview> bb = new BannerButtonActionNone<>();
        GridItemAction<GvReviewOverviewList.GvReviewOverview> giAction
                = new GridItemLauncher<>(mLauncherFactory, mLaunchableFactory);
        MenuAction<GvReviewOverviewList.GvReviewOverview> menuAction = new MenuActionNone<>();
        ReviewViewActions<GvReviewOverviewList.GvReviewOverview> actions
                = new ReviewViewActions<>(subject, rb, bb, giAction, menuAction);

        return childListBuilder.buildView(node, mAdapterFactory, actions);
    }
}
