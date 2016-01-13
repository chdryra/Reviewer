package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationContexts.Factories.FactoryApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.PersistencePlugin;
import com.chdryra.android.reviewer.PlugIns.LocationServices.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.AuthorId;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation.PersistenceAndroidSqlLite;
import com.chdryra.android.reviewer.PlugIns.LocationServices.LocationServicesGoogle.LocationServicesGoogle;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationLaunch {
    private static final File FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

    private static final DatumAuthor AUTHOR = new DatumAuthor("Rizwan Choudrey", AuthorId.generateId());
    private static final String IMAGE_DIR = "Reviewer";
    private static final String DATABASE_NAME = "Reviewer.db";
    private static final String TEST_DATABASE_NAME = "TestReviewer.db";
    private static final int DATABASE_VER = 1;

    private Context mContext;
    private LaunchState mLaunchState;
    private ApplicationContext mApplicationContext;
    private static ApplicationLaunch sApplicationLaunch;

    public enum LaunchState {RELEASE, TEST}

    private ApplicationLaunch(Context context, LaunchState launchState) {
        mContext = context;
        mLaunchState = launchState;
        createApplicationContext();
        intialiseSingletons();
    }

    public static void intitialiseSingletons(Context context, LaunchState launchState) {
        if(sApplicationLaunch != null) {
            throw new RuntimeException("Can only have 1 new Instance!");
        }

        sApplicationLaunch = new ApplicationLaunch(context, launchState);
    }

    private void createApplicationContext() {
        String db = mLaunchState == LaunchState.TEST? TEST_DATABASE_NAME : DATABASE_NAME;
        FactoryApplicationContext contextFactory = new FactoryApplicationContext();

        mApplicationContext = contextFactory.newReleaseContext(mContext, AUTHOR, db, DATABASE_VER,
                FILE_DIR_EXT, IMAGE_DIR, getPersistencePlugin(), getLocationProviderPlugIn());
    }

    private void intialiseSingletons() {
        ApplicationInstance.newInstance(mContext, mApplicationContext);
    }


    @NonNull
    private PersistencePlugin getPersistencePlugin() {
        return new PersistenceAndroidSqlLite();
    }

    private LocationServicesPlugin getLocationProviderPlugIn() {
        return new LocationServicesGoogle();
    }
}
