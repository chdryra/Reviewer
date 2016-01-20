package test.DataAlgorithms.DataSorting;

import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation.UrlLabelComparator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.net.URL;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UrlLabelComparatorTest extends ComparatorTest<DataUrl>{
    public UrlLabelComparatorTest() {
        super(new UrlLabelComparator());
    }

    @Test
    public void labelAlphabeticalAscendingFirst_AscendingValueSecond() {
        DataUrl AA = new DatumUrl(RandomReviewId.nextReviewId(), RandomString.toRandomCase("A"), RandomString.toRandomCase("A"));
        DataUrl AZ = new DatumUrl(RandomReviewId.nextReviewId(), RandomString.toRandomCase("A"), RandomString.toRandomCase("Z"));
        DataUrl BC = new DatumUrl(RandomReviewId.nextReviewId(), RandomString.toRandomCase("B"), RandomString.toRandomCase("C"));
        DataUrl CA = new DatumUrl(RandomReviewId.nextReviewId(), RandomString.toRandomCase("C"), RandomString.toRandomCase("A"));

        ComparatorTester<DataUrl> tester = newComparatorTester();
        tester.testFirstSecond(AA, AZ);
        tester.testFirstSecond(AZ, BC);
        tester.testFirstSecond(BC, CA);
        tester.testFirstSecond(AA, CA);
    }

    @Test
    public void labelAlphabeticalAscendingDifferentFirstLetter_SameValue() {
        String value = RandomString.nextWord();
        DataUrl urlA = new DatumUrl(RandomReviewId.nextReviewId(), "A" + RandomString.nextWord(), RandomString.toRandomCase(value));
        DataUrl urlB = new DatumUrl(RandomReviewId.nextReviewId(), "B" + RandomString.nextWord(), RandomString.toRandomCase(value));
        DataUrl urlC = new DatumUrl(RandomReviewId.nextReviewId(), "C" + RandomString.nextWord(), RandomString.toRandomCase(value));

        ComparatorTester<DataUrl> tester = newComparatorTester();
        tester.testFirstSecond(urlA, urlB);
        tester.testFirstSecond(urlB, urlC);
        tester.testFirstSecond(urlA, urlC);
    }

    @Test
    public void valueAscending_SameLabel() {
        String label = RandomString.nextWord();
        DataUrl url1 = new DatumUrl(RandomReviewId.nextReviewId(), RandomString.toRandomCase(label), "A");
        DataUrl url2 = new DatumUrl(RandomReviewId.nextReviewId(), RandomString.toRandomCase(label), "B");
        DataUrl url3 = new DatumUrl(RandomReviewId.nextReviewId(), RandomString.toRandomCase(label), "C");

        ComparatorTester<DataUrl> tester = newComparatorTester();
        tester.testFirstSecond(url1, url2);
        tester.testFirstSecond(url2, url3);
        tester.testFirstSecond(url1, url3);
    }

    @Test
    public void labelAlphabeticalAscendingStartingLabelStemSame_SameValue() {
        String label1 = RandomString.nextWord();
        String label2 = label1 + RandomString.nextWord();
        String value = RandomString.nextWord();
        DataUrl url1 = new DatumUrl(RandomReviewId.nextReviewId(), label1, value.toLowerCase());
        DataUrl url2 = new DatumUrl(RandomReviewId.nextReviewId(), label2, value.toUpperCase());

        ComparatorTester<DataUrl> tester = newComparatorTester();
        if(label1.compareToIgnoreCase(label2) < 0) {
            tester.testFirstSecond(url1, url2);
        } else if(label1.compareToIgnoreCase(label2) > 0){
            tester.testFirstSecond(url2, url1);
        } else {
            tester.testEquals(url1, url2);
        }
    }

    @Test
    public void comparatorEqualitySameLabel_SameValue() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        DataUrl url1 = new DatumUrl(RandomReviewId.nextReviewId(), label, value);
        DataUrl url2 = new DatumUrl(RandomReviewId.nextReviewId(), label, value);

        ComparatorTester<DataUrl> tester = newComparatorTester();
        tester.testEquals(url1, url1);
        tester.testEquals(url1, url2);
    }

    @Test
    public void comparatorEqualityIgnoresLabelCase_SameValue() {
        String label = RandomString.nextWord();
        String value  = RandomString.nextWord();
        DataUrl url = new DatumUrl(RandomReviewId.nextReviewId(), label, value);
        DataUrl urlLower = new DatumUrl(RandomReviewId.nextReviewId(), label.toLowerCase(), value.toUpperCase());
        DataUrl urlUpper = new DatumUrl(RandomReviewId.nextReviewId(), label.toUpperCase(), value.toLowerCase());

        ComparatorTester<DataUrl> tester = newComparatorTester();
        tester.testEquals(url, urlLower);
        tester.testEquals(url, urlUpper);
    }

    private class DatumUrl extends DatumFact implements DataUrl{
        public DatumUrl(ReviewId reviewId, String label, String value) {
            super(reviewId, label, value);
        }

        @Override
        public URL getUrl() {
            return null;
        }

        @Override
        public boolean isUrl() {
            return true;
        }
    }
}
