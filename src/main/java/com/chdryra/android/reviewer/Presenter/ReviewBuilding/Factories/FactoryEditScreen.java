/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation
        .ReviewDataEditScreenImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 06/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditScreen {
    private Context mContext;
    private ReviewBuilderAdapter<?> mBuilder;
    private FactoryReviewDataEditor mEditorFactory;

    public FactoryEditScreen(Context context, ReviewBuilderAdapter<?> builder,
                             FactoryReviewDataEditor editorFactory) {
        mContext = context;
        mBuilder = builder;
        mEditorFactory = editorFactory;
    }
    
    public <T extends GvData> ReviewDataEditScreen<T> newScreen(GvDataType<T> dataType) {
        DataBuilderAdapter<T> adapter = mBuilder.getDataBuilderAdapter(dataType);
        ReviewDataEditor<T> editor = mEditorFactory.newEditor(adapter);
        return new ReviewDataEditScreenImpl<>(mContext, editor);
    }
}
