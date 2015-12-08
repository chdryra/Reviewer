package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewLaunchable {
    private FactoryChildListView mChildListScreenBuilder;
    private FactoryReviewViewParams mParamsFactory;
    private ConfigDataUi mConfig;
    private LaunchableUiLauncher mLauncher;

    public FactoryReviewViewLaunchable(ConfigDataUi config, LaunchableUiLauncher launcher,
                                       FactoryReviewViewParams paramsFactory) {
        mConfig = config;
        mParamsFactory = paramsFactory;
        mLauncher = launcher;
        mChildListScreenBuilder = new FactoryChildListView();
    }

    public FactoryReviewViewParams getParamsFactory() {
        return mParamsFactory;
    }

    public LaunchableUi newReviewsListScreen(ReviewNode node, FactoryReviewViewAdapter adapterFactory) {
        return newReviewsListScreen(node, adapterFactory, getListScreenActions());
    }

    public ReviewView<GvReviewOverview> newReviewsListScreen(ReviewNode node,
                                                             FactoryReviewViewAdapter adapterFactory,
                                             ReviewViewActions<GvReviewOverview> actions) {
        return mChildListScreenBuilder.buildView(node, adapterFactory, actions);
    }

    public <T extends GvData> LaunchableUi newViewScreen(ReviewViewAdapter<T> adapter) {
        //TODO this is probably expensive...
        GvDataType<T> dataType = adapter.getGridData().getGvDataType();

        ReviewViewParams params = mParamsFactory.getParams(dataType);
        ReviewViewActions<T> actions = newViewScreenActions(dataType);
        ReviewViewPerspective<T> perspective = new ReviewViewPerspective<>(adapter, actions, params);

        return new ReviewViewDefault<>(perspective);
    }

    private <T extends GvData> ReviewViewActions<T> newViewScreenActions(GvDataType<T> dataType) {
        SubjectAction<T> subject = new SubjectActionNone<>();
        RatingBarAction<T> ratingBar = new RatingBarExpandGrid<>(this, mLauncher);
        BannerButtonAction<T> bannerButton = new BannerButtonActionNone<>();
        GridItemAction<T> gridItem = getGridItem(dataType);
        MenuAction<T> menu = getMenu(dataType);
        return new ReviewViewActions<>(subject, ratingBar, bannerButton, gridItem, menu);
    }

    //TODO make type safe
    private <T extends GvData> GridItemAction<T> getGridItem(GvDataType<T> dataType) {
        LaunchableConfig viewerConfig = mConfig.getViewerConfig(dataType.getDatumName());
        if (dataType.equals(GvComment.TYPE)) {
            return (GridItemAction<T>) new GridItemComments(viewerConfig, this, mLauncher, new GvDataPacker<>());
        } else {
            return new GridItemConfigLauncher<>(viewerConfig, this, mLauncher, new GvDataPacker<>());
        }
    }

    //TODO make type safe
    private <T extends GvData> MenuAction<T> getMenu(GvDataType<T> dataType) {
        if (dataType.equals(GvComment.TYPE)) {
            return (MenuAction<T>) new MenuComments();
        } else {
            return new MenuActionNone<>(dataType.getDataName());
        }
    }

    @NonNull
    private ReviewViewActions<GvReviewOverview> getListScreenActions() {
        SubjectAction<GvReviewOverview> subject = new SubjectActionNone<>();
        RatingBarAction<GvReviewOverview> rb = new RatingBarExpandGrid<>(this, mLauncher);
        BannerButtonAction<GvReviewOverview> bb = new BannerButtonActionNone<>();
        GridItemAction<GvReviewOverview> giAction
                = new GridItemLauncher<>(this, mLauncher);
        MenuAction<GvReviewOverview> menuAction = new MenuActionNone<>();

        return new ReviewViewActions<>(subject, rb, bb, giAction, menuAction);
    }
}