/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class NamedComparator<T> implements Comparator<T> {
    private final String mName;
    private final Comparator<T> mComparator;
    private final List<Comparator<T>> mTieBreakers;
    private String mReverseName;

    private NamedComparator(String name,
                            String reverseName,
                            Comparator<T> comparator,
                            List<Comparator<T>> tieBreakers) {
        mName = name;
        mReverseName = reverseName;
        mComparator = comparator;
        mTieBreakers = tieBreakers;
    }

    public String getName() {
        return mName;
    }

    public String getReverseName() {
        return mReverseName;
    }
    public NamedComparator<T> reverse() {
        return reverse(true);
    }

    public NamedComparator<T> reverse(boolean reverseTieBreakers) {
        ReverseComparator<T> comparator =
                new ReverseComparator<>(reverseTieBreakers ? this : mComparator);
        List<Comparator<T>> tieBreakers = reverseTieBreakers ? new ArrayList<Comparator<T>>() : mTieBreakers;
        return new NamedComparator<>(mReverseName, mName, comparator, tieBreakers);
    }

    @Override
    public int compare(T lhs, T rhs) {
        int comp = mComparator.compare(lhs, rhs);
        if (comp == 0) {
            for(Comparator<T> comparator : mTieBreakers)
                comp = comparator.compare(lhs, rhs);
        }

        return comp;
    }

    public static class Builder<T> {
        private final String mName;
        private final Comparator<T> mComparator;

        private String mReverseName;
        private ArrayList<Comparator<T>> mTieBreakers;

        public Builder(String name, Comparator<T> comparator) {
            mName = name;
            mComparator = comparator;
            mReverseName = mName + ": Reversed";
            mTieBreakers = new ArrayList<>();
        }

        public Builder<T> withReverseName(String reverseName) {
            mReverseName = reverseName;
            return this;
        }

        public Builder<T> addTieBreaker(Comparator<T> tieBreaker) {
            mTieBreakers.add(tieBreaker);
            return this;
        }

        public NamedComparator<T> build() {
            return new NamedComparator<>(mName, mReverseName, mComparator, mTieBreakers);
        }
    }
}
