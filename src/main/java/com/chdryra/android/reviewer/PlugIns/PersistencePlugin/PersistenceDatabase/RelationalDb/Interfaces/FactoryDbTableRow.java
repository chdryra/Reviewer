/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces;


/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FactoryDbTableRow {
    <T extends DbTableRow> T emptyRow(Class<T> rowClass);

    <T extends DbTableRow, D> T newRow(Class<T> rowClass, D values);

    <T extends DbTableRow, D> T newRow(Class<T> rowClass, D values, int index);
}
