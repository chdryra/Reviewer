/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenShareButton;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemClickObserved;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.RatingEditBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectEditBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsBuild<GC extends GvDataList<? extends GvDataParcelable>> extends FactoryActionsNone<GC> {
    private LaunchableConfig mShareScreenUi;

    public FactoryActionsBuild(GvDataType<GC> dataType, LaunchableConfig shareScreenUi) {
        super(dataType);
        mShareScreenUi = shareScreenUi;
    }

    @Override
    public SubjectAction<GC> newSubject() {
        return new SubjectEditBuildScreen<>();
    }

    @Override
    public RatingBarAction<GC> newRatingBar() {
        return new RatingEditBuildScreen<>();
    }

    @Override
    public BannerButtonAction<GC> newBannerButton() {
        return new BannerButtonActionNone<>(Strings.Buttons.BUILD_SCREEN_BANNER);
    }

    @Override
    public GridItemAction<GC> newGridItem() {
        return new GridItemClickObserved<>();
    }

    @Override
    public MenuAction<GC> newMenu() {
        return new MenuBuildScreen<>(Strings.Screens.BUILD);
    }

    @Nullable
    @Override
    public ContextualButtonAction<GC> newContextButton() {
        return new BuildScreenShareButton<>(mShareScreenUi);
    }
}
