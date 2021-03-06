/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.NullUserAccount;
import com.chdryra.android.startouch.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginProvider;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccount;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.Social.Implementation.SocialPlatformList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserSessionDefault implements UserSession, UserAccounts.GetAccountCallback {
    private static final AuthenticationError NO_USER_ERROR = new AuthenticationError
            (ApplicationInstance.APP_NAME, AuthenticationError.Reason.NO_AUTHENTICATED_USER);

    private final AccountsManager mManager;
    private final SocialPlatformList mSocialPlatforms;
    private final ArrayList<SessionObserver> mObservers;

    private UserAccount mAccount;

    public UserSessionDefault(AccountsManager usersManager,
                              SocialPlatformList socialPlatforms) {
        mManager = usersManager;
        mSocialPlatforms = socialPlatforms;

        mObservers = new ArrayList<>();
        getAuthenticator().registerObserver(this);

        onUserStateChanged(null, getAuthenticator().getAuthenticatedUser());
    }

    @Override
    public boolean isAuthenticated() {
        return getAuthenticator().getAuthenticatedUser() != null;
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
    public AuthenticatedUser getUser() {
        return isInSession() ? mAccount.getAccountHolder() : new AuthenticatedUser();
    }

    @Override
    public UserAccount getAccount() {
        return isInSession() ? mAccount : nullAccount();
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
    public void logout(LoginProvider<?> googleHack) {
        //From Firebase
        getAuthenticator().logout();
        //From twitter/fb (also if used for login)
        mSocialPlatforms.logout();
        //Google needs its own method
        googleHack.logout(new LoginProvider.LogoutCallback() {
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
        AuthenticatedUser user = getAuthenticator().getAuthenticatedUser();
        if (user != null) {
            mManager.getAccounts().getAccount(user, this);
        } else {
            notifyOnSession(nullAccount(),
                    new AuthenticationError(ApplicationInstance.APP_NAME,
                            AuthenticationError.Reason.NO_AUTHENTICATED_USER));
        }
    }

    @Override
    public void onAccount(UserAccount account, @Nullable AuthenticationError error) {
        if (error == null) {
            if (mAccount != null && !mAccount.getAuthorId().equals(account.getAuthorId())) {
                notifySessionEnded(CallbackMessage.ok());
            }
            mAccount = account;
        }

        notifyOnSession(account, error);
    }

    private UserAuthenticator getAuthenticator() {
        return mManager.getAuthenticator();
    }

    @NonNull
    private NullUserAccount nullAccount() {
        return new NullUserAccount();
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
