/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Social;

import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static org.junit.Assert.fail;

/**
 * Created by: Rizwan Choudrey
 * On: 11/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TwitterTest {
    @Test
    public void test() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("S1cZ1sRDt4pbUP49MCduMt0IQ")
                .setOAuthConsumerSecret("mUhFe14zIdh1E3wg9kPrad2hsk6rHHlquZ2eFXjK1p5I3EmqsX")
                .setOAuthAccessToken("697073886572212224-B9lKIZPrHvgauqStLIsYpwV6tFiO1Wm")
                .setOAuthAccessTokenSecret("OuErvZFBY5CQrRbDlC40YC2Q7ijv36O8efV720b4JOkFx");

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try {
            twitter.updateStatus("test");
        } catch (TwitterException e) {
            fail();
            e.printStackTrace();
        }
    }
}
