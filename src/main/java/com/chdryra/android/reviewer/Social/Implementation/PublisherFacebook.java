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

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;

import org.json.JSONArray;


/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherFacebook implements SocialPublisher<AccessToken> {
    public static final String NAME = "facebook";

    private ReviewSummariser mSummariser;
    private ReviewFormatter mFormatter;
    private AccessToken mToken;

    public PublisherFacebook(ReviewSummariser summariser, ReviewFormatter formatter) {
        mSummariser = summariser;
        mFormatter = formatter;
    }

    @Override
    public String getPlatformName() {
        return NAME;
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

        ShareApi.share(content, getShareCallback(listener));
    }

    @Override
    public void getFollowersAsync(final FollowersListener listener) {
        //Pointless as facebook only returns friends who have also given their permission
        // to the app via Facebook Login.
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                mToken, new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray objects, GraphResponse response) {
                        listener.onNumberFollowers(objects.length());
                    }
                }
        );

        request.executeAsync();
    }

    @Override
    public void setAccessToken(AccessToken token) {
        mToken = token;
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
                listener.onPublished(new PublishResults(NAME, 0));
            }

            @Override
            public void onCancel() {
                listener.onPublished(new PublishResults(NAME, "Canceled"));
            }

            @Override
            public void onError(FacebookException error) {
                listener.onPublished(new PublishResults(NAME, error.toString()));
            }
        };
    }
}
