package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataBuilder<T extends GvData> {
    GvDataList<T> getData();

    InputHandler.ConstraintResult add(T datum);

    void delete(T datum);

    void deleteAll();

    InputHandler.ConstraintResult replace(T oldDatum, T newDatum);

    void setData();

    void resetData();
}
