/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ProfileSocial extends HasAuthorId{
    enum FollowUnfollow {FOLLOW, UNFOLLOW}

    interface FollowCallback {
        void onFollowingCallback(AuthorId authorId, FollowUnfollow type, CallbackMessage message);
    }

    RefAuthorList getFollowing();

    RefAuthorList getFollowers();

    void followUnfollow(AuthorId authorId, FollowUnfollow type, FollowCallback callback);
}
