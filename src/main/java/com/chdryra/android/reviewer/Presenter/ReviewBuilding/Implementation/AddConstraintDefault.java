/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class AddConstraintDefault<G extends GvData> implements DataBuilder.AddConstraint<G> {
    @Override
    public DataBuilder.ConstraintResult passes(GvDataList<G> data, G datum) {
        if (data == null) {
            return DataBuilder.ConstraintResult.NULL_LIST;
        } else {
            return !data.contains(datum) ? DataBuilder.ConstraintResult.PASSED : DataBuilder
                    .ConstraintResult.HAS_DATUM;
        }
    }
}
