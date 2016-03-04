/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.BatchReviewSharerListener;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewSharerService extends IntentService implements BatchReviewSharerListener{
    public static final String SERVICE = "ReviewSharerService";

    public ReviewSharerService() {
        super(SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String reviewId = intent.getStringExtra(PublishingAction.PUBLISHED);
        ArrayList<String> platforms = intent.getStringArrayListExtra(PublishingAction.PLATFORMS);

        if(reviewId != null && platforms != null && platforms.size() > 0) {
            ApplicationInstance app = ApplicationInstance.getInstance(getApplicationContext());
            Review review = app.getReview(reviewId);
            TagsManager tagsManager = app.getTagsManager();
            Collection<SocialPlatform<?>> chosenPlatforms
                    = getPlatforms(platforms, app.getSocialPlatformList());

            new BatchReviewSharerImpl(this).shareReview(review, tagsManager, chosenPlatforms);
        }
    }

    @Override
    public void onPublished(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Collection<SocialPlatform<?>> getPlatforms(ArrayList<String> chosen,
                                              SocialPlatformList platformList) {
        Collection<SocialPlatform<?>> platforms = new ArrayList<>();
        for(SocialPlatform<?> platform : platformList) {
            if(chosen.contains(platform.getName())) platforms.add(platform);
        }

        return platforms;
    }
}
