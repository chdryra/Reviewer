package com.chdryra.android.reviewer.ApplicationContexts.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ApplicationContextImpl;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleasePresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseViewContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryApplicationContext {
    public ApplicationContext newReleaseContext(Context context,
                                                DataAuthor author,
                                                DeviceContext deviceContext,
                                                ApplicationPlugins plugins) {
        ModelContext modelContext =
                new ReleaseModelContext(context, author, plugins.getPersistencePlugin());

        ViewContext viewContext = new ReleaseViewContext();

        PresenterContext presenterContext =
                new ReleasePresenterContext(context, modelContext, viewContext, deviceContext, author);

        return new ApplicationContextImpl(presenterContext, plugins.getLocationServicesPlugin());
    }
}
