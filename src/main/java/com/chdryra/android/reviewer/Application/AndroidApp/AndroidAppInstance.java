/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.AndroidApp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.CurrentScreen;
import com.chdryra.android.reviewer.ApplicationContexts.Factories.FactoryApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.UserSessionDefault;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsRelease;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsTest;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendService.BackendRepoService;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityEditData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.CredentialProviders.GoogleLoginAndroid;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Implementation.NullRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher.ReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Singleton that controls app-wide duties.
 */
public class AndroidAppInstance extends ApplicationSingleton implements ApplicationInstance, UserSession.SessionObserver {
    private static final int LAUNCH_LOGIN = RequestCodeGenerator.getCode("LaunchLogin");
    private static final String NAME = "ApplicationInstance";

    private static AndroidAppInstance sSingleton;

    private ReviewPacker mReviewPacker;
    private PresenterContext mAppContext;
    private LocationServicesApi mLocationServices;
    private UserSession mUserSession;
    private CurrentScreen mScreen;
    private Activity mActivity;

    public enum LaunchState {RELEASE, TEST}

    private AndroidAppInstance(Context context) {
        super(context, NAME);
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

        mAppContext = appContext.getContext();
        mLocationServices = appContext.getLocationServices();
        mUserSession = new UserSessionDefault(mAppContext);
        mUserSession.registerSessionObserver(this);
        mReviewPacker = new ReviewPacker();
    }

    //Static methods
    public static AndroidAppInstance getInstance(Context context) {
        sSingleton = getSingleton(sSingleton, AndroidAppInstance.class, context);
        return sSingleton;
    }

    public static void setActivity(Activity activity) {
        getInstance(activity.getApplicationContext()).setCurrentActivity(activity);
    }

    //API
    @Override
    public ReviewBuilderAdapter<? extends GvDataList<?>> getReviewBuilderAdapter() {
        return mAppContext.getReviewBuilderAdapter();
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
        return mAppContext.getLauncherFactory().newLauncher(mActivity);
    }

    @Override
    public FactoryReviewViewParams getViewParamsFactory() {
        return mAppContext.getReviewViewParamsFactory();
    }

    @Override
    public FactoryGvData getGvDataFactory() {
        return mAppContext.getGvDataFactory();
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
    public UsersManager getUsersManager() {
        return mAppContext.getUsersManager();
    }

    @Override
    public UserSession getUserSession() {
        return mUserSession;
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
        if(mUserSession.isInSession()) {
            SocialProfile socialProfile = mUserSession.getAccount().getSocialProfile();
            return mAppContext.newFeed(mUserSession.getAuthorId(), socialProfile.getFollowing());
        } else {
            return new NullRepository();
        }
    }

    @Override
    public ReviewBuilderAdapter<?> newReviewBuilderAdapter(@Nullable Review template) {
        return mAppContext.newReviewBuilderAdapter(template);
    }

    @Override
    public void discardReviewBuilderAdapter() {
        mAppContext.discardReviewBuilderAdapter();
    }

    @Override
    public Review executeReviewBuilder() {
        return mAppContext.executeReviewBuilder();
    }

    @Override
    public void packReview(Review review, Bundle args) {
        mReviewPacker.packReview(review, args);
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
        service.setRepository(mAppContext.getMasterRepository().getMutableRepository(mUserSession));
    }

    @Override
    public void logout() {
        mScreen.showToast("Logging out...");
        mUserSession.logout(new GoogleLoginAndroid(mActivity));
    }

    @Override
    public void onLogIn(@Nullable UserAccount account, @Nullable AuthenticationError error) {

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
    public void launchImageChooser(ImageChooser chooser, int requestCode) {
        mActivity.startActivityForResult(chooser.getChooserIntents(), requestCode);
    }

    @Override
    public void setReturnResult(ActivityResultCode result) {
        if (result != null) mActivity.setResult(result.get(), null);
    }

    @Override
    public ReviewsListView newReviewsListView(ReviewNode node, boolean feedScreen) {
        return mAppContext.newReviewsListView(node, feedScreen);
    }

    @Override
    public ReviewLauncher newReviewLauncher() {
        return mAppContext.newReviewLauncher(getUiLauncher());
    }

    @Override
    public LocationClient getLocationClient(LocationClient.Locatable locatable) {
        return new LocationClientConnector(mActivity, locatable);
    }

    @Override
    public void launchEditScreen(GvDataType<? extends GvDataParcelable> type) {
        //TODO can this be moved to LaunchablesList?
        ActivityEditData.start(mActivity, type);
    }

    private void setCurrentActivity(Activity activity) {
        mActivity = activity;
        mScreen = new CurrentScreenAndroid(activity);
    }
}
