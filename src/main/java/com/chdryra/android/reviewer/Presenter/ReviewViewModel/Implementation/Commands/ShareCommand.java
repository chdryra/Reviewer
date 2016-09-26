/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareCommand extends Command {
    private ApplicationInstance mApp;
    private ReviewId mReviewId;
    private SocialPublisher mSharer;

    public ShareCommand(int requestCode,
                        ExecutionListener listener,
                        ApplicationInstance app,
                        ReviewId reviewId,
                        SocialPublisher sharer) {
        super(requestCode, listener);
        mApp = app;
        mReviewId = reviewId;
        mSharer = sharer;
    }

    @Override
    public void execute() {
        mApp.getReview(mReviewId, fetchAndShare(mApp.getTagsManager()));
    }

    private RepositoryCallback fetchAndShare(final TagsManager tagsManager) {
        return new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                Review review = result.getReview();
                if (!result.isError() && review != null) {
                    mSharer.publish(review, tagsManager);
                } else {
                    String message = Strings.Toasts.REVIEW_NOT_FOUND;
                    if (result.isError()) message += ": " + result.getMessage();
                    mApp.getCurrentScreen().showToast(message);
                }
                onExecutionComplete();
            }
        };
    }
}
