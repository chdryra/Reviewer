/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Handles user inputs of review data. Checks validity of data and compares user input to current
 *
 * @param <T>: {@link GvData} type.
 */
public interface DataBuilder<T extends GvData> {
    interface DataBuilderObserver {
        <T2 extends GvData> void onDataPublished(DataBuilder<T2> builder);
    }

    GvDataType<T> getGvDataType();

    GvDataList<T> getData();

    ConstraintResult add(T newDatum);

    ConstraintResult replace(T oldDatum, T newDatum);

    void delete(T data);

    void deleteAll();

    void resetData();

    void publishData();

    void registerObserver(DataBuilderObserver observer);

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
