package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation;



/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbEntryTypes {
    public static final DbEntryType<String> TEXT = new DbEntryType<>("String", String.class);
    public static final DbEntryType<Float> FLOAT = new DbEntryType<>("Float", Float.class);
    public static final DbEntryType<Double> DOUBLE = new DbEntryType<>("Double", Double.class);
    public static final DbEntryType<Integer> INTEGER = new DbEntryType<>("Integer", Integer.class);
    public static final DbEntryType<Boolean> BOOLEAN = new DbEntryType<>("Boolean", Boolean.class);
    public static final DbEntryType<Long> LONG = new DbEntryType<>("Long", Long.class);
    public static final DbEntryType<ByteArray> BYTE_ARRAY = new DbEntryType<>("ByteArray", ByteArray.class);
}
