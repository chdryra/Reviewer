/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.MenuItem;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Authentication.Implementation.NullSocialProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.R;

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
    private SocialProfileRef.FollowUnfollow mActionType;
    private Binder mBinder;

    public MaiFollow(AuthorId authorId) {
        mAuthorId = authorId;
        mBinder = new Binder();
    }

    @Override
    public void doAction(MenuItem item) {
        String toast = mActionType == SocialProfileRef.FollowUnfollow.FOLLOW ?
                Strings.Toasts.FOLLOWING : Strings.Toasts.UNFOLLOWING;
        getCurrentScreen().showToast(toast);

        getProfile().followUnfollow(mAuthorId, mActionType, new SocialProfileRef.FollowCallback() {
            @Override
            public void onFollowingCallback(AuthorId authorId, SocialProfileRef.FollowUnfollow type,
                                            CallbackMessage message) {
                String onCompletion = message.isError() ?
                        message.getMessage() : Strings.Toasts.DONE;
                getCurrentScreen().showToast(onCompletion);
            }
        });
    }

    @Override
    public void onInflateMenu() {
        if(!isAttached()) return;

        MenuItem menuItem = getMenuItem();
        AuthorId userId = getApp().getAccounts().getUserSession().getAuthorId();
        if(menuItem != null && userId.toString().equals(mAuthorId.toString())) {
            menuItem.setVisible(false);
        } else {
            setFollow();
            getProfile().getFollowing().bindToItems(mBinder);
        }
    }

    @Override
    public void onDetachReviewView() {
        getProfile().getFollowing().unbindFromItems(mBinder);
    }

    private SocialProfileRef getProfile() {
        UserSession session = getApp().getAccounts().getUserSession();
        return session.isInSession() ? session.getAccount().getSocialProfile() : new NullSocialProfileRef();
    }

    private boolean isAuthor(AuthorId value) {
        return value.toString().equals(mAuthorId.toString());
    }

    private void setFollow() {
        mActionType = SocialProfileRef.FollowUnfollow.FOLLOW;
        MenuItem item = getMenuItem();
        if (item != null) item.setIcon(FOLLOW_ICON);
    }

    private void setUnfollow() {
        mActionType = SocialProfileRef.FollowUnfollow.UNFOLLOW;
        MenuItem item = getMenuItem();
        if (item != null) item.setIcon(UNFOLLOW_ICON);
    }

    private class Binder implements CollectionBinder<AuthorId> {
        @Override
        public void onItemAdded(AuthorId item) {
            if (isAuthor(item)) setUnfollow();
        }

        @Override
        public void onItemRemoved(AuthorId item) {
            if (isAuthor(item)) setFollow();
        }

        @Override
        public void onCollectionChanged(Collection<AuthorId> newItems) {
            setFollow();
            for (AuthorId item : newItems) {
                if (isAuthor(item)) setUnfollow();
            }
        }

        @Override
        public void onInvalidated(CollectionReference<AuthorId, ?, ?> reference) {
            setFollow();
        }
    }
}
