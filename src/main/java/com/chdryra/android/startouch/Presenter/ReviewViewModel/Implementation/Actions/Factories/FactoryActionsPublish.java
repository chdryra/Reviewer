/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.ShareScreenPlatform;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.ShareScreenPublish;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.ShareScreenShare;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatformList;
import com.chdryra.android.startouch.Social.Interfaces.PlatformAuthoriser;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsPublish extends FactoryActionsNone<GvSocialPlatform> {
    private static final String TITLE = Strings.Screens.SHARE;
    private final ReviewEditor<?> mEditor;
    private final PublishAction mPublishAction;
    private final GvSocialPlatformList mPlatforms;
    private final PlatformAuthoriser mAuthoriser;

    public FactoryActionsPublish(ReviewEditor<?> editor,
                                 PublishAction publishAction,
                                 GvSocialPlatformList platforms,
                                 PlatformAuthoriser authoriser) {
        super(GvSocialPlatform.TYPE);
        mEditor = editor;
        mPublishAction = publishAction;
        mPlatforms = platforms;
        mAuthoriser = authoriser;
    }

    @Override
    public ButtonAction<GvSocialPlatform> newBannerButton() {
        return new ShareScreenShare(TITLE);
    }

    @Override
    public GridItemAction<GvSocialPlatform> newGridItem() {
        return new ShareScreenPlatform(mAuthoriser);
    }

    @Override
    public MenuAction<GvSocialPlatform> newMenu() {
        return newDefaultMenu(TITLE);
    }

    @Nullable
    @Override
    public ButtonAction<GvSocialPlatform> newContextButton() {
        return new ShareScreenPublish(mEditor, mPublishAction, mPlatforms);
    }
}
