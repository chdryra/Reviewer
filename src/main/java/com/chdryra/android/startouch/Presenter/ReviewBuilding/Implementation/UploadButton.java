/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.support.annotation.NonNull;
import android.view.View;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonActionNone;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class UploadButton<T extends GvData> extends ButtonActionNone<T> {
    private final PublishAction mPublishAction;

    public UploadButton(String title, PublishAction publishAction) {
        super(title);
        mPublishAction = publishAction;
    }

    protected abstract Review getReview();

    @NonNull
    protected ArrayList<String> getSocialPlatforms() {
        return new ArrayList<>();
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void onClick(View v) {
        mPublishAction.publish(getReview(), getSocialPlatforms());
    }
}
