package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.EditScreenCriteria;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.EditScreenImages;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.EditScreenLocations;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.EditScreenReviewData;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.EditScreenReviewDataImpl;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.EditScreenTags;
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
    
    public <T extends GvData> EditScreenReviewData<T> newScreen(GvDataType<T> dataType, DataValidator validator) {
        DataBuilderAdapter<T> adapter = mBuilder.getDataBuilderAdapter(dataType);
        ReviewDataEditor<T> editor = mEditorFactory.newEditor(adapter);
        EditScreenReviewData screen;
        if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            screen = new EditScreenCriteria(mContext, mBuilder, mEditorFactory);
        } else if (dataType.equals(GvImageList.GvImage.TYPE)) {
            screen = new EditScreenImages(mContext, mBuilder, mBuilder.getImageChooser(), mEditorFactory);
        } else if (dataType.equals(GvLocationList.GvLocation.TYPE)) {
            screen = new EditScreenLocations(mContext, mBuilder, mEditorFactory);
        } else if (dataType.equals(GvTagList.GvTag.TYPE)) {
            screen = new EditScreenTags(mContext, mBuilder, validator, mEditorFactory);
        } else {
            screen = new EditScreenReviewDataImpl<>(mContext, editor);
        }

        return screen;
    }
}
