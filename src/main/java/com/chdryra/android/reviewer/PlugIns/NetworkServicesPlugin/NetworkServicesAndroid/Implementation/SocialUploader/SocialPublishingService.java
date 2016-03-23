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
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryError;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
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
        implements BatchSocialPublisher.BatchSocialPublisherListener,
        RepositoryCallback{
    public static final String STATUS_UPDATE = "SocialPublishingService.StatusUpdate";
    public static final String STATUS_PERCENTAGE = "SocialPublishingService.Percentage";
    public static final String STATUS_RESULTS = "SocialPublishingService.PublishResults";

    public static final String PUBLISHING_COMPLETED = "SocialPublishingService.PublishFinished";
    public static final String PUBLISHING_ERROR = "SocialPublishingService.PublishError";
    public static final String PUBLISH_OK = "SocialPublishingService.PublishResultsOk";
    public static final String PUBLISH_NOT_OK = "SocialPublishingService.PublishResultsNotOk";

    private static final String SERVICE = "SocialPublishingService";

    private String mReviewId;
    private ArrayList<String> mPlatforms;

    public SocialPublishingService() {
        super(SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mReviewId = intent.getStringExtra(PublishingAction.PUBLISHED);
        mPlatforms = intent.getStringArrayListExtra(PublishingAction.PLATFORMS);

        if (mReviewId != null && mPlatforms != null && mPlatforms.size() > 0) {
            batchPublish();
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
        update.putParcelableArrayListExtra(PUBLISH_OK, new ArrayList<>(publishedOk));
        update.putParcelableArrayListExtra(PUBLISH_NOT_OK, new ArrayList<>(publishedNotOk));

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    private void batchPublish() {
        ApplicationInstance app = ApplicationInstance.getInstance(getApplicationContext());
        app.getReview(mReviewId, this);
    }

    @Override
    public void onFetched(@Nullable Review review, RepositoryError error) {
        if(review != null && !error.isError()) {
            doPublish(review);
        } else {
            Intent update = new Intent(PUBLISHING_COMPLETED);
            String message = error.isError() ? error.getMessage() :
                    review == null ? "Review not found" : "Error publishing to social platforms";
            update.putExtra(PUBLISHING_ERROR, message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(update);
        }
    }

    private void doPublish(Review review) {
        ApplicationInstance app = ApplicationInstance.getInstance(getApplicationContext());
        TagsManager tagsManager = app.getTagsManager();

        Collection<SocialPlatform<?>> platforms = new ArrayList<>();
        for (SocialPlatform<?> platform : app.getSocialPlatformList()) {
            if (mPlatforms.contains(platform.getName())) platforms.add(platform);
        }

        BatchSocialPublisher publisher = new BatchSocialPublisher(platforms, this);
        publisher.publishReview(review, tagsManager);
    }

    @Override
    public void onCollectionFetched(Collection<Review> reviews, RepositoryError error) {

    }
}
