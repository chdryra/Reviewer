package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.BannerButtonAddLocation;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GridItemDataEditLocation;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;


/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsLocations extends FactoryEditActionsDefault<GvLocation> {
    private static final GvDataType<GvLocation> TYPE = GvLocation.TYPE;

    public FactoryEditActionsLocations(Context context, ConfigDataUi config,
                                       FactoryLaunchableUi launchableFactory,
                                       FactoryGvData dataFactory,
                                       GvDataPacker<GvLocation> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected BannerButtonAction<GvLocation> newBannerButtonAdd() {
        return new BannerButtonAddLocation(getAdderConfig(), getBannerButtonTitle(), getDataFactory(),
                getPacker(), getLaunchableFactory());
    }

    @Override
    protected GridItemAction<GvLocation> newGridItemEdit() {
        return new GridItemDataEditLocation(getEditorConfig(), getLaunchableFactory(), getPacker());
    }

}
