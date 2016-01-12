package com.chdryra.android.reviewer.ApplicationContexts.Factories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ApplicationContextImpl;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleasePresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseViewContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Factories.PersistenceSuiteAndroidSqlLite;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.LocationServicesGoogle.LocationServicesGoogle;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationServicesProvider;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryApplicationContext {
    public ApplicationContext newReleaseContext(Context context,
                                                DataAuthor author,
                                                String databaseName,
                                                int databaseVersion,
                                                File externalStorageDirectory,
                                                String imageDirectory) {
        ModelContext modelContext =
                new ReleaseModelContext(context, author, databaseName, databaseVersion,
                        getPersistencePlugin());

        ViewContext viewContext = new ReleaseViewContext();

        PresenterContext presenterContext =
                new ReleasePresenterContext(context, modelContext, viewContext, author,
                        externalStorageDirectory, imageDirectory);

        return new ApplicationContextImpl(presenterContext, getLocationProviderPlugIn());
    }

    @NonNull
    private PersistenceSuiteAndroidSqlLite<ReviewerDbContract> getPersistencePlugin() {
        return new PersistenceSuiteAndroidSqlLite<>();
    }

    private LocationServicesProvider getLocationProviderPlugIn() {
        return new LocationServicesGoogle();
    }
}
