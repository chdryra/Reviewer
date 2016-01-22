package test.DataAlgorithms.DataSorting;

import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.TagAlphabetical;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TagAlphabeticalTest extends ComparatorTest<DataTag>{
    public TagAlphabeticalTest() {
        super(new TagAlphabetical());
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter() {
        DataTag A = new DatumTag(RandomReviewId.nextReviewId(), "A" + RandomString.nextWord());
        DataTag B = new DatumTag(RandomReviewId.nextReviewId(), "B" + RandomString.nextWord());
        DataTag C = new DatumTag(RandomReviewId.nextReviewId(), "C" + RandomString.nextWord());

        ComparatorTester<DataTag> tester = newComparatorTester();
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }

    @Test
    public void alphabeticalAscendingStartingStemSame() {
        String string1 = RandomString.nextWord();
        String string2 = string1 + RandomString.nextWord();
        DataTag tag1 = new DatumTag(RandomReviewId.nextReviewId(), string1);
        DataTag tag2 = new DatumTag(RandomReviewId.nextReviewId(), string2);

        ComparatorTester<DataTag> tester = newComparatorTester();
        if(string1.compareToIgnoreCase(string2) < 0) {
            tester.testFirstSecond(tag1, tag2);
        } else if(string1.compareToIgnoreCase(string2) > 0){
            tester.testFirstSecond(tag2, tag1);
        } else {
            tester.testEquals(tag2, tag1);
        }
    }

    @Test
    public void comparatorEquality() {
        String string = RandomString.nextWord();
        DataTag tag1 = new DatumTag(RandomReviewId.nextReviewId(), string);
        DataTag tag2 = new DatumTag(RandomReviewId.nextReviewId(), string);

        ComparatorTester<DataTag> tester = newComparatorTester();
        tester.testEquals(tag1, tag1);
        tester.testEquals(tag1, tag2);
    }

    @Test
    public void comparatorEqualityIgnoresCase() {
        String string = RandomString.nextWord();
        DataTag tag = new DatumTag(RandomReviewId.nextReviewId(), string);
        DataTag tagLower = new DatumTag(RandomReviewId.nextReviewId(), string.toLowerCase());
        DataTag tagUpper = new DatumTag(RandomReviewId.nextReviewId(), string.toUpperCase());

        ComparatorTester<DataTag> tester = newComparatorTester();
        tester.testEquals(tag, tagLower);
        tester.testEquals(tag, tagUpper);
    }
}
