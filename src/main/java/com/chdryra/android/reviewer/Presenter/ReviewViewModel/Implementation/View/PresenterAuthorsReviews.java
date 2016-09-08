/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterAuthorsReviews extends PresenterReviewsList {
    PresenterAuthorsReviews(ApplicationInstance app, ReviewNodeRepo feedNode, boolean withMenu) {
        super(app, app.newReviewsListView(feedNode, withMenu, false));
    }

    @Override
    public void detach() {
        super.detach();
        ((ReviewNodeRepo)getNode()).detachFromRepo();
    }

    public static class Builder {
        private final ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public PresenterAuthorsReviews build(AuthorId authorId) {
            ReviewNodeRepo node = mApp.getReviewsFactory().createAuthorsTree(authorId,
                    mApp.getReviews(authorId), mApp.getUsersManager().getAuthorsRepository());
            return new PresenterAuthorsReviews(mApp, node, false);
        }
    }
}
