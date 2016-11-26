/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BannerButtonAddFacts;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemEditFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsFactsEdit extends FactoryActionsEditData<GvFact> {
    private static final GvDataType<GvFact> TYPE = GvFact.TYPE;


    public FactoryActionsFactsEdit(UiConfig config,
                                   FactoryGvData dataFactory,
                                   ReviewLauncher launcher,
                                   FactoryCommands commandsFactory) {
        super(TYPE, config, dataFactory, launcher, commandsFactory);
    }

    @Override
    public BannerButtonAction<GvFact> newBannerButton() {
        LaunchableConfig urlConfig = getUiConfig().getAdder(GvUrl.TYPE.getDatumName());
        return new BannerButtonAddFacts(getBannerButtonTitle(), getAdderConfig(), urlConfig,
                getDataFactory().newDataList(TYPE), getPacker());
    }

    @Override
    public GridItemAction<GvFact> newGridItem() {
        LaunchableConfig urlConfig = getUiConfig().getEditor(GvUrl.TYPE.getDatumName());
        return new GridItemEditFact(getEditorConfig(), urlConfig, getPacker());
    }

}
