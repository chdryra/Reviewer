/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseDeviceContext;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ApplicationContextImpl;
import com.chdryra.android.reviewer.Model.ReleaseModelContext;
import com.chdryra.android.reviewer.NetworkServices.ReleaseNetworkContext;
import com.chdryra.android.reviewer.Persistence.ReleasePersistenceContext;
import com.chdryra.android.reviewer.Presenter.ReleasePresenterContext;
import com.chdryra.android.reviewer.Social.ReleaseSocialContext;
import com.chdryra.android.reviewer.View.ReleaseViewContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;


import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryApplicationContext {
    public ApplicationContext newReleaseContext(Context context,
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

        PresenterContext presenterContext =
                new ReleasePresenterContext(context, modelContext, viewContext, socialContext,
                        networkContext, persistenceContext, deviceContext,
                        plugins.getDataComparatorsPlugin(),
                        plugins.getDataAggregatorsPlugin(),
                        validator);

        LocationServicesApi services = plugins.getLocationServicesPlugin().getLocationServices();

        return new ApplicationContextImpl(presenterContext, services);
    }
}
