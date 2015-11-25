/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.View.Implementation.LaunchableActivities.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.View.Implementation.LaunchableActivities.ActivityViewLocation;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Implementation.GvDataDialogs;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvSubject;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvUrl;

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
        add(new LaunchableClasses<>(GvTag.TYPE, GvDataDialogs.AddTag.class,
                GvDataDialogs.EditTag
                .class, GvDataDialogs.ViewTag.class));

        add(new LaunchableClasses<>(GvCriterion.TYPE,
                GvDataDialogs.AddCriterion.class, GvDataDialogs.EditCriterion.class,
                GvDataDialogs.ViewCriterion.class));

        add(new LaunchableClasses<>(GvComment.TYPE, GvDataDialogs.AddComment
                .class, GvDataDialogs
                .EditComment.class, GvDataDialogs.ViewComment.class));

        add(new LaunchableClasses<>(GvImage.TYPE, null,
                GvDataDialogs.EditImage.class, GvDataDialogs.ViewImage.class));

        add(new LaunchableClasses<>(GvFact.TYPE, GvDataDialogs.AddFact.class, GvDataDialogs
                .EditFact.class, GvDataDialogs.ViewFact.class));

        add(new LaunchableClasses<>(GvLocation.TYPE, GvDataDialogs.AddLocation.class, GvDataDialogs.EditLocation
                .class, ActivityViewLocation.class));

        add(new LaunchableClasses<>(GvUrl.TYPE, ActivityEditUrlBrowser.class,
                ActivityEditUrlBrowser.class, ActivityEditUrlBrowser.class));

        add(new LaunchableClasses<>(GvSubject.TYPE, null, null, GvDataDialogs.ViewSubject.class));

        add(new LaunchableClasses<>(GvDate.TYPE, null, null, GvDataDialogs.ViewDate.class));
    }
}
