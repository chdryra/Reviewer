/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;


/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherFacebook implements SocialPlatformsPublisher {
    private String mPlatformName;
    private ReviewSummariser mSummariser;
    private ReviewFormatter mFormatter;

    public PublisherFacebook(String platformName, ReviewSummariser summariser,
                             ReviewFormatter formatter) {
        mPlatformName = platformName;
        mSummariser = summariser;
        mFormatter = formatter;
    }

    @Override
    public String getPlatformName() {
        return mPlatformName;
    }

    @Override
    public void publishAsync(Review review, TagsManager tagsManager,
                             final SocialPublisherListener listener) {
        ReviewSummary summary = mSummariser.summarise(review, tagsManager);
        FormattedReview formatted = mFormatter.format(summary);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(getAppLink())
                .setContentTitle(formatted.getTitle())
                .setContentDescription(formatted.getBody())
                .build();

        ShareApi api = new ShareApi(content);
        if(api.canShare()) api.share(getShareCallback(listener));
    }

    private Uri getAppLink() {
        return Uri.parse("http://www.teeqr.com");
    }

    @NonNull
    private FacebookCallback<Sharer.Result> getShareCallback(final SocialPublisherListener
                                                                     listener) {
        return new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                listener.onPublished(new PublishResults(getPlatformName(), 0));
            }

            @Override
            public void onCancel() {
                listener.onPublished(new PublishResults(getPlatformName(), "Canceled"));
            }

            @Override
            public void onError(FacebookException error) {
                listener.onPublished(new PublishResults(getPlatformName(), error.toString()));
            }
        };
    }
}
