/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbColumnNotNullable extends DbColumnDefinitionBasic {
    public DbColumnNotNullable(String columnName, DbEntryType type) {
        super(columnName, type);
    }

    @Override
    public ValueNullable getNullable() {
        return ValueNullable.FALSE;
    }
}
