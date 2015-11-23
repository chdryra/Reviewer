package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.DataBuilderImpl;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataBuilder {
    private final ConverterGv mConverter;

    public FactoryDataBuilder(ConverterGv converter) {
        mConverter = converter;
    }

    public <T extends GvData> DataBuilder<T> newDataBuilder(GvDataType<T> dataType,
                                                            ReviewBuilder parentBuilder) {
        return new DataBuilderImpl<>(dataType, parentBuilder, mConverter.getCopier(dataType));
    }
}
