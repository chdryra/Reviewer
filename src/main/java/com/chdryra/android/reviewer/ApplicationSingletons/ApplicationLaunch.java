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
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.facebook.FacebookSdk;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationLaunch {
    private static final DatumAuthor AUTHOR = new DatumAuthor("Rizwan Choudrey", new DatumUserId("123"));
    private static final AuthorsStamp AUTHORS_STAMP = new AuthorsStamp(AUTHOR);


    private Context mContext;
    private ApplicationContext mApplicationContext;
    private static ApplicationLaunch sApplicationLaunch;

    public enum LaunchState {RELEASE, TEST}

    private ApplicationLaunch(Context context, LaunchState launchState) {
        mContext = context;
        createApplicationContext(launchState);
        intialiseSingletons();
        FacebookSdk.sdkInitialize(context);
    }

    public static void intitialiseLaunchIfNecessary(Context context, LaunchState launchState) {
        if(sApplicationLaunch == null) {
            sApplicationLaunch = new ApplicationLaunch(context, launchState);
        }
    }

    private void createApplicationContext(LaunchState launchState) {
        FactoryApplicationContext factory = new FactoryApplicationContext();

        mApplicationContext = factory.newReleaseContext(mContext, getDeviceContext(),
                Plugins.getPlugins(mContext, launchState));
        setUser();
    }

    private void setUser() {
        mApplicationContext.getContext().getReviewsFactory().setAuthorsStamp(AUTHORS_STAMP);
    }

    private void intialiseSingletons() {
        ApplicationInstance.newInstance(mContext, mApplicationContext);
    }

    private DeviceContext getDeviceContext() {
        return new ReleaseDeviceContext(mContext);
    }
}
