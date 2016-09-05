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
import com.chdryra.android.reviewer.Authentication.Interfaces.SessionProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;

/**
 * Created by: Rizwan Choudrey
 * On: 16/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserSessionDefault implements UserSession {
    private static final AuthenticationError NO_USER_ERROR = new AuthenticationError
            (ApplicationInstance.APP_NAME, AuthenticationError.Reason.NO_AUTHENTICATED_USER);

    private final PresenterContext mAppContext;
    private UserAccount mAccount;
    private SessionObserver mObserver;

    public UserSessionDefault(PresenterContext appContext) {
        mAppContext = appContext;
        UserAuthenticator authenticator = mAppContext.getUsersManager().getAuthenticator();
        authenticator.registerObserver(this);
        onUserStateChanged(null, authenticator.getAuthenticatedUser());
    }

    @Override
    public boolean isAuthenticated() {
        return mAppContext.getUsersManager().getAuthenticator().getAuthenticatedUser() != null;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAccount.getAuthorId();
    }

    @Override
    public void setSessionObserver(SessionObserver observer) {
        mObserver = observer;
        if (mAccount != null) notifyOnSession(mAccount, null);
    }

    @Override
    public void unsetSessionObserver() {
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
        if(oldUser != null && newUser != null && oldUser.equals(newUser)) return;

        mAccount = null;
        if (oldUser == null && newUser == null) {
            notifyOnSession(mAccount, NO_USER_ERROR);
        } else {
            refreshSession();
        }
    }

    @Override
    public void refreshSession() {
        mAppContext.getUsersManager().getCurrentUsersAccount(new UserAccounts.GetAccountCallback() {
            @Override
            public void onAccount(UserAccount account, @Nullable AuthenticationError error) {
                if (error == null) {
                    mAccount = account;
                    mAppContext.getReviewsFactory().setAuthorsStamp(new AuthorsStamp(getAuthorId()));
                }

                notifyOnSession(account, error);
            }
        });
    }

    private void notifyOnSession(@Nullable UserAccount account,
                                 @Nullable AuthenticationError error) {
        if (mObserver != null) mObserver.onLogIn(account, error);
    }
}
