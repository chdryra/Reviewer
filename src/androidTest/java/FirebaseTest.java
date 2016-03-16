/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ReviewerApp;
import com.firebase.client.Firebase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

/**
 * Created by: Rizwan Choudrey
 * On: 15/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(AndroidJUnit4.class)
public class FirebaseTest extends ApplicationTestCase<ReviewerApp> {
    private static final String FIREBASE = "https://fiery-heat-1802.firebaseio.com/";
    private static ReviewerApp mApplication;

    public FirebaseTest() {
        super(ReviewerApp.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createApplication();
        if (mApplication == null) {
            mApplication = getApplication();
        }

        if (mApplication == null) {
            mApplication = (ReviewerApp) getContext().getApplicationContext();
            assertNotNull(mApplication);
            long start = System.currentTimeMillis();
            while (!mApplication.isInitialised()){
                Thread.sleep(300);  //wait until FireBase is totally initialized
                if ( (System.currentTimeMillis() - start ) >= 1000 )
                    throw new TimeoutException(this.getClass().getName() +"Setup timeOut");
            }
        }
    }
    @Test
    public void testWrite(){
        Firebase cloud = new Firebase(FIREBASE);
        cloud.child("message").setValue("Do you have data? You'll love Firebase.");
    }
}
