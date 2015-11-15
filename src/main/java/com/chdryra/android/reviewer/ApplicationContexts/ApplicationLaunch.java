package com.chdryra.android.reviewer.ApplicationContexts;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationLaunch {
    private Context mContext;
    private LaunchState mLaunchState;
    private ApplicationContext mApplicationContext;
    private static ApplicationLaunch sApplicationLaunch;

    public enum LaunchState {RELEASE, TEST};

    private ApplicationLaunch(Context context, LaunchState launchState) {
        mContext = context;
        mLaunchState = launchState;
        createApplicationContext();
        intialiseSingeltons();
    }

    public static void intitialiseSingletons(Context context, LaunchState launchState) {
        if(sApplicationLaunch != null) {
            throw new RuntimeException("Can only have 1 newInstance!");
        }

        sApplicationLaunch = new ApplicationLaunch(context, launchState);
    }

    private ApplicationLaunch createApplicationContext() {
        if(mLaunchState == LaunchState.TEST) {
            mApplicationContext = new TestDatabaseApplicationContext(mContext);
        } else if(mLaunchState == LaunchState.RELEASE){
            mApplicationContext = new ReleaseApplicationContext(mContext);
        } else {
            throw new RuntimeException("LaunchState not recognised!");
        }

        return this;
    }

    private void intialiseSingeltons() {
        Administrator.createWithApplicationContext(mContext, mApplicationContext);
    }
}
