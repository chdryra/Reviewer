package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public enum ValueNullable {
    TRUE(true),
    FALSE(false);

    private boolean mNullable;
    ValueNullable(boolean nullable) {
        mNullable = nullable;
    }

    public boolean isNullable() {
        return mNullable;
    }
}
