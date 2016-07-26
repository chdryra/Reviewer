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
import com.chdryra.android.reviewer.Authentication.Implementation.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.SessionProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
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
    private NamedAuthor mAuthor;
    private UserAccount mAccount;
    private SessionObserver mObserver;

    public UserSessionDefault(PresenterContext appContext) {
        mAppContext = appContext;
        UserAuthenticator authenticator = mAppContext.getUsersManager().getAuthenticator();
        onUserStateChanged(null,  authenticator.getAuthenticatedUser());
        authenticator.registerObserver(this);
    }

    @Override
    @Nullable
    public AuthenticatedUser getSessionUser() {
        return mAppContext.getUsersManager().getAuthenticator().getAuthenticatedUser();
    }


    @Override
    public AuthorId getSessionAuthorId() {
        return mAccount.getAccountHolderAsAuthorId();
    }


    @Override
    public NamedAuthor getSessionAuthor() {
        return mAuthor;
    }

    @Override
    public boolean setSessionObserver(SessionObserver observer) {
        mObserver = observer;
        if(mAccount != null) {
            setUserAccount();
            return true;
        }

        return false;
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
        mAccount = null;

        if (oldUser == null && newUser == null) {
            notifyOnSession(null, NO_USER_ERROR);
            return;
        }

        setUserAccount();
    }

    private void setUserAccount() {
        mAppContext.getUsersManager().getCurrentUsersAccount(new UserAccounts.GetAccountCallback() {
            @Override
            public void onAccount(UserAccount account, @Nullable AuthenticationError error) {
                if(error == null) {
                    mAccount.getAuthorProfile(new UserAccount.GetAuthorProfileCallback() {
                        @Override
                        public void onAuthorProfile(AuthorProfile profile, @Nullable AuthenticationError error) {
                            if (error == null) {
                                mAuthor = profile.getAuthor();
                                mAppContext.getReviewsFactory()
                                        .setAuthorsStamp(new AuthorsStamp(mAuthor));
                                notifyOnSession(mAccount, null);
                            } else {
                                notifyOnSession(null, error);
                            }
                        }
                    });
                }

            }
        });
    }

    @Override
    public UserAccount getUserAccount() {
        return mAccount;
    }

    private void notifyOnSession(@Nullable UserAccount account,
                                 @Nullable AuthenticationError error) {
        if (mObserver != null) mObserver.onLogIn(account, error);
    }
}
