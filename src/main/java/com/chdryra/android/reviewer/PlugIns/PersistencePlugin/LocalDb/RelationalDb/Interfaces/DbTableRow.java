/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.Validatable;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbTableRow<DbRow extends DbTableRow>
        extends Validatable, Iterable<RowEntry<DbRow, ?>>{
    String getRowId();

    String getRowIdColumnName();

    @Override
    boolean hasData(DataValidator validator);
}
