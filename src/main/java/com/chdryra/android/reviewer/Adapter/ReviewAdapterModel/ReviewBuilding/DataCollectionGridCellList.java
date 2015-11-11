package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import android.os.Parcel;

import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataCollectionGridCellList extends GvDataList<DataCollectionGridCell> {
    public static final Creator<DataCollectionGridCellList> CREATOR = new Creator<DataCollectionGridCellList>() {
        //Overridden
        public DataCollectionGridCellList createFromParcel(Parcel in) {
            return new DataCollectionGridCellList(in);
        }

        public DataCollectionGridCellList[] newArray(int size) {
            return new DataCollectionGridCellList[size];
        }
    };

    DataCollectionGridCellList() {
        super(DataCollectionGridCell.TYPE, null);
    }

    private DataCollectionGridCellList(Parcel in) {
        super(in);
    }

    //Overridden
    @Override
    public void sort() {
    }
}
