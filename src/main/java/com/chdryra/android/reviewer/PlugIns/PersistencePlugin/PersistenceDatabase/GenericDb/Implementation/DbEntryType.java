package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation;



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

    public Class<T> getTypeClass() {
        return mTypeClass;
    }
}
