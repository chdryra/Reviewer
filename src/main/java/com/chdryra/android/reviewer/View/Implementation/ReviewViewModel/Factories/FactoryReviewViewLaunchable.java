package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Builders
        .BuilderChildListView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .BannerButtonActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .GridItemComments;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .GridItemConfigLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .GridItemLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .MenuActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.MenuComments;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .RatingBarExpandGrid;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .ReviewViewDefault;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .ReviewViewPerspective;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation
        .SubjectActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces
        .BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding
        .Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewLaunchable {
    private BuilderChildListView mChildListScreenBuilder;
    private FactoryReviewViewParams mParamsFactory;
    private ConfigDataUi mConfig;
    private LaunchableUiLauncher mLauncher;

    public FactoryReviewViewLaunchable(ConfigDataUi config, FactoryReviewViewParams paramsFactory,
                                       LaunchableUiLauncher launcher) {
        mConfig = config;
        mParamsFactory = paramsFactory;
        mLauncher = launcher;
        mChildListScreenBuilder = new BuilderChildListView();
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
