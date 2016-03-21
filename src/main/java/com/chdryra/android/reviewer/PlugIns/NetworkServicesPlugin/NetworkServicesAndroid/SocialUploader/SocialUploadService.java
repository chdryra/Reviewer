/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.SocialUploader;

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
public class SocialUploadService extends IntentService implements BatchReviewSharer.BatchReviewSharerListener {
    public static final String STATUS_UPDATE = "ReviewSharerService.StatusUpdate";
    public static final String STATUS_PERCENTAGE = "ReviewSharerService.Percentage";
    public static final String STATUS_RESULTS = "ReviewSharerService.PublishResults";

    public static final String UPLOAD_COMPLETED = "ReviewSharerService.PublishFinished";
    public static final String PUBLISH_RESULTS_OK = "ReviewSharerService.PublishResultsOk";
    public static final String PUBLISH_RESULTS_NOT_OK = "ReviewSharerService.PublishResultsNotOk";

    private static final String SERVICE = "ReviewSharerService";

    public SocialUploadService() {
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
        Intent update = new Intent(UPLOAD_COMPLETED);
        update.putParcelableArrayListExtra(PUBLISH_RESULTS_OK, new ArrayList<>(publishedOk));
        update.putParcelableArrayListExtra(PUBLISH_RESULTS_NOT_OK, new ArrayList<>(publishedNotOk));

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    private void batchPublish(String reviewId, ArrayList<String> platformNames) {
        ApplicationInstance app = ApplicationInstance.getInstance(getApplicationContext());
        Review review = app.getReviewFromLocalRepo(reviewId);
        TagsManager tagsManager = app.getTagsManager();

        Collection<SocialPlatform<?>> platforms = new ArrayList<>();
        for (SocialPlatform<?> platform : app.getSocialPlatformList()) {
            if (platformNames.contains(platform.getName())) platforms.add(platform);
        }

        BatchReviewSharer sharer = new BatchReviewSharer(platforms, this);
        sharer.shareReview(review, tagsManager);
    }
}
