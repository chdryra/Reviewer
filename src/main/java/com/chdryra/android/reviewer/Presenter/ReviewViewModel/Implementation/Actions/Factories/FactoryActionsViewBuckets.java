/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.BannerButtonCommandable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewBuckets extends FactoryActionsViewData<GvBucket> {
    private final ReviewNode mNode;

    public FactoryActionsViewBuckets(ViewDataParameters<GvBucket> parameters, ReviewNode node) {
        super(parameters);
        mNode = node;
    }

    @Override
    public GridItemAction<GvBucket> newGridItem() {
        return new GridItemLauncher<>(getLauncher(), getViewFactory());
    }

    @Override
    public BannerButtonAction<GvBucket> newBannerButton() {
        BannerButtonCommandable<GvBucket> button
                = new BannerButtonCommandable<>(Strings.Buttons.DISTRIBUTION);
        button.setClick(getCommandsFactory().newLaunchFormattedCommand(mNode));
        return button;
    }
}
