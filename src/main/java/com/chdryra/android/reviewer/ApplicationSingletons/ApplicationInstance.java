/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendService.BackendRepoService;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Singleton that controls app-wide duties.
 */
public class ApplicationInstance extends ApplicationSingleton implements UserAuthenticator
        .UserStateObserver {
    private static final int LAUNCH_SPLASH = RequestCodeGenerator.getCode("LaunchSplash");

    public static final String APP_NAME = "Teeqr";
    private static final String NAME = "ApplicationInstance";

    private static ApplicationInstance sSingleton;

    private final ReviewPacker mReviewPacker;
    private final PresenterContext mPresenterContext;
    private final LocationServicesApi mLocationServices;
    private LoginObserver mObserver;
    private AuthorProfile mCurrentProfile;
    private AuthenticatedUser mCurrentUser;

    public interface LoginObserver {
        void onLoggedIn(@Nullable AuthenticatedUser user, @Nullable AuthorProfile profile, @Nullable
        AuthenticationError error);
    }

    private ApplicationInstance(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call newInstance(.)!");
    }

    private ApplicationInstance(Context context, ApplicationContext applicationContext) {
        super(context, NAME);
        mPresenterContext = applicationContext.getContext();
        mLocationServices = applicationContext.getLocationServices();
        mReviewPacker = new ReviewPacker();

        UserAuthenticator authenticator = getUsersManager().getAuthenticator();
        onUserStateChanged(null, authenticator.getAuthenticatedUser());
        authenticator.registerObserver(this);
    }

    //Static methods
    public static void newInstance(Context context,
                                   ApplicationContext applicationContext) {
        sSingleton = new ApplicationInstance(context, applicationContext);
    }

    public static ApplicationInstance getInstance(Context context) {
        sSingleton = getSingleton(sSingleton, ApplicationInstance.class, context);
        return sSingleton;
    }

    public ReviewBuilderAdapter<? extends GvDataList<?>> getReviewBuilderAdapter() {
        return mPresenterContext.getReviewBuilderAdapter();
    }

    public ReviewsFeed getUsersFeed() {
        return mPresenterContext.getFeedFactory().newFeed(getReviewsFactory());
    }

    public FactoryReviews getReviewsFactory() {
        return mPresenterContext.getReviewsFactory();
    }

    public SocialPlatformList getSocialPlatformList() {
        return mPresenterContext.getSocialPlatformList();
    }

    public ConfigUi getConfigUi() {
        return mPresenterContext.getConfigUi();
    }

    public LaunchableUiLauncher getUiLauncher() {
        return mPresenterContext.getUiLauncher();
    }

    public FactoryReviewViewLaunchable getLaunchableFactory() {
        return mPresenterContext.getReviewViewLaunchableFactory();
    }

    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mPresenterContext.getReviewViewAdapterFactory();
    }

    public FactoryReviewViewParams getParamsFactory() {
        return getLaunchableFactory().getParamsFactory();
    }

    public FactoryGvData getGvDataFactory() {
        return mPresenterContext.getGvDataFactory();
    }

    public LocationServicesApi getLocationServices() {
        return mLocationServices;
    }

    public TagsManager getTagsManager() {
        return mPresenterContext.getTagsManager();
    }

    public ReviewPublisher getPublisher() {
        return mPresenterContext.getReviewPublisher();
    }

    public UsersManager getUsersManager() {
        return mPresenterContext.getUsersManager();
    }

    public <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mPresenterContext.getDataBuilderAdapter(dataType);
    }

    public ReviewBuilderAdapter<?> newReviewBuilderAdapter(@Nullable Review template) {
        return mPresenterContext.newReviewBuilderAdapter(template);
    }

    public void discardReviewBuilderAdapter() {
        mPresenterContext.discardReviewBuilderAdapter();
    }

    public Review executeReviewBuilder() {
        return mPresenterContext.executeReviewBuilder();
    }

    public void packReview(Review review, Bundle args) {
        mReviewPacker.packReview(review, args);
    }

    public
    @Nullable
    Review unpackReview(Bundle args) {
        return mReviewPacker.unpackReview(args);
    }

    public void launchReview(Activity activity, ReviewId reviewId) {
        mPresenterContext.launchReview(activity, reviewId);
    }

    public void getReview(ReviewId id, CallbackRepository callback) {
        mPresenterContext.getReview(id, callback);
    }

    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mPresenterContext.newReviewDeleter(id);
    }

    public ReviewsRepositoryMutable getBackendRepository(BackendRepoService service) {
        // to ensure only used by BackendRepoService
        return mPresenterContext.getBackendRepository();
    }

    public void setLoginObserver(LoginObserver observer) {
        mObserver = observer;
        observeCurrentUser();
    }

    public void observeCurrentUser() {
        if (mCurrentUser == null || mCurrentProfile == null) {
            notifyLogin(null, null,
                    new AuthenticationError("app", AuthenticationError.Reason.NO_AUTHENTICATED_USER));
        } else {
            notifyLogin(mCurrentUser, mCurrentProfile, null);
        }
    }

    public void unsetLoginObserver() {
        mObserver = null;
    }

    public void logout(Activity activity) {
        mPresenterContext.logoutCurrentUser();
        LaunchableConfig splashConfig = getConfigUi().getSplashConfig();
        getUiLauncher().launch(splashConfig, activity, LAUNCH_SPLASH);
        activity.finish();
    }

    @Override
    public void onUserStateChanged(@Nullable AuthenticatedUser oldUser,
                                   @Nullable AuthenticatedUser newUser) {
        mCurrentUser = newUser;
        mCurrentProfile = null;

        if (oldUser == null && newUser == null) {
            notifyLogin(null, null,
                    new AuthenticationError("app", AuthenticationError.Reason.NO_AUTHENTICATED_USER));
            return;
        }

        mPresenterContext.getCurrentProfile(new UserAccounts.GetProfileCallback() {
            @Override
            public void onProfile(AuthenticatedUser user, AuthorProfile profile, @Nullable
            AuthenticationError error) {
                if (error == null) setAuthor(user, profile);
                notifyLogin(user, profile, error);
            }
        });
    }

    private void setAuthor(AuthenticatedUser user, AuthorProfile profile) {
        getReviewsFactory().setAuthorsStamp(new AuthorsStamp(profile.getAuthor()));
        mCurrentUser = user;
        mCurrentProfile = profile;
    }

    private void notifyLogin(@Nullable AuthenticatedUser user, @Nullable AuthorProfile profile, @Nullable
    AuthenticationError error) {
        if (mObserver != null) mObserver.onLoggedIn(user, profile, error);
    }
}
