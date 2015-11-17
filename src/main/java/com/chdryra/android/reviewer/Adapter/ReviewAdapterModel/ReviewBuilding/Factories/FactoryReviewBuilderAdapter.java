package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.ReviewBuilderAdapterImpl;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.Utils.FactoryFileIncrementor;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilderAdapter {
    private final FactoryReviewBuilder mBuilderFactory;
    private final FactoryGridUi<? extends GvDataList, ReviewBuilderAdapter> mGridUiFactory;
    private final FactoryVhDataCollection mVhFactory;
    private final DataValidator mDataValidator;
    private final FactoryDataBuilderAdapter mDataBuilderAdapterFactory;
    private final FactoryFileIncrementor mFactoryFileIncrementor;
    private final FactoryImageChooser mFactoryImageChooser;

    public FactoryReviewBuilderAdapter(FactoryReviewBuilder builderFactory,
                                       FactoryGridUi<? extends GvDataList, ReviewBuilderAdapter>
                                               gridUiFactory,
                                       FactoryVhDataCollection vhFactory,
                                       DataValidator dataValidator,
                                       FactoryDataBuilderAdapter dataBuilderAdapterFactory,
                                       FactoryFileIncrementor factoryFileIncrementor,
                                       FactoryImageChooser factoryImageChooser) {
        mBuilderFactory = builderFactory;
        mGridUiFactory = gridUiFactory;
        mVhFactory = vhFactory;
        mDataValidator = dataValidator;
        mDataBuilderAdapterFactory = dataBuilderAdapterFactory;
        mFactoryFileIncrementor = factoryFileIncrementor;
        mFactoryImageChooser = factoryImageChooser;
    }

    public ReviewBuilderAdapter newAdapter() {

        return new ReviewBuilderAdapterImpl<>(mBuilderFactory.newBuilder(),
                mGridUiFactory.newGridUiWrapper(mVhFactory),
                mDataValidator,
                mDataBuilderAdapterFactory,
                mFactoryFileIncrementor,
                mFactoryImageChooser);
    }
}
