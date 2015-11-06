package com.chdryra.android.reviewer.ApplicationContexts;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationLaunch {
    public static boolean sTestState = false;

    public static void setReleaseLaunchState() {
        sTestState = false;
    }

    public static void setTestLaunchState() {
        sTestState = true;
    }

    public static ApplicationContext initialiseLaunchState(Context context) {
        ApplicationContext applicationContext;
        if(sTestState) {
            applicationContext = new TestDatabaseApplicationContext(context);
        } else {
            applicationContext = new ReleaseApplicationContext(context);
        }
        createLaunchState(context, applicationContext);
        return applicationContext;
    }

    private static void createLaunchState(Context context, ApplicationContext applicationContext) {
        Administrator admin = Administrator.createWithApplicationContext(context, applicationContext);
        Administrator.setAsAdministrator(admin);
    }
}
