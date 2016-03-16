/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.firebase.client.Firebase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by: Rizwan Choudrey
 * On: 15/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(AndroidJUnit4.class)
public class FirebaseTest extends InstrumentationTestCase{
    private static final String FIREBASE = "https://fiery-heat-1802.firebaseio.com/";

    @Override
    @Before
    public void setUp() {
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        Firebase.setAndroidContext(getInstrumentation().getTargetContext());
    }

    @Test
    public void testWrite(){
        Firebase cloud = new Firebase(FIREBASE);
        cloud.child("message").setValue("Do you have data? You'll love Firebase.");
    }
}
