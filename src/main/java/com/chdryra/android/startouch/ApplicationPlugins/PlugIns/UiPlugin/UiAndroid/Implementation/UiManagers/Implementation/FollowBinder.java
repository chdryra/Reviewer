/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.view.View;
import android.widget.Button;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.CollectionReference;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorListRef;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 22/02/2018
 * Email: rizwan.choudrey@gmail.com
 */
public class FollowBinder implements CollectionReference.ItemSubscriber<AuthorId>,
        SocialProfileRef.FollowCallback, SocialProfileRef.IsFollowingCallback {
    private static final SocialProfileRef.FollowUnfollow UNFOLLOW
            = SocialProfileRef.FollowUnfollow.UNFOLLOW;
    private static final SocialProfileRef.FollowUnfollow FOLLOW
            = SocialProfileRef.FollowUnfollow.FOLLOW;

    private final AuthorId mCandidateId;
    private final SocialProfileRef mUsersProfile;
    private final AuthorListRef mFollowing;
    private final Button mFollowButton;

    public FollowBinder(AuthorId candidateId, SocialProfileRef usersProfile, Button followButton) {
        mCandidateId = candidateId;
        mUsersProfile = usersProfile;
        mFollowing = mUsersProfile.getFollowing();
        mFollowButton = followButton;
    }

    public void bind() {
        mFollowButton.setText(Strings.Buttons.FETCHING);
        mUsersProfile.isFollowing(mCandidateId, this);
    }

    public void unbind() {
        mFollowing.unsubscribe(this);
    }

    @Override
    public void onItemAdded(AuthorId item) {
        if (item.equals(mCandidateId)) setButton(UNFOLLOW);
    }

    @Override
    public void onItemRemoved(AuthorId item) {
        if (item.equals(mCandidateId)) setButton(FOLLOW);
    }

    @Override
    public void onCollectionChanged(Collection<AuthorId> newItems) {
        onItemRemoved(mCandidateId);
        for (AuthorId author : newItems) {
            onItemAdded(author);
        }
    }

    @Override
    public void onInvalidated(CollectionReference<AuthorId, ?, ?> reference) {
        setButton(FOLLOW);
    }

    @Override
    public void onFollow(AuthorId authorId,
                         SocialProfileRef.FollowUnfollow type,
                         CallbackMessage message) {
        if (message.isOk() && type.equals(FOLLOW)) {
            setButton(UNFOLLOW);
        } else {
            setButton(FOLLOW);
        }
    }

    @Override
    public void onIsFollowing(AuthorId authorId, boolean isFollowing, CallbackMessage message) {
        if (message.isOk()) {
            setButton(isFollowing ? UNFOLLOW : FOLLOW);
            mFollowing.subscribe(this);
        } else {
            mFollowButton.setText(message.getMessage());
        }
    }

    private void setButton(final SocialProfileRef.FollowUnfollow followUnfollow) {
        mFollowButton.setText(followUnfollow == UNFOLLOW ? Strings.Buttons.UNFOLLOW : Strings
                .Buttons.FOLLOW);
        mFollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsersProfile.followUnfollow(mCandidateId, followUnfollow, FollowBinder.this);
            }
        });
    }
}
