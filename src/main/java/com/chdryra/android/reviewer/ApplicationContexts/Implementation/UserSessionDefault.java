/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.NullUserAccount;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.SessionProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;

import java.util.ArrayList;

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
    private ArrayList<SessionObserver> mObservers;

    public UserSessionDefault(PresenterContext appContext) {
        mAppContext = appContext;
        mObservers = new ArrayList<>();
        UserAuthenticator authenticator = mAppContext.getUsersManager().getAuthenticator();
        authenticator.registerObserver(this);
        onUserStateChanged(null, authenticator.getAuthenticatedUser());
    }

    @Override
    public boolean isAuthenticated() {
        return mAppContext.getUsersManager().getAuthenticator().getAuthenticatedUser() != null;
    }

    @Override
    public boolean isInSession() {
        return mAccount != null;
    }

    @Override
    public AuthorId getAuthorId() {
        return isInSession() ? mAccount.getAuthorId() : new DatumAuthorId();
    }

    @Override
    public UserAccount getAccount() {
        return isInSession() ? mAccount : new NullUserAccount();
    }

    @Override
    public void registerSessionObserver(SessionObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
        if (mAccount != null) notifyOnSession(mAccount, null);
    }

    @Override
    public void unregisterSessionObserver(SessionObserver observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @Override
    public void logout(SessionProvider<?> googleHack) {
        //From Firebase
        mAppContext.getUsersManager().logoutCurrentUser();
        //From twitter/fb (also if used for login)
        mAppContext.getSocialPlatformList().logout();
        //Google needs its own method
        googleHack.logout(new SessionProvider.LogoutCallback() {
            @Override
            public void onLoggedOut(CallbackMessage message) {
                notifySessionEnded(message);
            }
        });
    }

    @Override
    public void onUserStateChanged(@Nullable AuthenticatedUser oldUser, @Nullable
    AuthenticatedUser newUser) {
        if (oldUser != null && newUser != null && oldUser.equals(newUser)) return;

        mAccount = null;
        if (oldUser == null && newUser == null) {
            notifyOnSession(null, NO_USER_ERROR);
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
                    if(!mAccount.getAuthorId().equals(account.getAuthorId())) {
                        notifySessionEnded(CallbackMessage.ok());
                    }
                    mAccount = account;
                    mAppContext.getReviewsFactory().setAuthorsStamp(new AuthorsStamp(getAuthorId
                            ()));
                }

                notifyOnSession(account, error);
            }
        });
    }

    private void notifySessionEnded(CallbackMessage message) {
        for (SessionObserver observer : mObservers) {
            observer.onLogOut(mAccount, message);
        }
    }

    private void notifyOnSession(@Nullable UserAccount account,
                                 @Nullable AuthenticationError error) {
        for (SessionObserver observer : mObservers) {
            observer.onLogIn(account, error);
        }
    }
}
