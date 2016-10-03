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
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.AuthenticationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.LocationServicesSuite;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationContexts.Factories.FactoryApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ApplicationContextAndroid;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsRelease;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsTest;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BackendService.BackendRepoService;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Implementation.NullRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Singleton that controls app-wide duties.
 */
public class AppInstanceAndroid implements ApplicationInstance, UserSession.SessionObserver {
    private static final int GOOGLE_API_CHECK = RequestCodeGenerator.getCode(AppInstanceAndroid.class, "GoogleApiCheck");

    private static AppInstanceAndroid sSingleton;

    private ApplicationContextAndroid mContext;

    private ReviewPacker mReviewPacker;
    private PresenterContext mAppContext;
    private boolean mGoogleApiOk = false;

    private enum LaunchState {RELEASE, TEST}

    private AppInstanceAndroid(Context context) {
        instantiate(context, LaunchState.TEST);
    }

    private void instantiate(Context context, LaunchState launchState) {
        ApplicationPlugins plugins;
        if(launchState.equals(LaunchState.RELEASE)) {
            plugins = new ApplicationPluginsRelease(context);
        } else {
            plugins = new ApplicationPluginsTest(context);
        }

        FactoryApplicationContext factory = new FactoryApplicationContext();
        ApplicationContextAndroid appContext = factory.newAndroidContext(context, plugins);

        mContext = appContext;
        mAppContext = appContext.getContext();

        getAuthentication().getUserSession().registerSessionObserver(this);

        mReviewPacker = new ReviewPacker();
        mContext.setLauncher(mReviewPacker);
    }

    //Static methods
    public static AppInstanceAndroid getInstance(Context context) {
        if(sSingleton == null) sSingleton = new AppInstanceAndroid(context.getApplicationContext());
        return sSingleton;
    }

    public static void setActivity(Activity activity) {
        getInstance(activity).setCurrentActivity(activity);
    }

    //API
    @Override
    public AuthenticationSuite getAuthentication() {
        return mContext.getAuthenticationSuite();
    }

    @Override
    public LocationServicesSuite getLocationServices() {
        return mContext.getLocationServicesSuite();
    }

    @Override
    public UiSuite getUi() {
        return mContext.getUiSuite();
    }

    @Override
    public RepositorySuite getRepository() {
        return mContext.getRepositorySuite();
    }

    @Override
    public ReviewBuilderSuite getReviewBuilder() {
        return mContext.getReviewBuilderSuite();
    }

    @Override
    public void logout() {
        getAuthentication().logout();
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mAppContext.getSocialPlatformList();
    }

    @Override
    public TagsManager getTagsManager() {
        return mAppContext.getTagsManager();
    }

    @Override
    public ReviewPublisher getPublisher() {
        return mAppContext.getReviewPublisher();
    }

    public ReferencesRepository getUsersFeed() {
        if(getUserSession().isInSession()) {
            return mAppContext.newFeed(getSocialProfile());
        } else {
            return new NullRepository();
        }
    }

    @Override
    public SocialProfile getSocialProfile() {
        return getUserSession().getAccount().getSocialProfile();
    }

    @Override
    @Nullable
    public Review unpackReview(Bundle args) {
        return mReviewPacker.unpackReview(args);
    }

    public void setBackendRepository(BackendRepoService service) {
        // to ensure only used by BackendRepoService
        service.setRepository(mAppContext.getMasterRepository().getMutableRepository(getUserSession()));
    }

    private UserSession getUserSession() {
        return getAuthentication().getUserSession();
    }

    @Override
    public void onLogIn(@Nullable UserAccount account, @Nullable AuthenticationError error) {
        mContext.setSession(getUserSession());
    }

    @Override
    public void onLogOut(UserAccount account, CallbackMessage message) {
        if(!message.isError()) {
            returnToLogin();
        } else {
            getScreen().showToast("Problem logging out: " + message.getMessage());
        }
    }

    private CurrentScreen getScreen() {
        return getUi().getCurrentScreen();
    }

    private void returnToLogin() {
        mAppContext.getUiConfig().getLogin().launch();
        getScreen().close();
    }

    @Override
    public void setReturnResult(ActivityResultCode result) {
        if (result != null) mActivity.setResult(result.get(), null);
    }

    private void setCurrentActivity(Activity activity) {
        if(!mGoogleApiOk) checkGoogleApi(activity);

        mContext.setActivity(activity);
    }

    private void checkGoogleApi(Activity activity) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int status = instance.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(instance.isUserResolvableError(status)) {
                instance.getErrorDialog(activity, status, GOOGLE_API_CHECK).show();
            }
            mGoogleApiOk = false;
        }

        mGoogleApiOk = true;
    }
}
