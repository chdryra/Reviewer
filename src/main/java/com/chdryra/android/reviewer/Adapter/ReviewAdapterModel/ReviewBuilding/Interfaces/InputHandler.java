/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 October, 2014
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Handles user inputs of review data. Checks validity of data and compares user input to current
 *
 * @param <T>: {@link GvData} type.
 */
public interface InputHandler<T extends GvData> {
    GvDataType<T> getGvDataType();

    GvDataList<T> getData();

    ConstraintResult add(T newDatum);

    ConstraintResult replace(T oldDatum, T newDatum);

    void delete(T data);

    void deleteAll();

    //Classes
    interface AddConstraint<G extends GvData> {
        ConstraintResult passes(GvDataList<G> data, G datum);
    }

    interface ReplaceConstraint<G extends GvData> {
        ConstraintResult passes(GvDataList<G> data, G oldDatum, G newDatum);
    }

    enum ConstraintResult {
        PASSED,
        NULL_LIST,
        HAS_DATUM,
        INVALID_DATUM,
        OLD_EQUALS_NEW
    }
}
