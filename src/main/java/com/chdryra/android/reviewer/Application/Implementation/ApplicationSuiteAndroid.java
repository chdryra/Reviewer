/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.Permissions.PermissionsManagerAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.AuthenticationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.LocationServicesSuite;
import com.chdryra.android.reviewer.Application.Interfaces.NetworkSuite;
import com.chdryra.android.mygenerallibrary.Permissions.PermissionsManager;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.EditorSuite;
import com.chdryra.android.reviewer.Application.Interfaces.SocialSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ReviewPack;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationSuiteAndroid implements ApplicationSuite, UserSession.SessionObserver,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private final AuthenticationSuiteAndroid mAuth;
    private final LocationServicesSuiteAndroid mLocation;
    private final UiSuiteAndroid mUi;
    private final RepositorySuiteAndroid mRepository;
    private final EditorSuiteAndroid mBuilder;
    private final SocialSuiteAndroid mSocial;
    private final NetworkSuiteAndroid mNetwork;
    private final PermissionsManagerAndroid mPermissions;

    private Activity mActivity;

    public ApplicationSuiteAndroid(AuthenticationSuiteAndroid auth,
                                   LocationServicesSuiteAndroid location,
                                   UiSuiteAndroid ui,
                                   RepositorySuiteAndroid repository,
                                   EditorSuiteAndroid builder,
                                   SocialSuiteAndroid social,
                                   NetworkSuiteAndroid network,
                                   PermissionsManagerAndroid permissions) {
        mAuth = auth;
        mLocation = location;
        mUi = ui;
        mRepository = repository;
        mBuilder = builder;
        mSocial = social;
        mNetwork = network;
        mPermissions = permissions;

        mAuth.getUserSession().registerSessionObserver(this);
        mUi.setApplication(this);
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
        mAuth.setActivity(mActivity);
        mLocation.setActivity(mActivity);
        mUi.setActivity(mActivity);
        mSocial.setActivity(mActivity);
        mPermissions.setActivity(mActivity);
    }

    private void setSession() {
        UserSession session = getAuthentication().getUserSession();
        mUi.setSession(session);
        mSocial.setSession(session);
    }

    void setReturnResult(ActivityResultCode result) {
        if (result != null) mActivity.setResult(result.get(), null);
    }

    @Override
    public AuthenticationSuite getAuthentication() {
        return mAuth;
    }

    @Override
    public LocationServicesSuite getLocationServices() {
        return mLocation;
    }

    @Override
    public UiSuite getUi() {
        return mUi;
    }

    @Override
    public RepositorySuite getRepository() {
        return mRepository;
    }

    @Override
    public SocialSuite getSocial() {
        return mSocial;
    }

    @Override
    public NetworkSuite getNetwork() {
        return mNetwork;
    }

    @Override
    public EditorSuite getEditor() {
        return mBuilder;
    }

    @Override
    public PermissionsManager getPermissions() {
        return mPermissions;
    }

    @Override
    public void onLogIn(@Nullable UserAccount account, @Nullable AuthenticationError error) {
        setSession();
    }

    @Override
    public void onLogOut(UserAccount account, CallbackMessage message) {
        CurrentScreen screen = getUi().getCurrentScreen();
        if (message.isOk()) {
            getUi().getConfig().getLogin().launch();
            screen.close();
        } else {
            screen.showToast(Strings.Toasts.PROBLEM_LOGGING_OUT + ": " + message.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void logout() {
        getAuthentication().logout();
    }

    ReviewPack unpackReview(Bundle args) {
        return mUi.unpackReview(args);
    }

    @Nullable
    ReviewNode unpackNode(Bundle args) {
        return mUi.unpackNode(args);
    }

    @Nullable
    ReviewView<?> unpackView(Intent i) {
        return mUi.unpackView(i);
    }
}
