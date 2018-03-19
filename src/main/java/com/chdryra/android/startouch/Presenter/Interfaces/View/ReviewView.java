/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.View;

import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewView<T extends GvData>
        extends DataObservable, DataObservable.DataObserver, LaunchableUi, OptionSelectListener {

    ReviewViewAdapter<T> getAdapter();

    ReviewViewParams getParams();

    ReviewViewActions<T> getActions();

    String getContainerSubject();

    float getContainerRating();

    CurrentScreen getCurrentScreen();

    void attachEnvironment(ReviewViewContainer container, ApplicationInstance app);

    void detachEnvironment();

    ApplicationInstance getApp();
}