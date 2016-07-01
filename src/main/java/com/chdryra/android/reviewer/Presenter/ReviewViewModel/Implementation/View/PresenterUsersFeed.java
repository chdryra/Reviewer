/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleterListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces
        .ReviewPublisherListener;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterUsersFeed extends PresenterFeed implements
        ReviewPublisherListener,
        ReviewDeleter.ReviewDeleterCallback,
        ReviewNode.NodeObserver {

    private PresenterListener mListener;
    private ReviewDeleter mDeleter;

    public interface PresenterListener extends ReviewPublisherListener, ReviewDeleterListener {
        @Override
        void onReviewDeleted(ReviewId reviewId, CallbackMessage result);

        @Override
        void onUploadFailed(ReviewId id, CallbackMessage result);

        @Override
        void onUploadCompleted(ReviewId id, CallbackMessage result);

        @Override
        void onPublishingFailed(ReviewId reviewId, Collection<String> platforms, CallbackMessage
                result);

        @Override
        void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults justUploaded);

        @Override
        void onPublishingCompleted(ReviewId reviewId, Collection<PublishResults> platformsOk,
                                   Collection<PublishResults> platformsNotOk, CallbackMessage
                                           result);
    }

    private PresenterUsersFeed(ApplicationInstance app,
                               ReviewTreeRepo feedNode,
                               PresenterListener listener) {
        super(app, feedNode, true);
        getApp().getPublisher().registerListener(this);
        mListener = listener;
    }

    public void deleteReview(final ReviewId id) {
        mDeleter = getApp().newReviewDeleter(id);
        mDeleter.deleteReview(this);
    }

    public String getPublishedMessage(Collection<PublishResults> platformsOk,
                                      Collection<PublishResults> platformsNotOk,
                                      CallbackMessage callbackMessage) {
        int numFollowers = 0;
        ArrayList<String> ok = new ArrayList<>();
        for (PublishResults result : platformsOk) {
            ok.add(result.getPublisherName());
            numFollowers += result.getFollowers();
        }

        ArrayList<String> notOk = new ArrayList<>();
        for (PublishResults result : platformsNotOk) {
            notOk.add(result.getPublisherName());
        }

        String message = "";

        if (ok.size() > 0) {
            String num = String.valueOf(numFollowers);
            boolean fb = notOk.contains(PlatformFacebook.NAME);
            String plus = fb ? "+ " : "";
            String followers = numFollowers == 1 && !fb ? " follower" : " followers";
            String followersString = num + plus + followers;

            message = "Published to " + followersString + " on " +
                    StringUtils.join(ok.toArray(), ", ");
        }

        String notOkMessage = "";
        if (notOk.size() > 0) {
            notOkMessage = "Problems publishing to " + StringUtils.join(platformsNotOk.toArray(),
                    ",");
            if (ok.size() > 0) message += "\n" + notOkMessage;
        }

        if (callbackMessage.isError()) {
            message += "\nError: " + callbackMessage.getMessage();
        }

        return message;
    }

    @Override
    public void detach() {
        super.detach();
        getApp().getPublisher().unregisterListener(this);
    }

    @Override
    public void onUploadFailed(ReviewId id, CallbackMessage result) {
        mListener.onUploadFailed(id, result);
    }

    @Override
    public void onUploadCompleted(ReviewId id, CallbackMessage result) {
        mListener.onUploadCompleted(id, result);
    }

    @Override
    public void onPublishingFailed(ReviewId reviewId, Collection<String> platforms, CallbackMessage
            result) {
        mListener.onPublishingFailed(reviewId, platforms, result);
    }

    @Override
    public void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults
            justUploaded) {
        mListener.onPublishingStatus(reviewId, percentage, justUploaded);
    }

    @Override
    public void onPublishingCompleted(ReviewId reviewId, Collection<PublishResults> platformsOk,
                                      Collection<PublishResults> platformsNotOk, CallbackMessage
                                              result) {
        mListener.onPublishingCompleted(reviewId, platformsOk, platformsNotOk, result);
    }

    @Override
    public void onReviewDeleted(ReviewId reviewId, CallbackMessage result) {
        getApp().getTagsManager().clearTags(reviewId.toString());
        mListener.onReviewDeleted(reviewId, result);
    }

    public static class Builder extends PresenterFeed.Builder {
        public Builder(ApplicationInstance app) {
            super(app);
        }

        public PresenterUsersFeed build(PresenterListener listener) {
            DataAuthor author = getApp().getUserSession().getCurrentUserAsAuthor();
            return new PresenterUsersFeed(getApp(), getFeedNode(author), listener);
        }
    }
}
