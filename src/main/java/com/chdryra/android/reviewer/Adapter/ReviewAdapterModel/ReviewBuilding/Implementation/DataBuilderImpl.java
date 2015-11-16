package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.DataConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryInputHandler;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .InputHandler;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilder;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderImpl <T extends GvData> implements DataBuilder<T> {
    private final GvDataType<T> mDataType;
    private final ReviewBuilder mParentBuilder;
    private InputHandler<T> mHandler;
    private final DataConverter<? super T, T, ? extends GvDataList<T>> mCopier;

    public DataBuilderImpl(GvDataType<T> dataType,
                       ReviewBuilder parentBuilder,
                       DataConverter<? super T, T, ? extends GvDataList<T>> copier) {
        mDataType = dataType;
        mParentBuilder = parentBuilder;
        mCopier = copier;
        resetData();
    }

    //public methods
    private ReviewBuilder getParentBuilder() {
        return mParentBuilder;
    }

    public GvDataList<T> getData() {
        return mHandler.getData();
    }

    public InputHandler.ConstraintResult add(T datum) {
        return mHandler.add(datum);
    }

    public void delete(T datum) {
        mHandler.delete(datum);
    }

    public void deleteAll() {
        mHandler.deleteAll();
    }

    public InputHandler.ConstraintResult replace(T oldDatum, T newDatum) {
        return mHandler.replace(oldDatum, newDatum);
    }

    public void setData() {
        getParentBuilder().setData(this);
    }

    public void resetData() {
        GvDataList<T> data = mCopier.convert(getParentBuilder().getData(mDataType));
        mHandler = FactoryInputHandler.newHandler(data);
    }
}
