package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import android.os.Parcel;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .FactoryVhDataCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RvaGridCellList extends GvDataList<RvaGridCell> {
    public static final Creator<RvaGridCellList> CREATOR = new Creator<RvaGridCellList>() {
        //Overridden
        public RvaGridCellList createFromParcel(Parcel in) {
            return new RvaGridCellList(in);
        }

        public RvaGridCellList[] newArray(int size) {
            return new RvaGridCellList[size];
        }
    };

    RvaGridCellList() {
        super(RvaGridCell.TYPE, null);
    }

    private RvaGridCellList(Parcel in) {
        super(in);
    }

    <T extends GvData> void addNewGridCell(GvDataType<T> dataType,
                ReviewViewAdapter<T> dataAdapter,
                FactoryVhDataCollection viewHolderFactory) {
        add(new RvaGridCell<>(dataType, dataAdapter, viewHolderFactory));
    }

    //Overridden
    @Override
    public void sort() {
    }
}
