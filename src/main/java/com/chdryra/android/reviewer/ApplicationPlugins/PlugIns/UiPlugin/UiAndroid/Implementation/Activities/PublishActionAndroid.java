/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
class PublishActionAndroid extends PublishAction {
    private final Context mContext;
    private final Class<? extends Activity> mActivityToPublish;

    PublishActionAndroid(ApplicationInstance app, Context context, Class<? extends Activity>
            activityToPublish) {
        super(app);
        mContext = context;
        mActivityToPublish = activityToPublish;
    }

    @Override
    public void onAddedToQueue(ReviewId id, CallbackMessage message) {
        Toast.makeText(mContext, "Publishing...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(mContext, mActivityToPublish);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }

    @Override
    public void onFailed(@Nullable Review review, @Nullable ReviewId id,
                                    CallbackMessage message) {
        Toast.makeText(mContext, "Problem publishing: " + message.getMessage(), Toast.LENGTH_LONG).show();
    }
}
