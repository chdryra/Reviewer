/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.Comparators.BooleanComparator;
import com.chdryra.android.mygenerallibrary.Comparators.DataComparator;
import com.chdryra.android.mygenerallibrary.Comparators.DataGetter;
import com.chdryra.android.mygenerallibrary.Comparators.FloatComparator;
import com.chdryra.android.mygenerallibrary.Comparators.NamedComparator;
import com.chdryra.android.mygenerallibrary.Comparators.StringComparator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;

import java.util.Comparator;

import static com.chdryra.android.startouch.Application.Implementation.Strings.Comparators.A_TO_Z;
import static com.chdryra.android.startouch.Application.Implementation.Strings.Comparators.FALSE;
import static com.chdryra.android.startouch.Application.Implementation.Strings.Comparators.HIGH_TO_LOW;
import static com.chdryra.android.startouch.Application.Implementation.Strings.Comparators.LOW_TO_HIGH;
import static com.chdryra.android.startouch.Application.Implementation.Strings.Comparators.NEWEST;
import static com.chdryra.android.startouch.Application.Implementation.Strings.Comparators.OLDEST;
import static com.chdryra.android.startouch.Application.Implementation.Strings.Comparators.SEPARATOR;
import static com.chdryra.android.startouch.Application.Implementation.Strings.Comparators.TRUE;
import static com.chdryra.android.startouch.Application.Implementation.Strings.Comparators.Z_TO_A;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryComparators {
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
        return name.length() > 0 ? name + SEPARATOR + " " + order : order;
    }

    @NonNull
    public <T> NamedComparator.Builder<T> newBuilder(String name, Comparator<T> comparator) {
        return new NamedComparator.Builder<>(name, comparator);
    }

    @NonNull
    private StringComparator newAtoZ() {
        return new StringComparator();
    }
}
