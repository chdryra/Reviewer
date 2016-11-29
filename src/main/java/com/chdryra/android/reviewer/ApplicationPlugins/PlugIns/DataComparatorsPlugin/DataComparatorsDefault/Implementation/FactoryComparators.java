/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryComparators {
    private static final String A_TO_Z = "A to Z";
    private static final String Z_TO_A = "Z to A";
    private static final String HIGH_TO_LOW = "High To Low";
    private static final String LOW_TO_HIGH = "Low To High";
    private static final String NEWEST = "Newest";
    private static final String OLDEST = "Oldest";
    private static final String TRUE = "True";
    private static final String FALSE = "False";
    private static final String SEPARATOR = ":";

    public <ObjectType, DataType> Comparator<ObjectType>
    newDataComparator(Comparator<DataType> comparator, DataGetter<ObjectType, ? extends DataType>
            getter) {
        return new DataComparator<>(comparator, getter);
    }

    public <T> NamedComparator<T> newAtoZ(String name, DataGetter<T, String> getter) {
        return newNamed(concat(name, A_TO_Z), concat(name, Z_TO_A), newAtoZ(), getter);
    }

    public <T> NamedComparator<T> newAscendingFloat(String name, DataGetter<T, Float> getter) {
        return newNamed(concat(name, LOW_TO_HIGH), concat(name, HIGH_TO_LOW),
                new FloatComparator(), getter);
    }

    public <T> NamedComparator<T> newDateAscending(String name,
                                                   DataGetter<T, ? extends DateTime> getter) {
        return newNamed(concat(name, NEWEST), concat(name, OLDEST), new DateComparator(), getter);
    }

    public <T> NamedComparator<T> newTrueThenFalse(String name, DataGetter<T, Boolean> getter) {
        return newNamed(concat(name, TRUE), concat(name, FALSE), new BooleanComparator(), getter);
    }

    public NamedComparator<DateTime> newDateAscending() {
        return newBuilder(OLDEST, new DateComparator()).withReverseName(NEWEST).build();
    }

    private <T, S> NamedComparator<T> newNamed(String compName,
                                               String reverseName,
                                               Comparator<S> comparator,
                                               DataGetter<T, ? extends S> getter) {
        Comparator<T> comp = newDataComparator(comparator, getter);
        return newBuilder(compName, comp).withReverseName(reverseName).build();
    }

    private String concat(String name, String order) {
        return name + SEPARATOR + " " + order;
    }

    @NonNull
    public <T> NamedComparator.Builder<T> newBuilder(String name, Comparator<T> comparator) {
        return new NamedComparator.Builder<>(name, comparator);
    }

    @NonNull
    private <T> ReverseComparator<T> reverse(Comparator<T> comparator) {
        return new ReverseComparator<>(comparator);
    }

    @NonNull
    private StringComparator newAtoZ() {
        return new StringComparator();
    }
}
