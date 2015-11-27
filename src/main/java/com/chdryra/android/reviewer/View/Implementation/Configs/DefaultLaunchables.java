/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer.View.Implementation.Configs;

import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Activities
        .ActivityBuildReview;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Activities
        .ActivityShareReview;
import com.chdryra.android.reviewer.View.Implementation.SpecialisedActivities
        .ActivityEditLocationMap;
import com.chdryra.android.reviewer.View.Implementation.SpecialisedActivities.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.View.Implementation.SpecialisedActivities.ActivityViewLocation;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Implementation.GvDataDialogs;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrl;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Defines the adder, editor and display UIs to use with each data type.
 */
public final class DefaultLaunchables extends LaunchablesList {

    public DefaultLaunchables() {
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