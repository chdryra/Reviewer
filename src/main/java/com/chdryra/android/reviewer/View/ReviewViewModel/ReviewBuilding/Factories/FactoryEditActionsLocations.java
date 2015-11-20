package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.BannerButtonAddLocation;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GridItemEditLocation;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;


/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsLocations extends FactoryEditActionsDefault<GvLocationList.GvLocation> {
    private static final GvDataType<GvLocationList.GvLocation> TYPE = GvLocationList.GvLocation.TYPE;

    public FactoryEditActionsLocations(Context context, ConfigDataUi config,
                                       FactoryLaunchableUi launchableFactory,
                                       FactoryGvData dataFactory,
                                       GvDataPacker<GvLocationList.GvLocation> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected BannerButtonAction<GvLocationList.GvLocation> newBannerButtonAdd() {
        return new BannerButtonAddLocation(getAdderConfig(), getBannerButtonTitle(), getDataFactory(),
                getPacker(), getLaunchableFactory());
    }

    @Override
    protected GridItemAction<GvLocationList.GvLocation> newGridItemEdit() {
        return new GridItemEditLocation(getEditorConfig(), getLaunchableFactory(), getPacker());
    }

}
