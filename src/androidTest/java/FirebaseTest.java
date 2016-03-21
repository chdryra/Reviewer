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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    }
    @Test
    public void testWrite(){
//        Firebase cloud = new Firebase(FIREBASE);
//        cloud.child("message").setValue("Do you have data? You'll love Firebase.");
    }
}
