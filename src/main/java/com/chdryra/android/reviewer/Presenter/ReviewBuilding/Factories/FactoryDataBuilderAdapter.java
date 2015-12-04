package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.DataBuilderAdapterImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces
        .DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataBuilderAdapter {
    private final Context mContext;

    public FactoryDataBuilderAdapter(Context context) {
        mContext = context;
    }

    public <T extends GvData> DataBuilderAdapter<T> newDataBuilderAdapter(GvDataType<T> dataType,
                                                            ReviewBuilderAdapter parentBuilder) {
        return new DataBuilderAdapterImpl<>(mContext, dataType, parentBuilder);
    }
}
