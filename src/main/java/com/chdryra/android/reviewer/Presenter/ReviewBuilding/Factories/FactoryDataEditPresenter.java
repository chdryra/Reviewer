/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PresenterReviewDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 06/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataEditPresenter {
    private final Context mContext;
    private final ReviewBuilderAdapter<?> mBuilder;
    private final FactoryReviewDataEditor mEditorFactory;

    public FactoryDataEditPresenter(Context context, ReviewBuilderAdapter<?> builder,
                                    FactoryReviewDataEditor editorFactory) {
        mContext = context;
        mBuilder = builder;
        mEditorFactory = editorFactory;
    }
    
    public <T extends GvDataParcelable> PresenterReviewDataEdit<T> newPresenter(GvDataType<T> dataType) {
        DataBuilderAdapter<T> adapter = mBuilder.getDataBuilderAdapter(dataType);
        ReviewDataEditor<T> editor = mEditorFactory.newEditor(adapter);
        return new PresenterReviewDataEdit<>(mContext, editor);
    }
}
