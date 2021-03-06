/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.startouch.Social.Interfaces.SocialPublisherAsync;
import com.chdryra.android.startouch.Social.Interfaces.SocialPublisherListener;
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
public class PublisherFacebook implements SocialPublisherAsync {
    private final String mPlatformName;
    private final ReviewSummariser mSummariser;
    private final ReviewFormatter mFormatter;

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
    public void publishAsync(Review review, SocialPublisherListener listener) {
        ReviewSummary summary = mSummariser.summarise(review);
        FormattedReview formatted = mFormatter.format(summary);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(ApplicationInstance.APP_SITE))
                .setContentTitle(formatted.getTitle())
                .setContentDescription(formatted.getBody())
                .build();

        ShareApi api = new ShareApi(content);
        if (api.canShare()) api.share(getShareCallback(listener));
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
