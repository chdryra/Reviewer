/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Factories;

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
import com.chdryra.android.reviewer.Application.Implementation.ApplicationSuiteAndroid;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseDeviceContext;
import com.chdryra.android.reviewer.Application.Implementation.InSessionStamper;
import com.chdryra.android.reviewer.Application.Implementation.UserSessionDefault;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReleaseModelContext;
import com.chdryra.android.reviewer.NetworkServices.ReleaseNetworkContext;
import com.chdryra.android.reviewer.Persistence.ReleasePersistenceContext;
import com.chdryra.android.reviewer.Presenter.ReleasePresenterContext;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.ReleaseSocialContext;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherAndroid;
import com.chdryra.android.reviewer.View.ReleaseViewContext;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryApplicationSuite {
    public ApplicationSuiteAndroid newAndroidContext(Context context,
                                                     ApplicationPlugins plugins) {
        DataValidator validator = new DataValidator();
        DeviceContext device = new ReleaseDeviceContext(context);
        ModelContext model = new ReleaseModelContext();
        ViewContext view = new ReleaseViewContext(plugins.getUi());
        SocialContext social = new ReleaseSocialContext(context);
        NetworkContext network = new ReleaseNetworkContext(context, plugins.getNetworkServices());
        PersistenceContext persistence
                = new ReleasePersistenceContext(model, validator, plugins.getPersistence());
        PresenterContext presenter =
                new ReleasePresenterContext(context, model, view, persistence, device,
                        plugins.getDataComparators(), plugins.getDataAggregators(), validator);

        LocationServicesApi locationApi = plugins.getLocationServices().getApi();

        AuthenticationSuiteAndroid auth = newAuthenticationSuite(model, persistence, social);
        LocationServicesSuiteAndroid location = newLocationServicesSuite(locationApi);
        RepositorySuiteAndroid repository = newRepositorySuite(model, persistence, network);
        ReviewBuilderSuiteAndroid builder = newReviewBuilderSuite(presenter);
        UiSuiteAndroid ui = newUiSuite(model, persistence, view, presenter, repository, builder);
        SocialSuiteAndroid socialSuite = newSocialSuite(model, social);

        return new ApplicationSuiteAndroid(auth, location, ui, repository, builder, socialSuite);
    }

    private AuthenticationSuiteAndroid newAuthenticationSuite(ModelContext model, PersistenceContext persistence, SocialContext social) {
        AccountsManager manager = persistence.getAccountsManager();
        SocialPlatformList platforms = social.getSocialPlatforms();

        UserSession session = new UserSessionDefault(manager, platforms);

        InSessionStamper reviewStamper = new InSessionStamper();
        session.registerSessionObserver(reviewStamper);
        model.getReviewsFactory().setReviewStamper(reviewStamper);

        return new AuthenticationSuiteAndroid(manager, session);
    }

    private LocationServicesSuiteAndroid newLocationServicesSuite(LocationServicesApi services) {
        return new LocationServicesSuiteAndroid(services);
    }

    private RepositorySuiteAndroid newRepositorySuite(ModelContext model, PersistenceContext persistence, NetworkContext network) {
        return new RepositorySuiteAndroid(persistence.getMasterRepo(),
                persistence.getAuthorsRepository(), persistence.getRepoFactory(), network.getDeleterFactory(),
                network.getPublisherFactory().newPublisher(persistence.getLocalRepository()),
                model.getTagsManager());
    }

    private SocialSuiteAndroid newSocialSuite(ModelContext model, SocialContext social) {
        return new SocialSuiteAndroid(social.getSocialPlatforms(), model.getTagsManager());
    }

    private UiSuiteAndroid newUiSuite(ModelContext model,
                                      PersistenceContext persistence,
                                      ViewContext view,
                                      PresenterContext presenter,
                                      RepositorySuite repo,
                                      ReviewBuilderSuite builder) {
        FactoryReviewView reviewViewFactory = presenter.getReviewViewFactory();

        UiConfig uiConfig = view.getUiConfig();
        UiLauncherAndroid uiLauncher = view.getLauncherFactory().newLauncher(repo, builder,
                reviewViewFactory, persistence.getMasterRepo(), uiConfig.getBuildReview());
        return new UiSuiteAndroid(uiConfig, uiLauncher, reviewViewFactory, model.getReviewsFactory());
    }

    private ReviewBuilderSuiteAndroid newReviewBuilderSuite(PresenterContext context) {
        return new ReviewBuilderSuiteAndroid(context.getReviewViewFactory(), new ReviewPacker());
    }
}
