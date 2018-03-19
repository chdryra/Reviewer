/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewId;


/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderGridCellList extends GvDataListImpl<DataBuilderGridCell> {
    public DataBuilderGridCellList() {
        super(DataBuilderGridCell.TYPE, new GvReviewId());
    }

    @Override
    public void sort() {
    }

    <T extends GvDataParcelable> void addNewGridCell(DataBuilderAdapter<T> dataAdapter,
                                                     FactoryVhDataCollection viewHolderFactory) {
        add(new DataBuilderGridCell<>(dataAdapter, viewHolderFactory));
    }
}
