/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.SocialPublisherService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation.ReviewPublisher;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPublishingService extends IntentService
        implements BatchSocialPublisher.BatchSocialPublisherListener, ReviewPublisher.QueueCallback {
    public static final String REVIEW_ID = "SocialPublishingService.ReviewId";

    public static final String STATUS_UPDATE = "SocialPublishingService.StatusUpdate";
    public static final String STATUS_PERCENTAGE = "SocialPublishingService.Percentage";
    public static final String STATUS_RESULTS = "SocialPublishingService.PublishResults";

    public static final String PUBLISHING_COMPLETED = "SocialPublishingService.PublishFinished";
    public static final String PUBLISH_OK = "SocialPublishingService.PublishResultsOk";
    public static final String PUBLISH_NOT_OK = "SocialPublishingService.PublishResultsNotOk";
    public static final String RESULT = "SocialPublishingService.Result";

    private static final int PUBLISH_SUCCESSFUL = R.string.review_published;
    private static final int PUBLISH_ERROR = R.string.publishing_error;
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

        if (mReviewId != null && mPlatforms != null && mPlatforms.size() > 0) batchPublish();
    }

    @Override
    public void onStatusUpdate(double percentage, PublishResults results) {
        Intent update = new Intent(STATUS_UPDATE);
        update.putExtra(REVIEW_ID, mReviewId);
        update.putExtra(STATUS_PERCENTAGE, percentage);
        update.putExtra(STATUS_RESULTS, results);

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    @Override
    public void onPublished(Collection<PublishResults> publishedOk,
                            Collection<PublishResults> publishedNotOk) {
        CallbackMessage report = getReport(publishedOk, publishedNotOk);
        broadcastPublishingComplete(publishedOk, publishedNotOk, report);
    }

    @Override
    public void onAddedToQueue(ReviewId id, CallbackMessage message) {

    }

    @Override
    public void onFailed(@Nullable Review review, @Nullable ReviewId id, CallbackMessage message) {
        broadcastPublishingComplete(new ArrayList<PublishResults>(), new ArrayList
                <PublishResults>(), message);
    }

    @Override
    public void onRetrievedFromQueue(Review review, CallbackMessage message) {
        doPublish(review);
    }

    @NonNull
    private CallbackMessage getReport(Collection<PublishResults> publishedOk,
                                              Collection<PublishResults> publishedNotOk) {
        String report = "";
        if (publishedOk.size() > 0) {
            report += getApplicationContext().getString(PUBLISH_SUCCESSFUL) + ": ";
            for (PublishResults ok : publishedOk) {
                report += ok.getPublisherName() + " ";
            }

            if (publishedNotOk.size() > 0) report += "\n";
        }

        if (publishedNotOk.size() > 0) {
            report += getApplicationContext().getString(PUBLISH_ERROR) + ": ";
            for (PublishResults notOk : publishedNotOk) {
                report += notOk.getPublisherName() + "(" + notOk.getErrorIfFail() + ")" + " ";
            }
        }

        boolean totalError = publishedOk.size() == 0;
        return totalError ? CallbackMessage.error(report)
                : CallbackMessage.ok(report);
    }

    private void broadcastPublishingComplete(Collection<PublishResults> publishedOk,
                                             Collection<PublishResults> publishedNotOk,
                                             CallbackMessage result) {
        Intent update = new Intent(PUBLISHING_COMPLETED);
        update.putParcelableArrayListExtra(PUBLISH_OK, new ArrayList<>(publishedOk));
        update.putParcelableArrayListExtra(PUBLISH_NOT_OK, new ArrayList<>(publishedNotOk));
        update.putExtra(RESULT, result);
        update.putExtra(REVIEW_ID, mReviewId);

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    private void batchPublish() {
        ApplicationInstance app = ApplicationInstance.getInstance(getApplicationContext());
        app.getPublisher().getFromQueue(new DatumReviewId(mReviewId), this, this);
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
}
