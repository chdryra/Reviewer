package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.DataConverter;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvDataHandler;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataHandler;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilder<T extends GvData> {
    private GvDataType<T> mDataType;
    private ReviewBuilder mParentBuilder;
    private GvDataHandler<T> mHandler;
    private DataConverter<? super T, T, ? extends GvDataList<T>> mCopier;

    DataBuilder(GvDataType<T> dataType,
                ReviewBuilder parentBuilder,
                DataConverter<? super T, T, ? extends GvDataList<T>> copier) {
        mDataType = dataType;
        mParentBuilder = parentBuilder;
        mCopier = copier;
        resetData();
    }

    //public methods
    public ReviewBuilder getParentBuilder() {
        return mParentBuilder;
    }

    public GvDataList<T> getData() {
        return mHandler.getData();
    }

    public void reset() {
        getParentBuilder().resetDataBuilder(mHandler.getGvDataType());
    }

    public GvDataHandler.ConstraintResult add(T datum) {
        return mHandler.add(datum);
    }

    public void delete(T datum) {
        mHandler.delete(datum);
    }

    public void deleteAll() {
        mHandler.deleteAll();
    }

    public GvDataHandler.ConstraintResult replace(T oldDatum, T newDatum) {
        return mHandler.replace(oldDatum, newDatum);
    }

    public void setData() {
        getParentBuilder().setData(getData(), true);
    }

    public void resetData() {
        GvDataList<T> data = mCopier.convert(getParentBuilder().getData(mDataType));
        mHandler = FactoryGvDataHandler.newHandler(data);
    }
}
