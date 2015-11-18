package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.ReviewDataEditorImpl;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewDataEditor {
    public <T extends GvData> ReviewDataEditor<T> newEditor(DataBuilderAdapter<T> adapter,
                                                            ReviewViewParams params,
                                                            ReviewViewActions actions) {
        return new ReviewDataEditorImpl<>(adapter, params, actions);
    }
}
