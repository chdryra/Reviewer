/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterFeed extends PresenterReviewsList {
    protected PresenterFeed(ApplicationInstance app, ReviewTreeLive feedNode, boolean withMenu) {
        super(app, app.newReviewsListView(feedNode, withMenu));
    }

    @Override
    public void detach() {
        super.detach();
        ((ReviewTreeLive)getNode()).detachFromRepo();
    }

    public static class Builder {
        private ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        protected ApplicationInstance getApp() {
            return mApp;
        }

        public PresenterFeed build(DataAuthor author) {
            return new PresenterFeed(mApp, getFeedNode(author), false);
        }

        @NonNull
        protected ReviewTreeLive getFeedNode(DataAuthor author) {
            ReviewsFeed feed = mApp.getFeed(author);
            String title = author.getName() + "'s feed";
            return new ReviewTreeLive(feed, mApp.getReviewsFactory(), title);
        }
    }
}
