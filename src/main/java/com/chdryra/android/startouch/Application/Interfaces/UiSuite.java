/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Interfaces;

import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewNode;
import com.chdryra.android.startouch.Social.Implementation.SocialPlatformList;
import com.chdryra.android.startouch.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public interface UiSuite {
    CurrentScreen getCurrentScreen();

    UiConfig getConfig();

    UiLauncher getLauncher();

    ReviewView<?> newDataView(ReviewNode review, GvDataType<?> type);

    ReviewViewNode newFeedView(RepositorySuite repository, SocialProfileRef profile);

    ReviewView<?> newPublishView(ReviewEditor<?> editor,
                                 ReviewPublisher publisher,
                                 SocialPlatformList platforms,
                                 PlatformAuthoriser authoriser,
                                 PublishAction.PublishCallback callback);

    ConverterGv getGvConverter();

    FactoryLaunchCommands getCommandsFactory();

    void returnToFeedScreen();
}
