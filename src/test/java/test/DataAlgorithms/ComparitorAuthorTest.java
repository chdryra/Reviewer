package test.DataAlgorithms;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorAuthor;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.TestUtils.RandomAuthor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorAuthorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ComparitorAuthor mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparitorAuthor();
    }

    @Test
    public void sameAuthorReturnsDifferenceBooleanOfFalse() {
        DataAuthor lhs = RandomAuthor.nextAuthor();
        DataAuthor rhs = new DatumAuthor(lhs.getName(), lhs.getUserId());
        checkComparisonHasDifference(lhs, rhs, false);
    }

    @Test
    public void differentAuthorsReturnsDifferenceBooleanOfTrue() {
        DataAuthor lhs = RandomAuthor.nextAuthor();
        DataAuthor rhs = RandomAuthor.nextAuthor();
        checkComparisonHasDifference(lhs, rhs, true);
    }

    @Test
    public void sameNamesDifferentUserIdsThrowsRuntimeException() {
        expectedException.expect(RuntimeException.class);
        DataAuthor lhs = RandomAuthor.nextAuthor();
        DataAuthor rhs = RandomAuthor.nextAuthor();
        mComparitor.compare(lhs, new DatumAuthor(lhs.getName(), rhs.getUserId()));
    }

    @Test
    public void differentNamesSameUserIdsThrowsRuntimeException() {
        expectedException.expect(RuntimeException.class);
        DataAuthor lhs = RandomAuthor.nextAuthor();
        DataAuthor rhs = RandomAuthor.nextAuthor();
        mComparitor.compare(lhs, new DatumAuthor(rhs.getName(), lhs.getUserId()));
    }

    private void checkComparisonHasDifference(DataAuthor lhs, DataAuthor rhs, boolean
            expected) {
        DifferenceBoolean calculated1 = mComparitor.compare(lhs, rhs);
        DifferenceBoolean calculated2 = mComparitor.compare(rhs, lhs);
        DifferenceBoolean expectedDiff = new DifferenceBoolean(expected);
        assertThat(calculated1.lessThanOrEqualTo(expectedDiff), is(true));
        assertThat(calculated2.lessThanOrEqualTo(expectedDiff), is(true));
    }
}
