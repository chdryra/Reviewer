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
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Implementation.RepoResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareCommand extends Command {
    private final ReviewId mReviewId;
    private final ReviewsRepoReadable mRepo;
    private final CurrentScreen mScreen;
    private final SocialPublisher mSharer;

    public ShareCommand(ReviewId reviewId,
                        ReviewsRepoReadable repo,
                        CurrentScreen screen,
                        SocialPublisher sharer) {
        super(Strings.Commands.SHARE);
        mRepo = repo;
        mScreen = screen;
        mReviewId = reviewId;
        mSharer = sharer;
    }

    @Override
    public void execute() {
        mRepo.getReview(mReviewId, fetchAndShare());
    }

    private RepoCallback fetchAndShare() {
        return new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                if (result.isReview()) {
                    mSharer.publish(result.getReview());
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
