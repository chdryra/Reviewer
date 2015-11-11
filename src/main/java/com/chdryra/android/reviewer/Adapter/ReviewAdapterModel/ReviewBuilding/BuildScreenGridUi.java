package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenGridUi implements AdapterGridUi<ReviewBuilderAdapter> {

    private DataCollectionGridCellList mWrapper;
    private FactoryDataCollectionGridCell mGridCellFactory;
    private ReviewBuilderAdapter mBuilder;

    BuildScreenGridUi(FactoryDataCollectionGridCell gridCellFactory) {
        mWrapper = new DataCollectionGridCellList();
        mGridCellFactory = gridCellFactory;
    }

    @Override
    public void setViewAdapter(ReviewBuilderAdapter builder) {
        mBuilder = builder;
    }

    @Override
    public  <T extends GvData> void addGridCell(GvDataType<T> dataType) {
        if(mBuilder != null) {
            ReviewBuilderAdapter.DataBuilderAdapter<T> adapter = mBuilder.getDataBuilder(dataType);
            FactoryVhBuildReviewData vhFactory = new FactoryVhBuildReviewData();
            mWrapper.add(mGridCellFactory.newGridCell(dataType, adapter, vhFactory));
        }
    }

    @Override
    public GvDataList getGridUi() {
        return mWrapper;
    }

}
