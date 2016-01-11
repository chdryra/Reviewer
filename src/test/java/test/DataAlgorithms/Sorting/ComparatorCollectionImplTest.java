package test.DataAlgorithms.Sorting;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Factories.DataComparatorsFactory;
import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Interfaces.ComparatorCollection;

import org.junit.Test;

import java.util.Comparator;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ComparatorCollectionImplTest<T> {
    private ComparatorCollection<T> mComparators;

    protected abstract ComparatorCollection<T> getComparators(DataComparatorsFactory factory);

    public ComparatorCollectionImplTest() {
        DataComparatorsFactory comparatorsFactory = new DataComparatorsFactory();
        mComparators = getComparators(comparatorsFactory);
    }

    protected Comparator<T> getDefaultComparator() {
        return mComparators.getDefault();
    }

    @NonNull
    protected ComparatorTester<T> newComparatorTester(Comparator<T> comparator) {
        return new ComparatorTester<>(comparator);
    }

    @Test
    public void testGetDefault() {
        assertThat(getDefaultComparator(), not(nullValue()));
    }

    @Test
    public void testNext() {
        int size = mComparators.size();
        Comparator<T> def = mComparators.getDefault();
        assertThat(def, not(nullValue()));
        assertThat(def, is(mComparators.next()));
        for (int i = 1; i < size; ++i) {
            Comparator<T> comparator = mComparators.next();
            assertThat(comparator, not(nullValue()));
            assertThat(def, not(comparator));
        }
    }
}
