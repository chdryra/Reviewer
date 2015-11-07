/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 April, 2015
 */

package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbTable<T extends TableRow> extends DbTableDef {
    private Class<T> mRowClass;

    //Constructors
    public ReviewerDbTable(String tableName, Class<T> rowClass) {
        super(tableName);
        mRowClass = rowClass;
    }

    //public methods
    public Class<T> getRowClass() {
        return mRowClass;
    }
}
