/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.Interfaces.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Application.CurrentScreen;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewView<T extends GvData> extends DataObservable, DataObservable.DataObserver, LaunchableUi {
    String getSubject();

    float getRating();

    GvDataList<T> getGridData();

    GvDataList<T> getGridViewData();

    void setGridViewData(GvDataList<T> dataToShow);

    ReviewViewAdapter<T> getAdapter();

    ReviewViewContainer getContainer();

    ReviewViewParams getParams();

    ReviewViewActions<T> getActions();

    boolean isEditable();

    String getContainerSubject();

    float getContainerRating();

    void attachContainer(ReviewViewContainer container);

    void detachContainer(ReviewViewContainer container);

    void updateCover();

    CurrentScreen getScreen();

    View modifyIfNecessary(View v, LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState);
}