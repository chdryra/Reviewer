package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Factories.FactoryApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins.Plugins;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseDeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationLaunch {
    private static final DatumAuthor AUTHOR = new DatumAuthor("Rizwan Choudrey", AuthorId.generateId());

    private Context mContext;
    private ApplicationContext mApplicationContext;
    private static ApplicationLaunch sApplicationLaunch;

    public enum LaunchState {RELEASE, TEST}

    private ApplicationLaunch(Context context, LaunchState launchState) {
        mContext = context;
        createApplicationContext(launchState);
        intialiseSingletons();
    }

    public static void intitialiseSingletons(Context context, LaunchState launchState) {
        if(sApplicationLaunch != null) {
            throw new RuntimeException("Can only have 1 new Instance!");
        }

        sApplicationLaunch = new ApplicationLaunch(context, launchState);
    }

    private void createApplicationContext(LaunchState launchState) {
        FactoryApplicationContext contextFactory = new FactoryApplicationContext();

        mApplicationContext
                = contextFactory.newReleaseContext(mContext, AUTHOR,
                getDeviceContext(),
                Plugins.getPlugins(launchState));
    }

    private void intialiseSingletons() {
        ApplicationInstance.newInstance(mContext, mApplicationContext);
    }

    private DeviceContext getDeviceContext() {
        return new ReleaseDeviceContext();
    }
}
