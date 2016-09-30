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
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.AuthenticationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.ApplicationContexts.Factories.FactoryApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsRelease;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsTest;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendService.BackendRepoService;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Implementation.NullRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher.ReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewNodeRepo;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncherAndroid;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Singleton that controls app-wide duties.
 */
public class AndroidAppInstance implements ApplicationInstance, UserSession.SessionObserver {
    private static final int LAUNCH_LOGIN = RequestCodeGenerator.getCode(AndroidAppInstance.class, "LaunchLogin");
    private static final int GOOGLE_API_CHECK = RequestCodeGenerator.getCode(AndroidAppInstance.class, "GoogleApiCheck");

    private static AndroidAppInstance sSingleton;

    private ApplicationContext mContext;

    private ReviewPacker mReviewPacker;
    private PresenterContext mAppContext;
    private LocationServicesApi mLocationServices;
    private UiLauncherAndroid mUiLauncher;
    private CurrentScreen mScreen;
    private Activity mActivity;
    private boolean mGoogleApiOk = false;

    private enum LaunchState {RELEASE, TEST}

    private AndroidAppInstance(Context context) {
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
        ApplicationContext appContext = factory.newReleaseContext(context, plugins);

        mContext = appContext;
        mAppContext = appContext.getContext();
        mLocationServices = appContext.getLocationServices();

        mContext.getAuthenticationSuite().getUserSession().registerSessionObserver(this);

        mReviewPacker = new ReviewPacker();
    }

    //Static methods
    public static AndroidAppInstance getInstance(Context context) {
        if(sSingleton == null) sSingleton = new AndroidAppInstance(context.getApplicationContext());
        return sSingleton;
    }

    public static void setActivity(Activity activity) {
        getInstance(activity).setCurrentActivity(activity);
    }

    //API

    @Override
    public AuthenticationSuite getAuthenticationSuite() {
        return mContext.getAuthenticationSuite();
    }

    @Override
    public void logout() {
        getAuthenticationSuite().logout(mActivity);
    }

    @Override
    public FactoryReviews getReviewsFactory() {
        return mAppContext.getReviewsFactory();
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mAppContext.getSocialPlatformList();
    }

    @Override
    public ConfigUi getConfigUi() {
        return mAppContext.getConfigUi();
    }

    @Override
    public UiLauncher getUiLauncher() {
        if(mUiLauncher == null) newLauncher();
        return mUiLauncher;
    }

    private void newLauncher() {
        mUiLauncher = mAppContext.newUiLauncher(mReviewPacker);
        mUiLauncher.setActivity(mActivity);
        mUiLauncher.setSession(getUserSession());
        getConfigUi().setUiLauncher(mUiLauncher);
    }

    @Override
    public LocationServicesApi getLocationServices() {
        return mLocationServices;
    }

    @Override
    public TagsManager getTagsManager() {
        return mAppContext.getTagsManager();
    }

    @Override
    public ReviewPublisher getPublisher() {
        return mAppContext.getReviewPublisher();
    }

    @Override
    public CurrentScreen getCurrentScreen() {
        return mScreen;
    }

    @Override
    public ReferencesRepository getReviews(AuthorId authorId) {
        return mAppContext.getMasterRepository().getRepositoryForAuthor(authorId);
    }

    @Override
    public ReferencesRepository getUsersFeed() {
        if(getUserSession().isInSession()) {
            SocialProfile socialProfile = getSocialProfile();
            return mAppContext.newFeed(getSessionUser(), socialProfile.getFollowing());
        } else {
            return new NullRepository();
        }
    }

    @Override
    public SocialProfile getSocialProfile() {
        return getUserSession().getAccount().getSocialProfile();
    }

    @Override
    public ReviewEditor<?> newReviewEditor(@Nullable Review template) {
        return mAppContext.newReviewEditor(template, getUiLauncher(), newLocationClient());
    }

    @Override
    public ReviewEditor<?> getReviewEditor() {
        return mAppContext.getReviewEditor();
    }

    @Override
    public void discardReviewEditor() {
        mAppContext.discardReviewEditor();
    }

    @Override
    public Review executeReviewEditor() {
        return mAppContext.executeReviewEditor();
    }

    @Override
    public ReviewView<?> newPublishScreen(PlatformAuthoriser authoriser, PublishAction.PublishCallback callback) {
        return mAppContext.newPublishScreen(authoriser, callback);
    }

    @Override
    @Nullable
    public Review unpackReview(Bundle args) {
        return mReviewPacker.unpackReview(args);
    }

    @Override
    public void getReview(ReviewId id, RepositoryCallback callback) {
        mAppContext.getReview(id, callback);
    }

    @Override
    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mAppContext.newReviewDeleter(id);
    }

    public void setBackendRepository(BackendRepoService service) {
        // to ensure only used by BackendRepoService
        service.setRepository(mAppContext.getMasterRepository().getMutableRepository(getUserSession()));
    }

    private UserSession getUserSession() {
        return getAuthenticationSuite().getUserSession();
    }

    @Override
    public void onLogIn(@Nullable UserAccount account, @Nullable AuthenticationError error) {
        ((UiLauncherAndroid)getUiLauncher()).setSession(getUserSession());
    }

    @Override
    public void onLogOut(UserAccount account, CallbackMessage message) {
        if(!message.isError()) {
            returnToLogin();
        } else {
            mScreen.showToast("Problem logging out: " + message.getMessage());
        }
    }

    private void returnToLogin() {
        LaunchableConfig loginConfig = mAppContext.getConfigUi().getLogin();
        getUiLauncher().launch(loginConfig, LAUNCH_LOGIN);
        mScreen.close();
    }

    @Override
    public void setReturnResult(ActivityResultCode result) {
        if (result != null) mActivity.setResult(result.get(), null);
    }

    @Override
    public ReviewsListView newFeedView() {
        ReferencesRepository feed = getUsersFeed();
        AuthorsRepository authorsRepo = getUsersManager().getAuthorsRepository();
        ReviewNodeRepo node = getReviewsFactory().createFeed(getSessionUser(), feed, authorsRepo);

        return mAppContext.newFeedView(node, newReviewLauncher());
    }

    @Override
    public ReviewLauncher newReviewLauncher() {
        return mUiLauncher.newReviewLauncher();
    }

    private AuthorId getSessionUser() {
        return getUserSession().getAuthorId();
    }

    @Override
    public LocationClient newLocationClient() {
        return new LocationClientConnector(mActivity);
    }

    private void setCurrentActivity(Activity activity) {
        if(!mGoogleApiOk) checkGoogleApi(activity);

        mActivity = activity;
        mScreen = new CurrentScreenAndroid(activity);

        if(mUiLauncher != null) {
            mUiLauncher.setActivity(mActivity);
        } else {
            newLauncher();
        }
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
