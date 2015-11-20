package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .FactoryVhDataCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridDataObservable;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderGridCell<T extends GvData> extends GvDataList<T>
        implements GridDataObservable.GridDataObserver {

    public static final Creator<DataBuilderGridCell> CREATOR = new Creator<DataBuilderGridCell>() {
        //Overridden
        @Override
        public DataBuilderGridCell createFromParcel(Parcel in) {
            return new DataBuilderGridCell(in);
        }

        @Override
        public DataBuilderGridCell[] newArray(int size) {
            return new DataBuilderGridCell[size];
        }
    };

    public static final GvDataType<DataBuilderGridCell> TYPE =
            new GvDataType<>(DataBuilderGridCell.class, "create", "create");

    private ReviewViewAdapter<T> mDataAdapter;
    private FactoryVhDataCollection mViewHolderFactory;

    public DataBuilderGridCell(DataBuilderAdapter<T> dataAdapter,
                               FactoryVhDataCollection viewHolderFactory) {
        super(dataAdapter.getGvDataType(), null);
        mDataAdapter = dataAdapter;
        mViewHolderFactory = viewHolderFactory;
        mDataAdapter.registerGridDataObserver(this);
        onGridDataChanged();
    }

    private DataBuilderGridCell(Parcel in) {
        super(in);
    }

    //Overridden
    @Override
    public String getStringSummary() {
        return getGvDataType().getDataName();
    }

    @Override
    public ViewHolder getViewHolder() {
        return mViewHolderFactory.newViewHolder();
    }

    @Override
    public void onGridDataChanged() {
        mData = mDataAdapter.getGridData().toArrayList();
    }
}
