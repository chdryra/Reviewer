/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewBuckets extends FactoryActionsViewData<GvBucket> {
    public FactoryActionsViewBuckets(ViewDataParameters<GvBucket> parameters) {
        super(parameters);
    }

    @Override
    public GridItemAction<GvBucket> newGridItem() {
        return new GridItemLauncher<>(getLauncher(), getViewFactory());
    }

    @Override
    public BannerButtonAction<GvBucket> newBannerButton() {
        return new BannerButtonActionNone<>(Strings.Buttons.DISTRIBUTION);
    }
}
