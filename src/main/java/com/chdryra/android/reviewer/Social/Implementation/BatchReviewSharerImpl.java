/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.BatchReviewSharer;
import com.chdryra.android.reviewer.Social.Interfaces.BatchReviewSharerListener;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BatchReviewSharerImpl implements BatchReviewSharer {
    private BatchReviewSharerListener mListener;

    public BatchReviewSharerImpl(BatchReviewSharerListener listener) {
        mListener = listener;
    }

    @Override
    public void shareReview(Review review, TagsManager tagsManager, Collection<SocialPlatform<?>> platforms) {
        if(platforms.size() == 0) return;
        new BatchSocialPublisher(platforms).publish(review, tagsManager, this);
    }

    @Override
    public void onPublished(Collection<PublishResults> results) {
        ArrayList<String> platformsOk = new ArrayList<>();
        ArrayList<String> platformsNotOk = new ArrayList<>();
        int numFollowers = 0;
        for(PublishResults result : results) {
            if(result.wasSuccessful()) {
                platformsOk.add(result.getPublisherName());
                numFollowers += result.getFollowers();
            } else {
                platformsNotOk.add(result.getPublisherName());
            }
        }

        mListener.onPublished(makeMessage(platformsOk, platformsNotOk, numFollowers));
    }

    @NonNull
    private String makeMessage(ArrayList<String> platformsOk,
                               ArrayList<String> platformsNotOk,
                               int numFollowers) {
        String message = "";

        if(platformsOk.size() > 0) {
            String num = String.valueOf(numFollowers);
            boolean fb = platformsOk.contains(PlatformFacebook.NAME);
            String plus = fb ? "+ " : "";
            String followers = numFollowers == 1 && !fb ? " follower" : " followers";
            String followersString = num + plus + followers;

            message = "Published to " + followersString + " on " +
                    StringUtils.join(platformsOk.toArray(), ", ");
        }

        String notOkMessage = "";
        if(platformsNotOk.size() > 0) {
            notOkMessage = "Problems publishing to " + StringUtils.join(platformsNotOk.toArray(), ",");
            if(platformsOk.size() > 0) message += "\n" + notOkMessage;
        }

        return message;
    }
}
