/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.ComparatorCollectionImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorCollectionImplTest {
    private ComparatorCollectionImpl<String> mComparators;
    private Comparator<String> mAscending;
    private Comparator<String> mDescending;

    @Before
    public void setUp() {
        mAscending = new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareToIgnoreCase(rhs);
            }
        };

        mDescending = new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return rhs.compareToIgnoreCase(lhs);
            }
        };

        mComparators = new Comparators();
    }

    @Test
    public void testGetDefault() {
        assertThat(mComparators.getDefault(), is(mAscending));
    }

    @Test
    public void testNext() {
        int size = mComparators.size();
        assertThat(size, is(2));

        Comparator<String> def = mComparators.getDefault();
        assertThat(def, is(mComparators.next()));
        for (int i = 1; i < size; ++i) {
            Comparator<String> comparator = mComparators.next();
            assertThat(comparator, is(mDescending));
        }
        assertThat(mComparators.next(), is(mAscending));
    }

    private class Comparators extends ComparatorCollectionImpl<String> {
        public Comparators() {
            super(mAscending);
            add(mDescending);
        }
    }
}
