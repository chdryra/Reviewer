/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Implementation;



/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbEntryType<T> {
    public static final DbEntryType<String> TEXT = new DbEntryType<>(String.class);
    public static final DbEntryType<Float> FLOAT = new DbEntryType<>(Float.class);
    public static final DbEntryType<Double> DOUBLE = new DbEntryType<>(Double.class);
    public static final DbEntryType<Integer> INTEGER = new DbEntryType<>(Integer.class);
    public static final DbEntryType<Boolean> BOOLEAN = new DbEntryType<>(Boolean.class);
    public static final DbEntryType<Long> LONG = new DbEntryType<>(Long.class);
    public static final DbEntryType<ByteArray> BYTE_ARRAY = new DbEntryType<>(ByteArray.class);

    private Class<T> mTypeClass;
    private DbEntryType(Class<T> typeClass) {
        mTypeClass = typeClass;
    }

    public T cast(Object obj) {
        try {
            return mTypeClass.cast(obj);
        } catch (ClassCastException e) {
            throw new RuntimeException(obj + " is not of DbEntryType: " + mTypeClass.getSimpleName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DbEntryType)) return false;

        DbEntryType<?> that = (DbEntryType<?>) o;

        return !(mTypeClass != null ? !mTypeClass.equals(that.mTypeClass) : that.mTypeClass !=
                null);

    }

    @Override
    public int hashCode() {
        return mTypeClass != null ? mTypeClass.hashCode() : 0;
    }
}
