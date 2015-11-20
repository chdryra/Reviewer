package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.ReviewDataEditScreenImpl;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;

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
