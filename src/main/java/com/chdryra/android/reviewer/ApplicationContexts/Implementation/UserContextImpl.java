/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserContext;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;

/**
 * Created by: Rizwan Choudrey
 * On: 16/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserContextImpl implements UserContext {
    private static final AuthenticationError NO_USER_ERROR = new AuthenticationError
            (ApplicationInstance.APP_NAME, AuthenticationError.Reason.NO_AUTHENTICATED_USER);
    private static final AuthenticationError NO_PROFILE_ERROR = new AuthenticationError
            (ApplicationInstance.APP_NAME, AuthenticationError.Reason.UNKNOWN_USER);

    private AuthenticatedUser mUser;
    private AuthorProfile mProfile;
    private PresenterContext mContext;
    private LoginObserver mObserver;


    public UserContextImpl(ApplicationContext context) {
        mContext = context.getContext();

        UserAuthenticator authenticator = mContext.getUsersManager().getAuthenticator();
        onUserStateChanged(null, authenticator.getAuthenticatedUser());
        authenticator.registerObserver(this);
    }

    @Override
    public void unsetLoginObserver() {
        mObserver = null;
    }

    @Override
    public void setLoginObserver(LoginObserver observer) {
        mObserver = observer;
        observeCurrentUser();
    }

    @Override
    public void observeCurrentUser() {
        if (mUser == null || mProfile == null) {
            notifyLogin(mUser, mProfile, mUser == null ? NO_USER_ERROR : NO_PROFILE_ERROR);
        } else {
            notifyLogin(mUser, mProfile, null);
        }
    }

    @Override
    public DataAuthor getCurrentUserAsAuthor() {
        return mProfile.getAuthor();
    }

    @Override
    public void logout() {
        mContext.getUsersManager().logoutCurrentUser();
        mContext.getSocialPlatformList().logout();
    }

    @Override
    public boolean getCurrentProfile(final UserAccounts.GetProfileCallback callback) {
        return mContext.getUsersManager().getCurrentUsersProfile(callback);
    }

    @Override
    public void onUserStateChanged(@Nullable AuthenticatedUser oldUser, @Nullable
    AuthenticatedUser newUser) {
        mUser = newUser;
        mProfile = null;

        if (oldUser == null && newUser == null) {
            notifyLogin(null, null, NO_USER_ERROR);
            return;
        }

        getCurrentProfile(new UserAccounts.GetProfileCallback() {
            @Override
            public void onProfile(AuthenticatedUser user, AuthorProfile profile, @Nullable
            AuthenticationError error) {
                if (error == null) setAuthor(user, profile);
                notifyLogin(user, profile, error);
            }
        });
    }

    private void setAuthor(AuthenticatedUser user, AuthorProfile profile) {
        mContext.getReviewsFactory().setAuthorsStamp(new AuthorsStamp(profile.getAuthor()));
        mUser = user;
        mProfile = profile;
    }

    private void notifyLogin(@Nullable AuthenticatedUser user,
                             @Nullable AuthorProfile profile,
                             @Nullable AuthenticationError error) {
        if (mObserver != null) mObserver.onLoggedIn(user, profile, error);
    }
}
