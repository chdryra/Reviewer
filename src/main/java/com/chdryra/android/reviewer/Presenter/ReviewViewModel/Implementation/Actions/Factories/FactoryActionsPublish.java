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
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemShareScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishButton;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsPublish extends FactoryActionsNone<GvSocialPlatform> {
    private static final String TITLE = Strings.Screens.SHARE;
    private PlatformAuthoriser mAuthoriser;
    private GvSocialPlatformList mPlatforms;
    private PublishAction mPublishAction;

    public FactoryActionsPublish(PlatformAuthoriser authoriser,
                                 GvSocialPlatformList platforms,
                                 PublishAction publishAction) {
        super(GvSocialPlatform.TYPE);
        mAuthoriser = authoriser;
        mPlatforms = platforms;
        mPublishAction = publishAction;
    }

    @Override
    public BannerButtonAction<GvSocialPlatform> newBannerButton() {
        return new BannerButtonActionNone<>(TITLE);
    }

    @Override
    public GridItemAction<GvSocialPlatform> newGridItem() {
        return new GridItemShareScreen(mAuthoriser);
    }

    @Override
    public MenuAction<GvSocialPlatform> newMenu() {
        return newMenu(TITLE);
    }

    @Nullable
    @Override
    public ContextualButtonAction<GvSocialPlatform> newContextButton() {
        return new PublishButton(mPlatforms, mPublishAction);
    }
}
