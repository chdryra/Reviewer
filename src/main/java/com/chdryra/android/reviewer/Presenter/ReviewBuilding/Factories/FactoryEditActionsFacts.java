package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BannerButtonAddFacts;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemDataEditFact;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsFacts extends FactoryEditActionsDefault<GvFact> {
    private static final GvDataType<GvFact> TYPE = GvFact.TYPE;


    public FactoryEditActionsFacts(Context context, ConfigDataUi config,
                                   LaunchableUiLauncher launchableFactory,
                                   FactoryGvData dataFactory,
                                   GvDataPacker<GvFact> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected BannerButtonAction<GvFact> newBannerButtonAdd() {
        LaunchableConfig urlConfig = getConfig().getAdderConfig(GvUrl.TYPE.getDatumName());
        return new BannerButtonAddFacts(getBannerButtonTitle(), getAdderConfig(), urlConfig,
                getLaunchableFactory(), getDataFactory().newDataList(TYPE), getPacker());
    }

    @Override
    protected GridItemAction<GvFact> newGridItemEdit() {
        LaunchableConfig urlConfig = getConfig().getEditorConfig(GvUrl.TYPE.getDatumName());
        return new GridItemDataEditFact(getEditorConfig(), urlConfig, getLaunchableFactory(), getPacker());
    }

}
