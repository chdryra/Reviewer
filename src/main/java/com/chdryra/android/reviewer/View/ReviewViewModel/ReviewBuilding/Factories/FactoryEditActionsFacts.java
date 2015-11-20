package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .BannerButtonAddFacts;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .GridItemEditFact;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsFacts extends FactoryEditActionsDefault<GvFactList.GvFact> {
    private static final GvDataType<GvFactList.GvFact> TYPE = GvFactList.GvFact.TYPE;


    public FactoryEditActionsFacts(Context context, ConfigDataUi config,
                                   FactoryLaunchableUi launchableFactory,
                                   FactoryGvData dataFactory,
                                   GvDataPacker<GvFactList.GvFact> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected BannerButtonAction<GvFactList.GvFact> newBannerButtonAdd() {
        LaunchableConfig<GvUrlList.GvUrl> urlConfig = getConfig().getAdderConfig(GvUrlList.GvUrl
                .TYPE);
        return new BannerButtonAddFacts(getBannerButtonTitle(), getAdderConfig(), urlConfig,
                getDataFactory(), getPacker(), getLaunchableFactory());
    }

    @Override
    protected GridItemAction<GvFactList.GvFact> newGridItemEdit() {
        LaunchableConfig<GvUrlList.GvUrl> urlConfig = getConfig().getEditorConfig(GvUrlList.GvUrl.TYPE);
        return new GridItemEditFact(getEditorConfig(), urlConfig, getLaunchableFactory(), getPacker());
    }

}
