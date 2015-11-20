package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryInputHandler;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .InputHandler;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilder;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
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

    @Override
    public GvDataList<T> getData() {
        return mHandler.getData();
    }

    @Override
    public InputHandler.ConstraintResult add(T datum) {
        return mHandler.add(datum);
    }

    @Override
    public void delete(T datum) {
        mHandler.delete(datum);
    }

    @Override
    public void deleteAll() {
        mHandler.deleteAll();
    }

    @Override
    public InputHandler.ConstraintResult replace(T oldDatum, T newDatum) {
        return mHandler.replace(oldDatum, newDatum);
    }

    @Override
    public void setData() {
        getParentBuilder().setData(this);
    }

    @Override
    public void resetData() {
        GvDataList<T> data = mCopier.convert(getParentBuilder().getData(mDataType));
        mHandler = FactoryInputHandler.newHandler(data);
    }
}
