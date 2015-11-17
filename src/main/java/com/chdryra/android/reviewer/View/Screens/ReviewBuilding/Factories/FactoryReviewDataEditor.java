package com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation.ReviewDataEditorImpl;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.View.Screens.ReviewViewActions;
import com.chdryra.android.reviewer.View.Screens.ReviewViewParams;

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
