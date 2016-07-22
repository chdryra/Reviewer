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
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.SessionProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;

/**
 * Created by: Rizwan Choudrey
 * On: 16/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserSessionDefault implements UserSession{
    private static final AuthenticationError NO_USER_ERROR = new AuthenticationError
            (ApplicationInstance.APP_NAME, AuthenticationError.Reason.NO_AUTHENTICATED_USER);

    private PresenterContext mAppContext;
    private AuthenticatedUser mUser;
    private AuthorProfile mProfile;
    private LoginObserver mObserver;

    public UserSessionDefault(PresenterContext appContext) {
        mAppContext = appContext;

        UserAuthenticator authenticator = mAppContext.getUsersManager().getAuthenticator();
        onUserStateChanged(null,  authenticator.getAuthenticatedUser());
        authenticator.registerObserver(this);
    }

    @Override
    public boolean hasUser() {
        return mAppContext.getUsersManager().getAuthenticator().getAuthenticatedUser() != null;
    }

    @Override
    public boolean setLoginObserver(LoginObserver observer) {
        mObserver = observer;
        if(mUser != null) {
            getUserProfile();
            return true;
        }

        return false;
    }

    @Override
    public DataAuthor getCurrentUserAsAuthor() {
        return mProfile.getAuthor();
    }

    @Override
    public void loginComplete() {
        mObserver = null;
    }

    @Override
    public void logout(SessionProvider.LogoutCallback callback, SessionProvider<?> googleHack) {
        //From Firebase
        mAppContext.getUsersManager().logoutCurrentUser();
        //From twitter/fb (also if used for login)
        mAppContext.getSocialPlatformList().logout();
        //Google needs its own method
        googleHack.logout(callback); //ensures
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

        getUserProfile();
    }

    @Override
    public void getUserProfile() {
        mAppContext.getUsersManager().getCurrentUsersProfile(new UserAccounts.GetProfileCallback() {
            @Override
            public void onProfile(AuthenticatedUser user, AuthorProfile profile, @Nullable
            AuthenticationError error) {
                if (error == null) setAuthor(user, profile);
                notifyLogin(user, profile, error);
            }
        });
    }

    private void setAuthor(AuthenticatedUser user, AuthorProfile profile) {
        mAppContext.getReviewsFactory().setAuthorsStamp(new AuthorsStamp(profile.getAuthor()));
        mUser = user;
        mProfile = profile;
    }

    private void notifyLogin(@Nullable AuthenticatedUser user,
                             @Nullable AuthorProfile profile,
                             @Nullable AuthenticationError error) {
        if (mObserver != null) mObserver.onLoggedIn(user, profile, error);
    }
}
