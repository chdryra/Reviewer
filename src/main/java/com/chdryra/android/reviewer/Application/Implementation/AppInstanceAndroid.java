/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.CacheUtils.ItemPacker;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Factories.FactoryApplicationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.AuthenticationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.LocationServicesSuite;
import com.chdryra.android.reviewer.Application.Interfaces.NetworkSuite;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewEditorSuite;
import com.chdryra.android.reviewer.Application.Interfaces.SocialSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsRelease;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsTest;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Singleton that controls app-wide duties.
 */
public class AppInstanceAndroid implements ApplicationInstance {
    private static final int GOOGLE_API_CHECK
            = RequestCodeGenerator.getCode(AppInstanceAndroid.class, "GoogleApiCheck");

    private static AppInstanceAndroid sSingleton;
    private static ItemPacker<ReviewView<?>> mViewPacker;

    private ApplicationSuiteAndroid mApp;
    private boolean mGoogleApiOk = false;

    private enum LaunchState {RELEASE, TEST}

    private AppInstanceAndroid(Context context) {
        instantiate(context, LaunchState.TEST);
        mViewPacker = new ItemPacker<>();
    }

    public static AppInstanceAndroid getInstance(Context context) {
        if (sSingleton == null) {
            sSingleton = new AppInstanceAndroid(context.getApplicationContext());
        }

        return sSingleton;
    }

    public static void setActivity(Activity activity) {
        getInstance(activity).setCurrentActivity(activity);
    }

    @Nullable
    public Review unpackTemplate(Bundle args) {
        return mApp.unpackTemplate(args);
    }

    @Nullable
    public ReviewNode unpackNode(Bundle args) {
        return mApp.unpackNode(args);
    }

    @Nullable
    public ReviewView<?> unpackView(Intent i) {
        return mApp.unpackView(i);
    }

    public void retainView(ReviewView<?> reviewView, Bundle args) {
        mViewPacker.pack(reviewView, args);
    }

    @Nullable
    public ReviewView<?> getRetainedView(Bundle args) {
        return mViewPacker.unpack(args);
    }

    @Override
    public AuthenticationSuite getAuthentication() {
        return mApp.getAuthentication();
    }

    @Override
    public LocationServicesSuite getLocationServices() {
        return mApp.getLocationServices();
    }

    @Override
    public UiSuite getUi() {
        return mApp.getUi();
    }

    @Override
    public RepositorySuite getRepository() {
        return mApp.getRepository();
    }

    @Override
    public ReviewEditorSuite getReviewEditor() {
        return mApp.getReviewEditor();
    }

    @Override
    public SocialSuite getSocial() {
        return mApp.getSocial();
    }

    @Override
    public NetworkSuite getNetwork() {
        return mApp.getNetwork();
    }

    @Override
    public void logout() {
        mApp.logout();
    }

    @Override
    public void setReturnResult(ActivityResultCode result) {
        mApp.setReturnResult(result);
    }

    private void instantiate(Context context, LaunchState launchState) {
        ApplicationPlugins plugins = launchState.equals(LaunchState.RELEASE) ?
                new ApplicationPluginsRelease(context) : new ApplicationPluginsTest(context);
        mApp = new FactoryApplicationSuite().newAndroidApp(context, plugins);
    }

    private void setCurrentActivity(Activity activity) {
        if (!mGoogleApiOk) checkGoogleApi(activity);
        mApp.setActivity(activity);
    }

    private void checkGoogleApi(Activity activity) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int status = instance.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (instance.isUserResolvableError(status)) {
                instance.getErrorDialog(activity, status, GOOGLE_API_CHECK).show();
            }
            mGoogleApiOk = false;
        }

        mGoogleApiOk = true;
    }
}
