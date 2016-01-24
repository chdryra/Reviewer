/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Parcel;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderGridCellList extends GvDataListImpl<DataBuilderGridCell> {
    public static final Creator<DataBuilderGridCellList> CREATOR = new Creator<DataBuilderGridCellList>() {
        @Override
        public DataBuilderGridCellList createFromParcel(Parcel in) {
            return new DataBuilderGridCellList(in);
        }

        @Override
        public DataBuilderGridCellList[] newArray(int size) {
            return new DataBuilderGridCellList[size];
        }
    };

    DataBuilderGridCellList() {
        super(DataBuilderGridCell.TYPE, null);
    }

    private DataBuilderGridCellList(Parcel in) {
        super(in);
    }

    <T extends GvData> void addNewGridCell(DataBuilderAdapter<T> dataAdapter,
                FactoryVhDataCollection viewHolderFactory) {
        add(new DataBuilderGridCell<>(dataAdapter, viewHolderFactory));
    }

    //Overridden
    @Override
    public void sort() {
    }
}
