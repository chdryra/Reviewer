package com.chdryra.android.startouch.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

import junit.framework.TestCase;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ComparatorCollectionTest<T extends GvData> extends TestCase {
    protected ComparatorCollection<T> mComparators;

    //Constructors
    public ComparatorCollectionTest(ComparatorCollection<T> collection) {
        mComparators = collection;
    }

    @SmallTest
    public void testGetDefault() {
        assertNotNull(mComparators.getDefault());
    }

    @SmallTest
    public void testNext() {
        int size = mComparators.size();
        Comparator<T> def = mComparators.getDefault();
        assertNotNull(def);
        assertEquals(def, mComparators.next());
        for (int i = 1; i < size; ++i) {
            Comparator<T> comparator = mComparators.next();
            assertNotNull(comparator);
            assertFalse(def.equals(comparator));
        }
        assertEquals(def, mComparators.next());
    }
}
