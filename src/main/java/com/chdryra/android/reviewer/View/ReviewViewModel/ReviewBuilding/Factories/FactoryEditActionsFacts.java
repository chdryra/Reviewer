package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvUrl;
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
public class FactoryEditActionsFacts extends FactoryEditActionsDefault<GvFact> {
    private static final GvDataType<GvFact> TYPE = GvFact.TYPE;


    public FactoryEditActionsFacts(Context context, ConfigDataUi config,
                                   FactoryLaunchableUi launchableFactory,
                                   FactoryGvData dataFactory,
                                   GvDataPacker<GvFact> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected BannerButtonAction<GvFact> newBannerButtonAdd() {
        LaunchableConfig<GvUrl> urlConfig = getConfig().getAdderConfig(GvUrl
                .TYPE);
        return new BannerButtonAddFacts(getBannerButtonTitle(), getAdderConfig(), urlConfig,
                getDataFactory(), getPacker(), getLaunchableFactory());
    }

    @Override
    protected GridItemAction<GvFact> newGridItemEdit() {
        LaunchableConfig<GvUrl> urlConfig = getConfig().getEditorConfig(GvUrl.TYPE);
        return new GridItemEditFact(getEditorConfig(), urlConfig, getLaunchableFactory(), getPacker());
    }

}
