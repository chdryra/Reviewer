/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Application.Implementation.AuthenticationSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.LocationServicesSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.RepositorySuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.ReviewBuilderSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.ReviewPacker;
import com.chdryra.android.reviewer.Application.Implementation.SocialSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.UiSuiteAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ApplicationContextAndroid;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseDeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.UserSessionDefault;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReleaseModelContext;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.NetworkServices.ReleaseNetworkContext;
import com.chdryra.android.reviewer.Persistence.ReleasePersistenceContext;
import com.chdryra.android.reviewer.Presenter.ReleasePresenterContext;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.ReleaseSocialContext;
import com.chdryra.android.reviewer.View.Configs.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncherAndroid;
import com.chdryra.android.reviewer.View.ReleaseViewContext;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryApplicationContext {
    public ApplicationContextAndroid newAndroidContext(Context context,
                                                       ApplicationPlugins plugins) {
        DataValidator validator = new DataValidator();

        DeviceContext deviceContext = new ReleaseDeviceContext(context);

        ModelContext modelContext = new ReleaseModelContext();

        ViewContext viewContext = new ReleaseViewContext(plugins.getUiPlugin());

        SocialContext socialContext = new ReleaseSocialContext(context);

        NetworkContext networkContext
                = new ReleaseNetworkContext(context, plugins.getNetworkServicesPlugin());

        PersistenceContext persistenceContext
                = new ReleasePersistenceContext(modelContext, validator, plugins.getPersistencePlugin());

        PresenterContext presenter =
                new ReleasePresenterContext(context, modelContext, viewContext, socialContext,
                        networkContext, persistenceContext, deviceContext,
                        plugins.getDataComparatorsPlugin(),
                        plugins.getDataAggregatorsPlugin(),
                        validator);

        LocationServicesApi services = plugins.getLocationServicesPlugin().getLocationServices();

        AuthenticationSuiteAndroid auth = newAuthenticationSuite(presenter);
        LocationServicesSuiteAndroid location = newLocationServicesSuite(services);
        RepositorySuiteAndroid repository = newRepositorySuite(presenter);
        ReviewBuilderSuiteAndroid builder = newReviewBuilderSuite(presenter);
        UiSuiteAndroid ui = newUiSuite(presenter, repository, builder);
        SocialSuiteAndroid social = newSocialSuite(presenter);

        return new ApplicationContextAndroid(auth, location, ui, repository, builder, social);
    }

    private AuthenticationSuiteAndroid newAuthenticationSuite(PresenterContext context) {
        AccountsManager manager = context.getPersistenceContext().getAccountsManager();
        SocialPlatformList platforms = context.getSocialContext().getSocialPlatforms();
        FactoryReviews reviewsFactory = context.getModelContext().getReviewsFactory();

        UserSession session = new UserSessionDefault(manager, platforms, reviewsFactory);
        return new AuthenticationSuiteAndroid(manager, session);
    }

    private LocationServicesSuiteAndroid newLocationServicesSuite(LocationServicesApi services) {
        return new LocationServicesSuiteAndroid(services);
    }

    private RepositorySuiteAndroid newRepositorySuite(PresenterContext context) {
        PersistenceContext persistence = context.getPersistenceContext();
        NetworkContext network = context.getNetworkContext();
        ModelContext model = context.getModelContext();
        return new RepositorySuiteAndroid(persistence.getMasterRepo(),
                persistence.getAuthorsRepository(), persistence.getRepoFactory(), network.getDeleterFactory(),
                network.getPublisherFactory().newPublisher(persistence.getLocalRepository()),
                model.getTagsManager());
    }

    private SocialSuiteAndroid newSocialSuite(PresenterContext context) {
        return new SocialSuiteAndroid(context.getSocialContext().getSocialPlatforms(), context
                .getModelContext().getTagsManager());
    }

    private UiSuiteAndroid newUiSuite(PresenterContext context, RepositorySuite repo, ReviewBuilderSuite
            builder) {
        ModelContext model = context.getModelContext();
        ViewContext view = context.getViewContext();
        PersistenceContext persistence = context.getPersistenceContext();
        FactoryReviewView reviewViewFactory = context.getReviewViewFactory();

        UiConfig uiConfig = view.getUiConfig();
        UiLauncherAndroid uiLauncher = view.getLauncherFactory().newLauncher(repo, builder,
                reviewViewFactory, persistence.getMasterRepo(), uiConfig.getBuildReview());
        return new UiSuiteAndroid(uiConfig, uiLauncher, reviewViewFactory, model.getReviewsFactory());
    }

    private ReviewBuilderSuiteAndroid newReviewBuilderSuite(PresenterContext context) {
        return new ReviewBuilderSuiteAndroid(context.getReviewViewFactory(), new ReviewPacker());
    }
}
