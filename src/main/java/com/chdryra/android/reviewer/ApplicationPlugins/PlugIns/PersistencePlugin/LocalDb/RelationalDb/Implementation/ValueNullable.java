/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Implementation;

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
