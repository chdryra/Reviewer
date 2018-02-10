/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Factories;

import android.content.Context;

import com.chdryra.android.corelibrary.Permissions.PermissionsManagerAndroid;
import com.chdryra.android.startouch.Application.Implementation.AccountsSuiteAndroid;
import com.chdryra.android.startouch.Application.Implementation.ApplicationSuiteAndroid;
import com.chdryra.android.startouch.Application.Implementation.EditorSuiteAndroid;
import com.chdryra.android.startouch.Application.Implementation.InSessionStamper;
import com.chdryra.android.startouch.Application.Implementation.GeolocationSuiteAndroid;
import com.chdryra.android.startouch.Application.Implementation.NetworkSuiteAndroid;
import com.chdryra.android.startouch.Application.Implementation.RepositorySuiteAndroid;
import com.chdryra.android.startouch.Application.Implementation.SocialSuiteAndroid;
import com.chdryra.android.startouch.Application.Implementation.UiSuiteAndroid;
import com.chdryra.android.startouch.Application.Interfaces.EditorSuite;
import com.chdryra.android.startouch.ApplicationContexts.Implementation.ReleaseDeviceContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.startouch.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServices;
import com.chdryra.android.startouch.Authentication.Implementation.UserSessionDefault;
import com.chdryra.android.startouch.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.Model.ReleaseModelContext;
import com.chdryra.android.startouch.NetworkServices.ReleaseNetworkContext;
import com.chdryra.android.startouch.Persistence.ReleasePersistenceContext;
import com.chdryra.android.startouch.Presenter.ReleasePresenterContext;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.startouch.Social.Implementation.ReviewSummariser;
import com.chdryra.android.startouch.Social.Implementation.SocialPlatformList;
import com.chdryra.android.startouch.Social.ReleaseSocialContext;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherAndroid;
import com.chdryra.android.startouch.View.ReleaseViewContext;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryApplicationSuite {
    public ApplicationSuiteAndroid newAndroidApp(Context context, ApplicationPlugins plugins) {
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

        AccountsSuiteAndroid auth = newAuthenticationSuite(model, persistence, social);
        PermissionsManagerAndroid permissions = new PermissionsManagerAndroid(context);
        GeolocationSuiteAndroid location = newLocationServicesSuite(plugins.getLocationServices().getApi(permissions));
        RepositorySuiteAndroid repo = newRepositorySuite(persistence, network);
        EditorSuiteAndroid editor = newReviewEditorSuite(presenter);
        UiSuiteAndroid ui = newUiSuite(persistence, view, presenter, editor);
        SocialSuiteAndroid socialSuite = newSocialSuite(social);
        NetworkSuiteAndroid networkSuite = new NetworkSuiteAndroid(context);

        return new ApplicationSuiteAndroid(auth, location, ui, repo, editor, socialSuite, networkSuite, permissions);
    }

    private AccountsSuiteAndroid newAuthenticationSuite(ModelContext model,
                                                        PersistenceContext persistence,
                                                        SocialContext social) {
        AccountsManager accountsManager = persistence.getAccountsManager();
        SocialPlatformList socialPlatforms = social.getSocialPlatforms();

        UserSession session = new UserSessionDefault(accountsManager, socialPlatforms);

        InSessionStamper reviewStamper = new InSessionStamper();
        session.registerSessionObserver(reviewStamper);
        model.getReviewsFactory().setReviewStamper(reviewStamper);

        return new AccountsSuiteAndroid(accountsManager, session);
    }

    private GeolocationSuiteAndroid newLocationServicesSuite(LocationServices services) {
        return new GeolocationSuiteAndroid(services);
    }

    private RepositorySuiteAndroid newRepositorySuite(PersistenceContext persistence,
                                                      NetworkContext network) {
        return new RepositorySuiteAndroid(persistence.getAuthorsRepo(), persistence.getReviewsRepo(),
                persistence.getRepoFactory(),
                network.getDeleterFactory(),
                network.getPublisherFactory().newPublisher(persistence.getLocalRepo()));
    }

    private SocialSuiteAndroid newSocialSuite(SocialContext social) {
        return new SocialSuiteAndroid(social.getSocialPlatforms(), new ReviewSummariser(), new ReviewFormatterTwitter());
    }

    private UiSuiteAndroid newUiSuite(PersistenceContext persistence,
                                      ViewContext view,
                                      PresenterContext presenter,
                                      EditorSuite builder) {
        FactoryReviewView viewFactory = presenter.getReviewViewFactory();
        UiConfig uiConfig = view.getUiConfig();
        UiLauncherAndroid uiLauncher = view.getLauncherFactory().newLauncher(builder,
                viewFactory, persistence.getReviewsRepo(), uiConfig);

        return new UiSuiteAndroid(uiConfig, uiLauncher, presenter.getCommandsFactory(),
                viewFactory, presenter.getGvConverter());
    }

    private EditorSuiteAndroid newReviewEditorSuite(PresenterContext context) {
        return new EditorSuiteAndroid(context.getReviewViewFactory(), context.getImageChooserFactory());
    }
}
