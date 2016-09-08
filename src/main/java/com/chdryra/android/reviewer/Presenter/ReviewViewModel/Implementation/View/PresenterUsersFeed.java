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
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisherListener;
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
public class PresenterUsersFeed extends PresenterReviewsList implements
        ReviewPublisherListener,
        ReviewNode.NodeObserver {

    private final PresenterListener mListener;

    public interface PresenterListener {
        void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults justUploaded);
    }

    private PresenterUsersFeed(ApplicationInstance app,
                               ReviewNodeRepo feedNode,
                               PresenterListener listener) {
        super(app, app.newReviewsListView(feedNode, true, true));
        getApp().getPublisher().registerListener(this);
        mListener = listener;
    }

    @Override
    public void detach() {
        super.detach();
        ((ReviewNodeRepo)getNode()).detachFromRepo();
        getApp().getPublisher().unregisterListener(this);
    }

    public void deleteReview(final ReviewId id) {
        makeToast(Strings.Toasts.DELETING);
        ReviewDeleter deleter = getApp().newReviewDeleter(id);
        deleter.deleteReview(new ReviewDeleter.ReviewDeleterCallback() {
            @Override
            public void onReviewDeleted(ReviewId reviewId, CallbackMessage result) {
                getApp().getTagsManager().clearTags(reviewId.toString());
                makeToast(result.getMessage());
            }
        });
    }

    private String getPublishedMessage(Collection<PublishResults> platformsOk,
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
    public void onUploadFailed(ReviewId id, CallbackMessage result) {
        makeToast(result.getMessage());
    }

    @Override
    public void onUploadCompleted(ReviewId id, CallbackMessage result) {
        makeToast(result.getMessage());
    }

    @Override
    public void onPublishingFailed(ReviewId reviewId, Collection<String> platforms, CallbackMessage
            result) {
        makeToast(result.getMessage());
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
        makeToast(getPublishedMessage(platformsOk, platformsNotOk, result));
    }

    private void makeToast(String message) {
        getApp().getCurrentScreen().showToast(message);
    }

    public static class Builder {
        private final ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public PresenterUsersFeed build(PresenterListener listener) {
            ReviewNodeRepo node = mApp.getReviewsFactory().createUsersFeed(mApp.getUserSession().getAuthorId(),
                    mApp.getUsersFeed(), mApp.getUsersManager().getAuthorsRepository());
            return new PresenterUsersFeed(mApp, node, listener);
        }
    }
}
