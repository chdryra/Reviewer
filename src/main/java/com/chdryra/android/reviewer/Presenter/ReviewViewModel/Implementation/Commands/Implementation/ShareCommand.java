/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
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
    private RepositorySuite mRepo;
    private CurrentScreen mScreen;
    private ReviewId mReviewId;
    private SocialPublisher mSharer;
    private TagsManager mTagsManager;

    public ShareCommand(ReviewId reviewId, RepositorySuite repo, CurrentScreen screen, SocialPublisher sharer, TagsManager tagsManager) {
        mRepo = repo;
        mScreen = screen;
        mReviewId = reviewId;
        mSharer = sharer;
        mTagsManager = tagsManager;
    }

    @Override
    void execute() {
        mRepo.getReview(mReviewId, fetchAndShare());
    }

    private RepositoryCallback fetchAndShare() {
        return new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                Review review = result.getReview();
                if (!result.isError() && review != null) {
                    mSharer.publish(review, mTagsManager);
                } else {
                    String message = Strings.Toasts.REVIEW_NOT_FOUND;
                    if (result.isError()) message += ": " + result.getMessage();
                    mScreen.showToast(message);
                }
                onExecutionComplete();
            }
        };
    }
}