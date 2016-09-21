/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.R;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiFollow<T extends GvData> extends MenuActionItemBasic<T> {
    private static final int FOLLOW_ICON = R.drawable.ic_menu_invite;
    private static final int UNFOLLOW_ICON = R.drawable.ic_menu_blocked_user;

    private AuthorId mAuthorId;
    private SocialProfile.FollowUnfollow mActionType;
    private Binder mBinder;

    public MaiFollow(AuthorId authorId) {
        mAuthorId = authorId;
        mBinder = new Binder();
    }

    @Override
    public void doAction(MenuItem item) {
        final ApplicationInstance app = getParent().getApp();
        String toast = mActionType == SocialProfile.FollowUnfollow.FOLLOW ?
                Strings.Toasts.FOLLOWING : Strings.Toasts.UNFOLLOWING;
        app.getCurrentScreen().showToast(toast);

        SocialProfile profile = app.getSocialProfile();
        profile.followUnfollow(mAuthorId, mActionType, new SocialProfile.FollowCallback() {
            @Override
            public void onFollowingCallback(AuthorId authorId, SocialProfile.FollowUnfollow type,
                                            CallbackMessage message) {
                String onCompletion = message.isError() ?
                        message.getMessage() : Strings.Toasts.Done;
                app.getCurrentScreen().showToast(onCompletion);
            }
        });
    }

    @Override
    public void onInflateMenu() {
        setFollow();
        SocialProfile profile = getParent().getApp().getSocialProfile();
        profile.getFollowing().bindToItems(mBinder);
    }

    @Override
    public void onDetachReviewView() {
        SocialProfile profile = getParent().getApp().getSocialProfile();
        profile.getFollowing().unbindFromItems(mBinder);
    }

    private boolean isAuthor(AuthorId value) {
        return value.toString().equals(mAuthorId.toString());
    }

    private void setFollow() {
        mActionType = SocialProfile.FollowUnfollow.FOLLOW;
        MenuItem item = getMenuItem();
        if (item != null) item.setIcon(FOLLOW_ICON);
    }

    private void setUnfollow() {
        mActionType = SocialProfile.FollowUnfollow.UNFOLLOW;
        MenuItem item = getMenuItem();
        if (item != null) item.setIcon(UNFOLLOW_ICON);
    }

    @Nullable
    private MenuItem getMenuItem() {
        return getParent().getItem(this);
    }

    private class Binder implements ListItemBinder<AuthorId> {
        @Override
        public void onItemAdded(AuthorId value) {
            if (isAuthor(value)) setUnfollow();
        }

        @Override
        public void onItemRemoved(AuthorId value) {
            if (isAuthor(value)) setFollow();
        }

        @Override
        public void onListChanged(Collection<AuthorId> newItems) {
            setFollow();
            for (AuthorId item : newItems) {
                if (isAuthor(item)) setUnfollow();
            }
        }

        @Override
        public void onInvalidated(ListReference<AuthorId, ?> reference) {
            setFollow();
        }
    }
}
