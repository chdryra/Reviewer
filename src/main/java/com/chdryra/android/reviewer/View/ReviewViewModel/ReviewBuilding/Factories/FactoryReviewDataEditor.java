package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.ReviewDataEditorImpl;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewDataEditor {
    FactoryReviewViewParams mParamsFactory;

    public FactoryReviewDataEditor(FactoryReviewViewParams paramsFactory) {
        mParamsFactory = paramsFactory;
    }

    public <T extends GvData> ReviewDataEditor<T> newEditor(DataBuilderAdapter<T> adapter,
                                                            ReviewViewActions<T> actions) {
        return new ReviewDataEditorImpl<>(adapter, mParamsFactory.getParams(adapter.getDataType()), actions);
    }
}
