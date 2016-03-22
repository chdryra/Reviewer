/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ApplicationContextImpl;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseNetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleasePersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleasePresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseSocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseViewContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryApplicationContext {
    public ApplicationContext newReleaseContext(Context context,
                                                DeviceContext deviceContext,
                                                ApplicationPlugins plugins,
                                                DataAuthor author) {
        ModelContext modelContext = new ReleaseModelContext(author);

        ViewContext viewContext = new ReleaseViewContext(plugins.getUiPlugin());

        SocialContext socialContext = new ReleaseSocialContext(context);

        NetworkContext networkContext
                = new ReleaseNetworkContext(context, plugins.getNetworkServicesPlugin());

        PersistenceContext persistenceContext
                = new ReleasePersistenceContext(context, modelContext, plugins.getPersistencePlugin());

        PresenterContext presenterContext =
                new ReleasePresenterContext(context, modelContext, viewContext, deviceContext,
                        socialContext, networkContext, persistenceContext,
                        author,
                        plugins.getDataComparatorsPlugin(),
                        plugins.getDataAggregatorsPlugin());

        LocationServicesApi services = plugins.getLocationServicesPlugin().getLocationServices();

        return new ApplicationContextImpl(presenterContext, services);
    }
}
