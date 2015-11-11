package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.ConverterGv;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataBuilder {
    private ConverterGv mConverter;

    public FactoryDataBuilder(ConverterGv converter) {
        mConverter = converter;
    }

    public <T extends GvData> DataBuilder<T> newDataBuilder(GvDataType<T> dataType,
                                                            ReviewBuilder parentBuilder) {
        return new DataBuilder<>(dataType, parentBuilder, mConverter);
    }
}
