/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.SocialUploader;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPublishingService extends IntentService 
        implements BatchSocialPublisher.BatchSocialPublisherListener {
    public static final String STATUS_UPDATE = "SocialPublishingService.StatusUpdate";
    public static final String STATUS_PERCENTAGE = "SocialPublishingService.Percentage";
    public static final String STATUS_RESULTS = "SocialPublishingService.PublishResults";

    public static final String PUBLISHING_COMPLETED = "SocialPublishingService.PublishFinished";
    public static final String PUBLISH_RESULTS_OK = "SocialPublishingService.PublishResultsOk";
    public static final String PUBLISH_RESULTS_NOT_OK = "SocialPublishingService.PublishResultsNotOk";

    private static final String SERVICE = "SocialPublishingService";

    public SocialPublishingService() {
        super(SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String reviewId = intent.getStringExtra(PublishingAction.PUBLISHED);
        ArrayList<String> platforms = intent.getStringArrayListExtra(PublishingAction.PLATFORMS);

        if (reviewId != null && platforms != null && platforms.size() > 0) {
            batchPublish(reviewId, platforms);
        }
    }

    @Override
    public void onStatusUpdate(double percentage, PublishResults results) {
        Intent update = new Intent(STATUS_UPDATE);
        update.putExtra(STATUS_PERCENTAGE, percentage);
        update.putExtra(STATUS_RESULTS, results);

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    @Override
    public void onPublished(Collection<PublishResults> publishedOk,
                            Collection<PublishResults> publishedNotOk) {
        Intent update = new Intent(PUBLISHING_COMPLETED);
        update.putParcelableArrayListExtra(PUBLISH_RESULTS_OK, new ArrayList<>(publishedOk));
        update.putParcelableArrayListExtra(PUBLISH_RESULTS_NOT_OK, new ArrayList<>(publishedNotOk));

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    private void batchPublish(String reviewId, ArrayList<String> platformNames) {
        ApplicationInstance app = ApplicationInstance.getInstance(getApplicationContext());
        Review review = app.getReview(reviewId);
        TagsManager tagsManager = app.getTagsManager();

        Collection<SocialPlatform<?>> platforms = new ArrayList<>();
        for (SocialPlatform<?> platform : app.getSocialPlatformList()) {
            if (platformNames.contains(platform.getName())) platforms.add(platform);
        }

        BatchSocialPublisher publisher = new BatchSocialPublisher(platforms, this);
        publisher.publishReview(review, tagsManager);
    }
}
