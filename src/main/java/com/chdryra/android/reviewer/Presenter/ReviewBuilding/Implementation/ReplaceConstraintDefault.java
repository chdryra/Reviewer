package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReplaceConstraintDefault<G extends GvData> implements DataBuilder.ReplaceConstraint<G> {
    @Override
    public DataBuilder.ConstraintResult passes(GvDataList<G> data, G oldDatum, G newDatum) {
        if (data == null) {
            return DataBuilder.ConstraintResult.NULL_LIST;
        } else {
            return !data.contains(newDatum) ? DataBuilder.ConstraintResult.PASSED : DataBuilder
                    .ConstraintResult.HAS_DATUM;
        }
    }
}
