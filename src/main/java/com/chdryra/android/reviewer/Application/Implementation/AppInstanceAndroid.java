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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.chdryra.android.mygenerallibrary.CacheUtils.ItemPacker;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.mygenerallibrary.Permissions.PermissionsManager;
import com.chdryra.android.reviewer.Application.Factories.FactoryApplicationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.AccountsSuite;
import com.chdryra.android.reviewer.Application.Interfaces.EditorSuite;
import com.chdryra.android.reviewer.Application.Interfaces.GeolocationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.NetworkSuite;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.SocialSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsRelease;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsTest;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ReviewPack;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Singleton that controls app-wide duties.
 */
public class AppInstanceAndroid implements ApplicationInstance, ActivityCompat.OnRequestPermissionsResultCallback {
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
        //printHashKey(context);
    }

//    //For FB
//    private static void printHashKey(Context context) {
//        String tag = "HASH";
//        try {
//            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
//            for (android.content.pm.Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i(tag, "printHashKey() Hash Key: " + hashKey);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Log.e(tag, "printHashKey()", e);
//        } catch (Exception e) {
//            Log.e(tag, "printHashKey()", e);
//        }
//    }

    public static AppInstanceAndroid getInstance(Context context) {
        if (sSingleton == null) {
            sSingleton = new AppInstanceAndroid(context.getApplicationContext());
        }

        return sSingleton;
    }

    public static void setActivity(Activity activity) {
        getInstance(activity).setCurrentActivity(activity);
    }

    public ReviewPack unpackReview(Bundle args) {
        return mApp.unpackReview(args);
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
    public AccountsSuite getAccounts() {
        return mApp.getAccounts();
    }

    @Override
    public GeolocationSuite getGeolocation() {
        return mApp.getGeolocation();
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
    public EditorSuite getEditor() {
        return mApp.getEditor();
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
    public PermissionsManager getPermissions() {
        return mApp.getPermissions();
    }

    @Override
    public void logout() {
        mApp.logout();
    }

    @Override
    public void setReturnResult(ActivityResultCode result) {
        mApp.setReturnResult(result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mApp.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
