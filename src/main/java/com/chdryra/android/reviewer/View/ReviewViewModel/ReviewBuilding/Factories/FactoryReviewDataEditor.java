package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .BannerButtonAddFacts;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .BannerButtonEdit;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .GridItemAddEditComments;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .GridItemAddEditFact;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GridItemEdit;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.MenuDataEdit;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .MenuEditComment;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .RatingBarEdit;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .ReviewDataEditorImpl;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.SubjectEdit;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewDataEditor {
    private Context mContext;
    private FactoryReviewViewParams mParamsFactory;
    private FactoryLaunchableUi mLaunchableFactory;
    private ConfigDataUi mConfig;
    private FactoryGvData mDataFactory;

    public FactoryReviewDataEditor(Context context, FactoryReviewViewParams paramsFactory,
                                   FactoryLaunchableUi launchableFactory, ConfigDataUi config,
                                   FactoryGvData dataFactory) {
        mContext = context;
        mParamsFactory = paramsFactory;
        mLaunchableFactory = launchableFactory;
        mConfig = config;
        mDataFactory = dataFactory;
    }

    public <T extends GvData> ReviewDataEditor<T> newEditor(DataBuilderAdapter<T> adapter) {

        ReviewViewParams params = mParamsFactory.getParams(adapter.getGvDataType());
        ReviewViewActions<T> actions = newActions(adapter.getGvDataType());
        return new ReviewDataEditorImpl<>(adapter, params, actions);
    }

    private <T extends GvData> ReviewViewActions<T> newActions(GvDataType<T> dataType) {
        SubjectEdit<T> subject = new SubjectEdit<>();
        RatingBarEdit<T> ratingbar = new RatingBarEdit<>();
        BannerButtonEdit<T> bannerButton = newBannerButtonAction(dataType);
        GridItemEdit<T> gridItem = newGridItemAction(dataType);
        MenuDataEdit<T> menuAction = newMenuAction(dataType);

        return new ReviewViewActions<>(subject, ratingbar, bannerButton, gridItem, menuAction);
    }

    private <T extends GvData> MenuDataEdit<T> newMenuAction(GvDataType<T> dataType) {
        MenuDataEdit menuAction;
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            menuAction = new MenuEditComment();
        } else {
            menuAction = new MenuDataEdit<>(dataType);
        }

        //TODO make type safe
        return menuAction;
    }

    private <T extends GvData> GridItemEdit<T> newGridItemAction(GvDataType<T> dataType) {
        GridItemEdit gridItem;
        if(dataType.equals(GvFactList.GvFact.TYPE)) {
            gridItem = newGridItemFactEdit();
        } else if(dataType.equals(GvCommentList.GvComment.TYPE)) {
            gridItem = newGridItemCommentEdit();
        } else {
            LaunchableConfig<T> config = mConfig.getEditorConfig(dataType);
            gridItem = new GridItemEdit<>(config, mLaunchableFactory, new GvDataPacker<T>());
        }

        //TODO make type safe
        return gridItem;
    }

    private <T extends GvData> BannerButtonEdit<T> newBannerButtonAction(GvDataType<T> dataType) {
        BannerButtonEdit bannerButton;

        String title = getBannerButtonTitle(dataType);
        if(dataType.equals(GvFactList.GvFact.TYPE)) {
            bannerButton = newBannerButtonAddFacts(title);
        } else {
            LaunchableConfig<T> config = mConfig.getAdderConfig(dataType);
            bannerButton = new BannerButtonEdit<>(config, title, dataType, mDataFactory,
                    new GvDataPacker<T>(), mLaunchableFactory);
        }

        //TODO make type safe
        return bannerButton;
    }

    private <T extends GvData> String getBannerButtonTitle(GvDataType<T> dataType) {
        String title = mContext.getResources().getString(R.string.button_add);
        title += " " + dataType.getDataName();
        return title;
    }

    private BannerButtonAddFacts newBannerButtonAddFacts(String title) {
        LaunchableConfig<GvFactList.GvFact> factConfig = mConfig.getAdderConfig(GvFactList
                .GvFact.TYPE);
        LaunchableConfig<GvUrlList.GvUrl> urlConfig = mConfig.getAdderConfig(GvUrlList.GvUrl.TYPE);
        return new BannerButtonAddFacts(title, factConfig, urlConfig,
                mLaunchableFactory);
    }

    private GridItemEdit<GvFactList.GvFact> newGridItemFactEdit() {
        LaunchableConfig<GvFactList.GvFact> factConfig = mConfig.getEditorConfig(GvFactList
                .GvFact.TYPE);
        LaunchableConfig<GvUrlList.GvUrl> urlConfig = mConfig.getEditorConfig(GvUrlList.GvUrl.TYPE);
        return new GridItemAddEditFact(factConfig, urlConfig,
                mLaunchableFactory, new GvDataPacker<GvFactList.GvFact>());
    }

    private GridItemEdit<GvCommentList.GvComment> newGridItemCommentEdit() {
        LaunchableConfig<GvCommentList.GvComment> config
                = mConfig.getEditorConfig(GvCommentList.GvComment.TYPE);
        return new GridItemAddEditComments(config, mLaunchableFactory,
                new GvDataPacker<GvCommentList.GvComment>());
    }
}
