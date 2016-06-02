/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BannerButtonAddLocation;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemDataEditLocation;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;


/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsLocations extends FactoryEditActionsDefault<GvLocation> {
    private static final GvDataType<GvLocation> TYPE = GvLocation.TYPE;

    public FactoryEditActionsLocations(Context context, ConfigUi config,
                                       FactoryGvData dataFactory,
                                       ParcelablePacker<GvLocation> packer) {
        super(context, TYPE, config, dataFactory, packer);
    }

    @Override
    protected BannerButtonAction<GvLocation> newBannerButtonAdd() {
        return new BannerButtonAddLocation(getAdderConfig(), getConfig().getMapEditor(),
                getBannerButtonTitle(), getDataFactory().newDataList(TYPE),
                getPacker());
    }

    @Override
    protected GridItemAction<GvLocation> newGridItemEdit() {
        return new GridItemDataEditLocation(getEditorConfig(), getConfig().getMapEditor(),
                getPacker());
    }

}
