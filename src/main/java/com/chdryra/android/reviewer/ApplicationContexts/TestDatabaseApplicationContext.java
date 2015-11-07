package com.chdryra.android.reviewer.ApplicationContexts;

import android.content.Context;

/**
 * Created by: Rizwan Choudrey
 * On: 06/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TestDatabaseApplicationContext extends ReleaseApplicationContext{
    private static final String DATABASE_NAME = "TestReviewer.db";
    private static final int DATABASE_VER = 1;

    public TestDatabaseApplicationContext(Context context) {
        super(context, DATABASE_NAME, DATABASE_VER);
    }
}
