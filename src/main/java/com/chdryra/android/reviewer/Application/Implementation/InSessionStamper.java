/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.NullAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewStamper;

/**
 * Created by: Rizwan Choudrey
 * On: 06/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class InSessionStamper implements UserSession.SessionObserver, ReviewStamper {
    private static final AuthorId NULL_AUTHOR = NullAuthor.AUTHOR.getAuthorId();

    private AuthorId mAuthorId;

    public InSessionStamper() {
        mAuthorId = NULL_AUTHOR;
    }

    @Override
    public ReviewStamp newStamp() {
        return ReviewStamp.newStamp(mAuthorId);
    }

    @Override
    public void onLogIn(@Nullable UserAccount account, @Nullable AuthenticationError error) {
        if (error == null && account != null) mAuthorId = account.getAuthorId();
    }

    @Override
    public void onLogOut(UserAccount account, CallbackMessage message) {
        if (message.isOk()) mAuthorId = NULL_AUTHOR;
    }
}
