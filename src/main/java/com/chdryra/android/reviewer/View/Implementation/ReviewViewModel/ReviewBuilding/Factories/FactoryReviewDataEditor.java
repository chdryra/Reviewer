package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.ReviewDataEditorImpl;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewDataEditor {
    private FactoryReviewViewParams mParamsFactory;
    private FactoryEditActions mActionsFactory;

    public FactoryReviewDataEditor(FactoryReviewViewParams paramsFactory,
                                   FactoryEditActions actionsFactory) {
        mParamsFactory = paramsFactory;
        mActionsFactory = actionsFactory;
    }

    public <T extends GvData> ReviewDataEditor<T> newEditor(DataBuilderAdapter<T> adapter) {
        ReviewViewParams params = mParamsFactory.getParams(adapter.getGvDataType());
        ReviewViewActions<T> actions = mActionsFactory.newActions(adapter.getGvDataType());
        return new ReviewDataEditorImpl<>(adapter, actions, params);
    }
}
