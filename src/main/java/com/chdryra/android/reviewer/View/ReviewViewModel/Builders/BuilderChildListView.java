package com.chdryra.android.reviewer.View.ReviewViewModel.Builders;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvImageConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvReviewConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.ViewerChildList;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces.GridDataViewer;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.SubjectAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewDefault;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderChildListView {
    private GvReviewConverter<GvReviewOverviewList.GvReviewOverview, GvReviewOverviewList> mReviewConverter;
    private GvImageConverter mImageConverter;
    private FactoryLaunchable mLaunchableFactory;

    public BuilderChildListView(GvReviewConverter<GvReviewOverviewList.GvReviewOverview,
            GvReviewOverviewList> reviewConverter, GvImageConverter imageConverter,
                                FactoryLaunchable launchableFactory) {
        mReviewConverter = reviewConverter;
        mImageConverter = imageConverter;
        mLaunchableFactory = launchableFactory;
    }

    public ReviewView buildView(ReviewNode node,
                          FactoryReviewViewAdapter adapterFactory,
                          GridItemAction<GvReviewOverviewList.GvReviewOverview> giAction,
                          MenuAction<GvReviewOverviewList.GvReviewOverview> menuAction) {

        GridDataViewer<GvReviewOverviewList.GvReviewOverview> viewer;
        ReviewViewAdapter<GvReviewOverviewList.GvReviewOverview> adapter;
        viewer = new ViewerChildList(node, mReviewConverter, adapterFactory);
        adapter = new AdapterReviewNode<>(node, mImageConverter, viewer);

        ReviewViewActions<GvReviewOverviewList.GvReviewOverview> actions;
        actions = getActions(mLaunchableFactory, giAction, menuAction);

        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setSubjectVisible(true).setRatingVisible(true).setBannerButtonVisible(true)
                .setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);

        ReviewViewPerspective<GvReviewOverviewList.GvReviewOverview> perspective;
        perspective = new ReviewViewPerspective<>(adapter, params, actions);
        return new ReviewViewDefault<>(perspective);
    }


    private ReviewViewActions<GvReviewOverviewList.GvReviewOverview>
    getActions(FactoryLaunchable launchableFactory,
                      GridItemAction<GvReviewOverviewList.GvReviewOverview> giAction,
                      MenuAction<GvReviewOverviewList.GvReviewOverview> menuAction) {
        SubjectAction<GvReviewOverviewList.GvReviewOverview> subject = new SubjectActionNone<>();
        RatingBarAction<GvReviewOverviewList.GvReviewOverview> rb = new RatingBarExpandGrid<>(launchableFactory);
        BannerButtonAction<GvReviewOverviewList.GvReviewOverview> bb = new BannerButtonActionNone<>();
        return new ReviewViewActions<>(subject, rb, bb, giAction, menuAction);
    }
}
