/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.support.annotation.NonNull;
import android.view.View;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActionBasic;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishButton extends ReviewViewActionBasic<GvSocialPlatform>
        implements ContextualButtonAction<GvSocialPlatform>{
    private final PublishAction mPublishAction;
    private final GvSocialPlatformList mPlatforms;
    private final ReviewEditor<?> mEditor;

    public PublishButton(ReviewEditor<?> editor, PublishAction publishAction, GvSocialPlatformList platforms) {
        mEditor = editor;
        mPlatforms = platforms;
        mPublishAction = publishAction;
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void onClick(View v) {
        mPublishAction.publish(mEditor.buildReview(), getChosenPlatforms());
    }

    @Override
    public String getButtonTitle() {
        return Strings.Buttons.PUBLISH;
    }

    @NonNull
    private ArrayList<String> getChosenPlatforms() {
        ArrayList<String> chosenPlatforms = new ArrayList<>();
        for(GvSocialPlatform platform : mPlatforms) {
            if(platform.isChosen()) chosenPlatforms.add(platform.getName());
        }

        return chosenPlatforms;
    }
}
