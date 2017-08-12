/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.GridItemLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewLocations;


import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewLocations extends FactoryActionsViewData<GvLocation.Reference> {
    private final ReviewNode mNode;
    private final LaunchableConfig mMapper;
    private final GvConverterLocations mConverter;

    public FactoryActionsViewLocations(ActionsParameters<GvLocation.Reference> parameters,
                                       ReviewNode node, LaunchableConfig mapper,
                                       GvConverterLocations converter) {
        super(parameters);
        mNode = node;
        mMapper = mapper;
        mConverter = converter;
    }

    @Override
    public MenuAction<GvLocation.Reference> newMenu() {
        LaunchBespokeViewCommand command
                = getCommandsFactory().newLaunchMappedCommand(mNode);
        return new MenuViewLocations(newOptionsMenuItem(), new MaiCommand<GvLocation.Reference>(command));
    }

    @Override
    public GridItemAction<GvLocation.Reference> newGridItem() {
        return new GridItemLocation(getLauncher(), getViewFactory(), getGridItemConfig(), mMapper, mConverter);
    }
}
