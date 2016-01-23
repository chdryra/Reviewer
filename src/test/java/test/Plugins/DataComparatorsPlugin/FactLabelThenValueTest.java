package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.FactLabelThenValue;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactLabelThenValueTest extends ComparatorTest<DataFact>{
    public FactLabelThenValueTest() {
        super(new FactLabelThenValue());
    }

    @Test
    public void labelAlphabeticalAscendingFirst_AscendingValueSecond() {
        DataFact AA = new DatumFact(RandomReviewId.nextReviewId(), RandomString.toRandomCase("A"), RandomString.toRandomCase("A"));
        DataFact AZ = new DatumFact(RandomReviewId.nextReviewId(), RandomString.toRandomCase("A"), RandomString.toRandomCase("Z"));
        DataFact BC = new DatumFact(RandomReviewId.nextReviewId(), RandomString.toRandomCase("B"), RandomString.toRandomCase("C"));
        DataFact CA = new DatumFact(RandomReviewId.nextReviewId(), RandomString.toRandomCase("C"), RandomString.toRandomCase("A"));

        ComparatorTester<DataFact> tester = newComparatorTester();
        tester.testFirstSecond(AA, AZ);
        tester.testFirstSecond(AZ, BC);
        tester.testFirstSecond(BC, CA);
        tester.testFirstSecond(AA, CA);
    }

    @Test
    public void labelAlphabeticalAscendingDifferentFirstLetter_SameValue() {
        String value = RandomString.nextWord();
        DataFact factA = new DatumFact(RandomReviewId.nextReviewId(), "A" + RandomString.nextWord(), RandomString.toRandomCase(value));
        DataFact factB = new DatumFact(RandomReviewId.nextReviewId(), "B" + RandomString.nextWord(), RandomString.toRandomCase(value));
        DataFact factC = new DatumFact(RandomReviewId.nextReviewId(), "C" + RandomString.nextWord(), RandomString.toRandomCase(value));

        ComparatorTester<DataFact> tester = newComparatorTester();
        tester.testFirstSecond(factA, factB);
        tester.testFirstSecond(factB, factC);
        tester.testFirstSecond(factA, factC);
    }

    @Test
    public void valueAscending_SameLabel() {
        String label = RandomString.nextWord();
        DataFact fact1 = new DatumFact(RandomReviewId.nextReviewId(), RandomString.toRandomCase(label), "A");
        DataFact fact2 = new DatumFact(RandomReviewId.nextReviewId(), RandomString.toRandomCase(label), "B");
        DataFact fact3 = new DatumFact(RandomReviewId.nextReviewId(), RandomString.toRandomCase(label), "C");

        ComparatorTester<DataFact> tester = newComparatorTester();
        tester.testFirstSecond(fact1, fact2);
        tester.testFirstSecond(fact2, fact3);
        tester.testFirstSecond(fact1, fact3);
    }

    @Test
    public void labelAlphabeticalAscendingStartingLabelStemSame_SameValue() {
        String label1 = RandomString.nextWord();
        String label2 = label1 + RandomString.nextWord();
        String value = RandomString.nextWord();
        DataFact fact1 = new DatumFact(RandomReviewId.nextReviewId(), label1, value.toLowerCase());
        DataFact fact2 = new DatumFact(RandomReviewId.nextReviewId(), label2, value.toUpperCase());

        ComparatorTester<DataFact> tester = newComparatorTester();
        if(label1.compareToIgnoreCase(label2) < 0) {
            tester.testFirstSecond(fact1, fact2);
        } else if(label1.compareToIgnoreCase(label2) > 0){
            tester.testFirstSecond(fact2, fact1);
        } else {
            tester.testEquals(fact1, fact2);
        }
    }

    @Test
    public void comparatorEqualitySameLabel_SameValue() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        DataFact fact1 = new DatumFact(RandomReviewId.nextReviewId(), label, value);
        DataFact fact2 = new DatumFact(RandomReviewId.nextReviewId(), label, value);

        ComparatorTester<DataFact> tester = newComparatorTester();
        tester.testEquals(fact1, fact1);
        tester.testEquals(fact1, fact2);
    }

    @Test
    public void comparatorEqualityIgnoresLabelCase_SameValue() {
        String label = RandomString.nextWord();
        String value  = RandomString.nextWord();
        DataFact fact = new DatumFact(RandomReviewId.nextReviewId(), label, value);
        DataFact factLower = new DatumFact(RandomReviewId.nextReviewId(), label.toLowerCase(), value.toUpperCase());
        DataFact factUpper = new DatumFact(RandomReviewId.nextReviewId(), label.toUpperCase(), value.toLowerCase());

        ComparatorTester<DataFact> tester = newComparatorTester();
        tester.testEquals(fact, factLower);
        tester.testEquals(fact, factUpper);
    }
}
