package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;
import android.os.Environment;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation
        .ReleaseApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.Models.UserModel.UserId;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationLaunch {
    private static final File FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

    private static final Author AUTHOR = new Author("Rizwan Choudrey", UserId
            .generateId());
    private static final String DATABASE_NAME = "Reviewer.db";
    private static final String TEST_DATABASE_NAME = "TestReviewer.db";
    private static final int DATABASE_VER = 1;

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

    private void createApplicationContext() {
        String db = DATABASE_NAME;
        if(mLaunchState == LaunchState.TEST) {
            db = TEST_DATABASE_NAME;
        }

        mApplicationContext = new ReleaseApplicationContext(mContext, AUTHOR, FILE_DIR_EXT, db, DATABASE_VER);
    }

    private void intialiseSingeltons() {
        ApplicationInstance.createWithApplicationContext(mContext, mApplicationContext);
    }
}
