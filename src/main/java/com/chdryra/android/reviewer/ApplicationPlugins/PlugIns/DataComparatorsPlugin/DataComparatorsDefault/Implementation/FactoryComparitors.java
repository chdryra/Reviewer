/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryComparitors {
    public <ObjectType, DataType> Comparator<ObjectType>
    newDataComparator(Comparator<DataType> comparator, DataGetter<ObjectType, ? extends DataType> getter) {
        return new DataComparator<>(comparator, getter);
    }
    public <T> Comparator<T> newAtoZ(DataGetter<T, String> getter) {
        return newDataComparator(newAtoZ(), getter);
    }

    public <T> Comparator<T> newZtoA(DataGetter<T, String> getter) {
        return newDataComparator(newZtoA(), getter);
    }

    public <T> Comparator<T> newAscendingFloat(DataGetter<T, Float> getter) {
        return newDataComparator(new FloatComparator(Ordering.ASCENDING), getter);
    }

    public <T> Comparator<T> newDescendingFloat(DataGetter<T, Float> getter) {
        return newDataComparator(new FloatComparator(Ordering.DESCENDING), getter);
    }

    public <T> Comparator<T> newMostRecentFirst(DataGetter<T, ? extends DateTime> getter) {
        return newDataComparator(new DateComparator(Ordering.DESCENDING), getter);
    }
    
    public <T> Comparator<T> newTrueThenFalse(DataGetter<T, Boolean> getter) {
        return newDataComparator(new BooleanComparator(Ordering.DESCENDING), getter);
    }

    @SafeVarargs
    public final <T> Comparator<T> newTieBreaker(Comparator<T> comparator, Comparator<T>... tieBreakers) {
        return new TieBreakerComparator<>(comparator, tieBreakers);
    }

    public Comparator<DateTime> newMostRecentFirst() {
        return new DateComparator(Ordering.DESCENDING);
    }

    public Comparator<DateTime> newOldestFirst() {
        return new DateComparator(Ordering.ASCENDING);
    }

    @NonNull
    private StringComparator newZtoA() {
        return new StringComparator(Ordering.DESCENDING);
    }

    @NonNull
    private StringComparator newAtoZ() {
        return new StringComparator(Ordering.ASCENDING);
    }
}
