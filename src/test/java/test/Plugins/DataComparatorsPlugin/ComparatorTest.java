/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ComparatorTest<T> {
    private Comparator<T> mComparator;

    public ComparatorTest(Comparator<T> comparator) {
        mComparator = comparator;
    }

    @NonNull
    protected ComparatorTester<T> newComparatorTester() {
        return new ComparatorTester<>(mComparator);
    }
}
