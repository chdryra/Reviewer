package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .FactoryGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .FactoryVhDataCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.ReviewBuilderAdapterImpl;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Utils.FactoryFileIncrementor;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilderAdapter {
    private final FactoryGridUi<? extends GvDataList, ReviewBuilderAdapter> gridUiFactory;
    private final FactoryVhDataCollection mVhFactory;
    private FactoryDataBuilderAdapter mDataBuilderAdapterFactory;
    private FactoryFileIncrementor mFactoryFileIncrementor;
    private FactoryImageChooser mFactoryImageChooser;
    private DataValidator mDataValidator;

    public FactoryReviewBuilderAdapter(Context context, DataValidator validator,
                                       FactoryFileIncrementor incrementorFactory) {
        gridUiFactory = new FactoryRvaGridUi();
        mVhFactory = new FactoryVhBuildReviewData();
        mDataBuilderAdapterFactory = new FactoryDataBuilderAdapter(context);
        mFactoryImageChooser = new FactoryImageChooser(context);
        mDataValidator = validator;
        mFactoryFileIncrementor = incrementorFactory;
    }

    public ReviewBuilderAdapter newAdapter(ReviewBuilder builder) {

        return new ReviewBuilderAdapterImpl<>(builder,
                gridUiFactory.newGridUiWrapper(mVhFactory),
                mDataValidator,
                mDataBuilderAdapterFactory,
                mFactoryFileIncrementor,
                mFactoryImageChooser);
    }
}
