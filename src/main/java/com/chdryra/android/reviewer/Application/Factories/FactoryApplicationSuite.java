/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Factories;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.Permissions.PermissionsManagerAndroid;
import com.chdryra.android.reviewer.Application.Implementation.AccountsSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.ApplicationSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.EditorSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.InSessionStamper;
import com.chdryra.android.reviewer.Application.Implementation.GeolocationSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.NetworkSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.RepositorySuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.SocialSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.UiSuiteAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.EditorSuite;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseDeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServices;
import com.chdryra.android.reviewer.Authentication.Implementation.UserSessionDefault;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReleaseModelContext;
import com.chdryra.android.reviewer.NetworkServices.ReleaseNetworkContext;
import com.chdryra.android.reviewer.Persistence.ReleasePersistenceContext;
import com.chdryra.android.reviewer.Presenter.ReleasePresenterContext;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewSummariser;
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
