/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.Api.UiPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityBuildReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityEditLocationMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityFeed;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityFormatReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityLogin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityNodeMapper;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityPublishReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityReviewView;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivitySignUp;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityViewLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogOptions;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.GvDataDialogs;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.View.Configs.Implementation.DataLaunchables;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.LaunchablesHolder;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchablesList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UiAndroid implements UiPlugin {
    @Override
    public LaunchablesList getUiLaunchables() {
        return new AndroidLaunchables();
    }

    /**
     * Defines the adder, editor and display UIs to use with each data type.
     */
    private static final class AndroidLaunchables extends LaunchablesHolder {

        private AndroidLaunchables() {
            super(ActivityLogin.class, ActivitySignUp.class, ActivityFeed.class,
                    ActivityBuildReview.class, ActivityFormatReview.class,
                    ActivityEditLocationMap.class, ActivityNodeMapper.class,
                    ActivityPublishReview.class, DialogOptions.class, ActivityReviewView.class);

            addDataClasses(new DataLaunchables<>(GvTag.TYPE, GvDataDialogs.AddTag.class,
                    GvDataDialogs.EditTag.class, GvDataDialogs.ViewTag.class));

            addDataClasses(new DataLaunchables<>(GvCriterion.TYPE,
                    GvDataDialogs.AddCriterion.class, GvDataDialogs.EditCriterion.class,
                    GvDataDialogs.ViewCriterion.class));

            addDataClasses(new DataLaunchables<>(GvComment.TYPE, GvDataDialogs.AddComment
                    .class, GvDataDialogs
                    .EditComment.class, GvDataDialogs.ViewComment.class));

            addDataClasses(new DataLaunchables<>(GvImage.TYPE, null,
                    GvDataDialogs.EditImage.class, GvDataDialogs.ViewImage.class));

            addDataClasses(new DataLaunchables<>(GvFact.TYPE, GvDataDialogs.AddFact.class,
                    GvDataDialogs.EditFact.class, GvDataDialogs.ViewFact.class));

            addDataClasses(new DataLaunchables<>(GvLocation.TYPE, GvDataDialogs.AddLocation.class,
                    GvDataDialogs.EditLocation.class, ActivityViewLocation.class));

            addDataClasses(new DataLaunchables<>(GvUrl.TYPE, ActivityEditUrlBrowser.class,
                    ActivityEditUrlBrowser.class, ActivityEditUrlBrowser.class));

            addDataClasses(new DataLaunchables<>(GvAuthorId.TYPE, null, null, GvDataDialogs.ViewAuthor.class));

            addDataClasses(new DataLaunchables<>(GvSubject.TYPE, null, null, GvDataDialogs.ViewSubject.class));

            addDataClasses(new DataLaunchables<>(GvDate.TYPE, null, null, GvDataDialogs.ViewDate.class));
        }
    }
}
