/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareScreenPublish extends UploadButton<GvSocialPlatform> {
    private final ReviewEditor<?> mEditor;
    private final GvSocialPlatformList mPlatforms;

    public ShareScreenPublish(ReviewEditor<?> editor, PublishAction publishAction, GvSocialPlatformList platforms) {
        super(Strings.Buttons.PUBLISH, publishAction);
        mEditor = editor;
        mPlatforms = platforms;
    }

    @Override
    protected Review getReview() {
        return mEditor.buildReview();
    }

    @NonNull
    @Override
    protected ArrayList<String> getSocialPlatforms() {
        ArrayList<String> chosenPlatforms = new ArrayList<>();
        for(GvSocialPlatform platform : mPlatforms) {
            if(platform.isChosen()) chosenPlatforms.add(platform.getName());
        }

        return chosenPlatforms;
    }
}
