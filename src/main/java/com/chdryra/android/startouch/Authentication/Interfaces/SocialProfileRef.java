/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasAuthorId;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorListRef;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialProfileRef extends HasAuthorId, DataReference<SocialProfile>{
    enum FollowUnfollow {FOLLOW, UNFOLLOW;}

    interface FollowCallback {
        void onFollow(AuthorId authorId, FollowUnfollow type, CallbackMessage message);
    }

    interface IsFollowingCallback {
        void onIsFollowing(AuthorId authorId, boolean isFollowing, CallbackMessage message);
    }

    AuthorListRef getFollowing();

    AuthorListRef getFollowers();

    void isFollowing(AuthorId authorId, IsFollowingCallback callback);

    void followUnfollow(AuthorId toFollow, FollowUnfollow type, FollowCallback callback);
}
