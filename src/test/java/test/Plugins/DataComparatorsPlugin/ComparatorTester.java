package test.Plugins.DataComparatorsPlugin;

import java.util.Comparator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorTester<T> {
    private Comparator<T> mComparator;

    public ComparatorTester(Comparator<T> comparator) {
        mComparator = comparator;
    }

    public void testEquals(T lhs, T rhs) {
        assertThat(mComparator.compare(lhs, rhs), is(0));
        assertThat(mComparator.compare(rhs, lhs), is(0));
    }

    public void testFirstSecond(T first, T second) {
        assertThat(mComparator.compare(first, second), lessThan(0));
        assertThat(mComparator.compare(first, second), is(-mComparator.compare(second, first)));
    }
}
