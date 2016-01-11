package test.DataAlgorithms.Sorting;

import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Factories.DataComparatorsFactory;
import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Interfaces.ComparatorCollection;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.AuthorId;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorComparatorsTest extends ComparatorCollectionImplTest<DataAuthor>{

    @Override
    protected ComparatorCollection<DataAuthor> getComparators(DataComparatorsFactory factory) {
        return factory.getAuthorComparators();
    }

    @Test
    public void defaultComparatorIsAlphabeticalAscendingDifferentFirstLetter() {
        Comparator<DataAuthor> comparator = getDefaultComparator();
        DataAuthor A = new DatumAuthor("A" + RandomString.nextWord(), AuthorId.generateId());
        DataAuthor B = new DatumAuthor("B" + RandomString.nextWord(), AuthorId.generateId());
        DataAuthor C = new DatumAuthor("C" + RandomString.nextWord(), AuthorId.generateId());

        ComparatorTester<DataAuthor> tester = newComparatorTester(comparator);
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }

    @Test
    public void defaultComparatorIsAlphabeticalAscendingStartingStemSame() {
        Comparator<DataAuthor> comparator = getDefaultComparator();
        String name1 = RandomString.nextWord();
        String name2 = name1 + RandomString.nextWord();
        DataAuthor author1 = new DatumAuthor(name1, AuthorId.generateId());
        DataAuthor author2 = new DatumAuthor(name2, AuthorId.generateId());

        ComparatorTester<DataAuthor> tester = newComparatorTester(comparator);
        if(name1.compareToIgnoreCase(name2) < 0) {
            tester.testFirstSecond(author1, author2);
        } else if(name1.compareToIgnoreCase(name2) > 0){
            tester.testFirstSecond(author2, author1);
        } else {
            tester.testEquals(author2, author1);
        }
    }

    @Test
    public void defaultComparatorEquality() {
        Comparator<DataAuthor> comparator = getDefaultComparator();
        String name = RandomString.nextWord();
        DataAuthor author = new DatumAuthor(name, AuthorId.generateId());
        DataAuthor author2 = new DatumAuthor(name, AuthorId.generateId());

        ComparatorTester<DataAuthor> tester = newComparatorTester(comparator);
        tester.testEquals(author, author);
        tester.testEquals(author, author2);
    }

    @Test
    public void defaultComparatorEqualityIgnoresCase() {
        Comparator<DataAuthor> comparator = getDefaultComparator();
        String name = RandomString.nextWord();
        DataAuthor author = new DatumAuthor(name, AuthorId.generateId());
        DataAuthor authorLower = new DatumAuthor(name.toLowerCase(), AuthorId.generateId());
        DataAuthor authorUpper = new DatumAuthor(name.toUpperCase(), AuthorId.generateId());

        ComparatorTester<DataAuthor> tester = newComparatorTester(comparator);
        tester.testEquals(author, authorLower);
        tester.testEquals(author, authorUpper);
    }
}
