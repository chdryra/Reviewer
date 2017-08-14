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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ActionsParameters;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemBespokeView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewImages extends FactoryActionsViewData<GvImage.Reference> {
    private final ReviewNode mNode;

    public FactoryActionsViewImages(ActionsParameters<GvImage.Reference> parameters,
                                    ReviewNode node) {
        super(parameters);
        mNode = node;
    }

    @Override
    public GridItemAction<GvImage.Reference> newGridItem() {
        LaunchBespokeViewCommand click = getCommandsFactory().newLaunchBespokeViewCommand(mNode,
                "Launch Image Viewer", GvImage.TYPE);
        return new GridItemBespokeView<>(getLauncher(), getViewFactory(), getGridItemConfig(), click);
    }
}
