package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid;

import com.chdryra.android.reviewer.PlugIns.UiPlugin.Api.UiPlugin;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Activities.ActivityBuildReview;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Activities.ActivityEditLocationMap;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Activities.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Activities.ActivityShareReview;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Activities.ActivityViewLocation;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Dialogs.Implementation.GvDataDialogs;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.View.Configs.AddEditViewClasses;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchablesList;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.LaunchablesListBasic;

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
    private static final class AndroidLaunchables extends LaunchablesListBasic {

        private AndroidLaunchables() {
            super(ActivityBuildReview.class, ActivityEditLocationMap.class, ActivityShareReview.class);

            addDataClasses(new AddEditViewClasses<>(GvTag.TYPE, GvDataDialogs.AddTag.class,
                    GvDataDialogs.EditTag
                            .class, GvDataDialogs.ViewTag.class));

            addDataClasses(new AddEditViewClasses<>(GvCriterion.TYPE,
                    GvDataDialogs.AddCriterion.class, GvDataDialogs.EditCriterion.class,
                    GvDataDialogs.ViewCriterion.class));

            addDataClasses(new AddEditViewClasses<>(GvComment.TYPE, GvDataDialogs.AddComment
                    .class, GvDataDialogs
                    .EditComment.class, GvDataDialogs.ViewComment.class));

            addDataClasses(new AddEditViewClasses<>(GvImage.TYPE, null,
                    GvDataDialogs.EditImage.class, GvDataDialogs.ViewImage.class));

            addDataClasses(new AddEditViewClasses<>(GvFact.TYPE, GvDataDialogs.AddFact.class,
                    GvDataDialogs
                    .EditFact.class, GvDataDialogs.ViewFact.class));

            addDataClasses(new AddEditViewClasses<>(GvLocation.TYPE, GvDataDialogs.AddLocation.class,
                    GvDataDialogs.EditLocation.class, ActivityViewLocation.class));

            addDataClasses(new AddEditViewClasses<>(GvUrl.TYPE, ActivityEditUrlBrowser.class,
                    ActivityEditUrlBrowser.class, ActivityEditUrlBrowser.class));

            addDataClasses(new AddEditViewClasses<>(GvSubject.TYPE, null, null, GvDataDialogs.ViewSubject.class));

            addDataClasses(new AddEditViewClasses<>(GvDate.TYPE, null, null, GvDataDialogs.ViewDate.class));
        }
    }
}
