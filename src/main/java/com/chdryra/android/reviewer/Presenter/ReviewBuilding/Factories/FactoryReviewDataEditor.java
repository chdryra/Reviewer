package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewDataEditorImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;

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
