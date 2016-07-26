/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.view.View;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ContextButtonStamp<T extends GvData> extends ReviewViewActionBasic<T>
        implements ContextualButtonAction<T> {
    private ApplicationInstance mApp;
    private ReviewStamp mStamp;

    public ContextButtonStamp(ApplicationInstance app, ReviewStamp stamp) {
        mApp = app;
        mStamp = stamp;
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void onClick(View v) {
        mApp.launchFeed(mStamp.getAuthorId());
    }

    @Override
    public String getButtonTitle() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format
                (new Date(mStamp.getDate().getTime()));
    }
}
