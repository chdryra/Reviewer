/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Social.Implementation.BatchSocialPublisher;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Implementation.PublisherFacebook;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BatchReviewSharer implements BatchSocialPublisher.BatchPublisherListener{
    private Activity mActivity;

    public BatchReviewSharer(Activity activity) {
        mActivity = activity;
    }

    public void shareNewReviewIfNecessary(Intent intent, ApplicationInstance app) {
        String reviewId = intent.getStringExtra(PublishingAction.PUBLISHED);
        ArrayList<String> platforms = intent.getStringArrayListExtra(PublishingAction.PLATFORMS);
        if(reviewId == null || platforms == null || platforms.size() == 0) return;

        BatchSocialPublisher publisher = getPublisher(platforms, app.getSocialPlatformList());
        publisher.publish(app.getReview(reviewId), app.getTagsManager(), this);
    }

    @NonNull
    private BatchSocialPublisher getPublisher(ArrayList<String> chosen,
                                              SocialPlatformList platformList) {
        Collection<SocialPublisher> publishers = new ArrayList<>();
        for(SocialPlatform platform : platformList) {
            if(chosen.contains(platform.getName())) publishers.add(platform.getPublisher());
        }

        return new BatchSocialPublisher(publishers);
    }

    @Override
    public void onPublished(Collection<PublishResults> results) {
        ArrayList<String> platformsOk = new ArrayList<>();
        ArrayList<String> platformsNotOk = new ArrayList<>();
        int numFollowers = 0;
        boolean facebook = false;
        for(PublishResults result : results) {
            if(result.wasSuccessful()) {
                if(result.getPublisherName().equals(PublisherFacebook.NAME)) facebook = true;
                platformsOk.add(result.getPublisherName());
                numFollowers += result.getFollowers();
            } else {
                platformsNotOk.add(result.getPublisherName());
            }
        }

        String num = String.valueOf(numFollowers);
        String fb = facebook ? "+ " : "";
        String followers = numFollowers == 1 ? " follower" : " followers";
        String message = "Published to " + num + fb + followers + " on " +
                StringUtils.join(platformsOk.toArray(), ", ");

        if(platformsNotOk.size() > 0) {
            message += "\nProblems publishing to " + StringUtils.join(platformsNotOk.toArray(), ",");
        }

        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }
}
