/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.BuildScreenGridUi;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenGridUiImpl implements BuildScreenGridUi<DataBuilderGridCell> {
    private final ArrayList<GvDataType<? extends GvDataParcelable>> mCells;
    private final DataBuilderGridCellList mWrapper;
    private final FactoryVhDataCollection mVhFactory;

    public BuildScreenGridUiImpl(FactoryVhDataCollection vhFactory) {
        mWrapper = new DataBuilderGridCellList();
        mVhFactory = vhFactory;
        mCells = new ArrayList<>();
    }

    public <T extends GvDataParcelable> void addGridCell(GvDataType<T> dataType) {
        mCells.add(dataType);
    }

    @Override
    public void setParentAdapter(ReviewBuilderAdapter<?> adapter) {
        for(GvDataType<? extends GvDataParcelable> dataType : mCells) {
            mWrapper.addNewGridCell(adapter.getDataBuilderAdapter(dataType), mVhFactory);
        }
    }

    @Override
    public DataBuilderGridCellList getGridWrapper() {
        return mWrapper;
    }
}
