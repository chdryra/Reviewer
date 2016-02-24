/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;


/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherFacebook extends SocialPublisherBasic<AccessToken> {
    public static final String NAME = "facebook";
    private static final PublishResults SUCCESS = new PublishResults(NAME, 0);
    private AccessToken mToken;

    public PublisherFacebook(ReviewSummariser summariser, ReviewFormatter formatter) {
        super(NAME, summariser, formatter);
    }

    @Override
    protected PublishResults publish(FormattedReview review) {
        return SUCCESS;
    }

    @Override
    public void getFollowers(final FollowersListener listener) {
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                mToken, new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray objects, GraphResponse response) {
                        parseFriends(objects, response, listener);
                    }
                }
        );
        request.executeAsync();
    }

    @Override
    public void setAccessToken(AccessToken token) {
        mToken = token;
    }

    private void parseFriends(JSONArray objects, GraphResponse response, FollowersListener listener) {
        listener.onNumberFollowers(objects.length());
    }
}
