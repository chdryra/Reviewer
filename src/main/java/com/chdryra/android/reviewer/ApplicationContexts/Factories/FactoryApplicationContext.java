package com.chdryra.android.reviewer.ApplicationContexts.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ApplicationContextImpl;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleasePresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseViewContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.LocationServices.Factories.FactoryLocationServices;
import com.chdryra.android.reviewer.LocationServices.Interfaces.ReviewerLocationServices;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;

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
        ModelContext modelContext =
                new ReleaseModelContext(context, author, plugins.getPersistencePlugin());

        ViewContext viewContext = new ReleaseViewContext(plugins.getUiPlugin());

        PresenterContext presenterContext =
                new ReleasePresenterContext(context, modelContext, viewContext, deviceContext,
                        author,
                        plugins.getDataComparatorsPlugin(),
                        plugins.getDataAggregatorsPlugin());

        ReviewerLocationServices services = getLocationServices(context, plugins.getLocationServicesPlugin());
        return new ApplicationContextImpl(presenterContext, services);
    }

    public ReviewerLocationServices getLocationServices(Context context, LocationServicesPlugin plugin) {
        FactoryLocationServices factory = new FactoryLocationServices();
        return factory.newServices(plugin.newAddressesProvider(context),
                plugin.newLocationDetailsProvider(),
                plugin.newAutoCompleterProvider(),
                plugin.newNearestPlacesProvider(),
                plugin.newPlaceSearcherProvider());
    }
}
