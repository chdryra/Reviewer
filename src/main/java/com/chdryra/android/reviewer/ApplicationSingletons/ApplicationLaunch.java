/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins.Plugins;
import com.chdryra.android.reviewer.ApplicationContexts.Factories.FactoryApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReleaseDeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationLaunch {
    private static final DatumAuthor AUTHOR = new DatumAuthor("Rizwan Choudrey", new DatumUserId("123"));

    private Context mContext;
    private ApplicationContext mApplicationContext;
    private static ApplicationLaunch sApplicationLaunch;

    public enum LaunchState {RELEASE, TEST}

    private ApplicationLaunch(Context context, LaunchState launchState) {
        mContext = context;
        createApplicationContext(launchState);
        intialiseSingletons();
    }

    public static boolean intitialiseLaunchIfNecessary(Context context, LaunchState launchState) {
        boolean launched = false;
        if(sApplicationLaunch == null) {
            sApplicationLaunch = new ApplicationLaunch(context, launchState);
            launched = true;
        }

        return launched;
    }

    private void createApplicationContext(LaunchState launchState) {
        FactoryApplicationContext factory = new FactoryApplicationContext();

        mApplicationContext = factory.newReleaseContext(mContext, getDeviceContext(),
                Plugins.getPlugins(mContext, launchState), AUTHOR);
    }

    private void intialiseSingletons() {
        ApplicationInstance.newInstance(mContext, mApplicationContext);
    }

    private DeviceContext getDeviceContext() {
        return new ReleaseDeviceContext();
    }
}
