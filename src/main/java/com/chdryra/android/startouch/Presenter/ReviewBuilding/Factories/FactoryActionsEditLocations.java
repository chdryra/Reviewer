/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.ButtonAddLocation;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.GridItemEditLocation;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.MaiMapLocations;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.MenuEditLocations;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;


/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsEditLocations extends FactoryActionsEditData<GvLocation> {
    private static final GvDataType<GvLocation> TYPE = GvLocation.TYPE;

    public FactoryActionsEditLocations(UiConfig config,
                                       FactoryGvData dataFactory,
                                       FactoryLaunchCommands commandsFactory) {
        super(TYPE, config, dataFactory, commandsFactory);
    }

    @Override
    public ButtonAction<GvLocation> newBannerButton() {
        return new ButtonAddLocation(getAdderConfig(), getUiConfig().getBespokeEditor(TYPE
                .getDatumName()),
                getBannerButtonTitle(), getDataFactory().newDataList(TYPE),
                getPacker());
    }

    @Override
    public GridItemAction<GvLocation> newGridItem() {
        return new GridItemEditLocation(getEditorConfig(), getUiConfig().getBespokeEditor(TYPE
                .getDatumName()),
                getPacker());
    }

    @Override
    public MenuAction<GvLocation> newMenu() {
        return new MenuEditLocations(newUpAction(), newDeleteAction(),
                newPreviewAction(),
                new MaiMapLocations(getCommandsFactory().newLaunchMappedCommand(null)));
    }
}
