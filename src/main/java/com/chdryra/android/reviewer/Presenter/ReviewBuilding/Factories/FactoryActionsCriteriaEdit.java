/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MaiRatingAverage;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuEditCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsCriteriaEdit extends FactoryActionsEditData<GvCriterion> {
    private static final GvDataType<GvCriterion> TYPE
            = GvCriterion.TYPE;

    public FactoryActionsCriteriaEdit(UiConfig config,
                                      FactoryGvData dataFactory,
                                      FactoryCommands commandsFactory) {
        super(TYPE, config, dataFactory, commandsFactory);
    }

    @Override
    public MenuAction<GvCriterion> newMenu() {
        return new MenuEditCriteria(newUpAction(), newDeleteAction(),
                newPreviewAction(), new MaiRatingAverage<GvCriterion>());
    }
}
