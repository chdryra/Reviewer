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
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.ButtonAddFacts;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.GridItemEditFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsFactsEdit extends FactoryActionsEditData<GvFact> {
    private static final GvDataType<GvFact> TYPE = GvFact.TYPE;


    public FactoryActionsFactsEdit(UiConfig config,
                                   FactoryGvData dataFactory,
                                   FactoryLaunchCommands commandsFactory) {
        super(TYPE, config, dataFactory, commandsFactory);
    }

    @Override
    public ButtonAction<GvFact> newBannerButton() {
        LaunchableConfig urlConfig = getUiConfig().getAdder(GvUrl.TYPE.getDatumName());
        return new ButtonAddFacts(getBannerButtonTitle(), getAdderConfig(), urlConfig,
                getDataFactory().newDataList(TYPE), getPacker());
    }

    @Override
    public GridItemAction<GvFact> newGridItem() {
        LaunchableConfig urlConfig = getUiConfig().getEditor(GvUrl.TYPE.getDatumName());
        return new GridItemEditFact(getEditorConfig(), urlConfig, getPacker());
    }

}
