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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewImages extends FactoryActionsViewData<GvImage.Reference> {
    private final ReviewNode mNode;
    private final LaunchableConfig mBespokeView;
    private final GvConverterImages mConverter;

    public FactoryActionsViewImages(ViewDataParameters<GvImage.Reference> parameters,
                                    ReviewNode node,
                                    LaunchableConfig bespokeView,
                                    GvConverterImages converter) {
        super(parameters);
        mNode = node;
        mBespokeView = bespokeView;
        mConverter = converter;
    }

    @Override
    public GridItemAction<GvImage.Reference> newGridItem() {
        LaunchBespokeViewCommand click = getCommandsFactory().newLaunchBespokeViewCommand(mNode,
                "Launch Image Viewer", GvImage.TYPE);
        return new GridItemCommand<>(click, null, null);
    }
}
