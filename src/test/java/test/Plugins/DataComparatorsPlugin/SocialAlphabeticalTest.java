/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.SocialAlphabetical;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialAlphabeticalTest extends ComparatorTest<DataSocialPlatform>{
    private static final Random RAND = new Random();

    public SocialAlphabeticalTest() {
        super(new SocialAlphabetical());
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter() {
        int followers = Math.abs(RAND.nextInt());
        DataSocialPlatform platformA = new Platform("a" + RandomString.nextWord(), followers);
        DataSocialPlatform platformB = new Platform("B" + RandomString.nextWord(), followers);
        DataSocialPlatform platformC = new Platform("C" + RandomString.nextWord(), followers);

        ComparatorTester<DataSocialPlatform> tester = newComparatorTester();
        tester.testFirstSecond(platformA, platformB);
        tester.testFirstSecond(platformB, platformC);
        tester.testFirstSecond(platformA, platformC);
    }

    @Test
    public void alphabeticalAscendingStartingNameStemSame() {
        String name1 = RandomString.nextSentence();
        String name2 = name1 + RandomString.nextSentence();
        int followers = Math.abs(RAND.nextInt());
        DataSocialPlatform platform1 = new Platform(name1, followers);
        DataSocialPlatform platform2 = new Platform(name2, followers);

        ComparatorTester<DataSocialPlatform> tester = newComparatorTester();
        if(name1.compareToIgnoreCase(name2) < 0) {
            tester.testFirstSecond(platform1, platform2);
        } else if(name1.compareToIgnoreCase(name2) > 0){
            tester.testFirstSecond(platform2, platform1);
        } else {
            tester.testEquals(platform1, platform2);
        }
    }

    @Test
    public void comparatorEqualitySameName() {
        String name = RandomString.nextSentence();
        int followers = Math.abs(RAND.nextInt());
        DataSocialPlatform platform1 = new Platform(name, followers);
        DataSocialPlatform platform2 = new Platform(name, followers);

        ComparatorTester<DataSocialPlatform> tester = newComparatorTester();
        tester.testEquals(platform1, platform1);
        tester.testEquals(platform1, platform2);
    }

    @Test
    public void comparatorEqualitySameNameRegardlessOfCase() {
        String name = RandomString.nextSentence();
        int followers = Math.abs(RAND.nextInt());
        DataSocialPlatform platform1 = new Platform(name, followers);
        DataSocialPlatform platformLower = new Platform(name.toLowerCase(), followers);
        DataSocialPlatform platformUpper = new Platform(name.toUpperCase(), followers);

        ComparatorTester<DataSocialPlatform> tester = newComparatorTester();
        tester.testEquals(platform1, platformLower);
        tester.testEquals(platformLower, platformUpper);
    }

    private class Platform implements DataSocialPlatform {
        private String mName;
        private int mFollowers;

        public Platform(String name, int followers) {
            mName = name;
            mFollowers = followers;
        }

        @Override
        public String getName() {
            return mName;
        }

        @Override
        public void getFollowers(FollowersListener listener) {
            listener.onNumberFollowers(mFollowers);
        }
    }
}
