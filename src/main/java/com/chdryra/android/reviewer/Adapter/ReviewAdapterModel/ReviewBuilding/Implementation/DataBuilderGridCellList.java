package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import android.os.Parcel;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderGridCellList extends GvDataList<DataBuilderGridCell> {
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
