package test.DataAlgorithms.Sorting;

import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Implementation.SocialMostFollowersThenAlphabetical;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialMostFollowersThenAlphabeticalTest extends ComparatorTest<DataSocialPlatform>{
    private static final Random RAND = new Random();

    public SocialMostFollowersThenAlphabeticalTest() {
        super(new SocialMostFollowersThenAlphabetical());
    }

    @Test
    public void isFollowersFirstThenAlphabeticalAscendingName() {
        DataSocialPlatform platformF200 = new Platform("F", 200);
        DataSocialPlatform platformD100 = new Platform("D", 100);
        DataSocialPlatform platformE100 = new Platform("E", 100);
        DataSocialPlatform platformB50 = new Platform("B", 50);
        DataSocialPlatform platformA10 = new Platform("A", 10);

        ComparatorTester<DataSocialPlatform> tester = newComparatorTester();
        tester.testFirstSecond(platformF200, platformD100);
        tester.testFirstSecond(platformD100, platformE100);
        tester.testFirstSecond(platformE100, platformB50);
        tester.testFirstSecond(platformB50, platformA10);
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter_SameIsHeadline() {
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
    public void alphabeticalAscendingStartingNameStemSame_SameFollowers() {
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
    public void comparatorEqualitySameFollowersSameName() {
        String name = RandomString.nextSentence();
        int followers = Math.abs(RAND.nextInt());
        DataSocialPlatform platform1 = new Platform(name, followers);
        DataSocialPlatform platform2 = new Platform(name, followers);

        ComparatorTester<DataSocialPlatform> tester = newComparatorTester();
        tester.testEquals(platform1, platform1);
        tester.testEquals(platform1, platform2);
    }

    @Test
    public void comparatorEqualitySameFollowersSameNameRegardlessOfCase() {
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
        public int getFollowers() {
            return mFollowers;
        }
    }
}
