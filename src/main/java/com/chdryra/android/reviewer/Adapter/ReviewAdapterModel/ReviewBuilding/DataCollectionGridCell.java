package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Screens.GridDataObservable;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataCollectionGridCell<T extends GvData> extends GvDataList<T>
        implements GridDataObservable.GridDataObserver {
    public static final Creator<DataCollectionGridCell> CREATOR = new Creator<DataCollectionGridCell>() {
        //Overridden
        public DataCollectionGridCell createFromParcel(Parcel in) {
            return new DataCollectionGridCell(in);
        }

        public DataCollectionGridCell[] newArray(int size) {
            return new DataCollectionGridCell[size];
        }
    };

    public static GvDataType<DataCollectionGridCell> TYPE =
            new GvDataType<>(DataCollectionGridCell.class, "create", "create");

    private ReviewViewAdapter<T> mDataAdapter;
    private FactoryVhDataCollection mViewHolderFactory;

    DataCollectionGridCell(GvDataType<T> dataType,
                           ReviewViewAdapter<T> dataAdapter,
                           FactoryVhDataCollection viewHolderFactory) {
        super(dataType, null);
        mDataAdapter = dataAdapter;
        mViewHolderFactory = viewHolderFactory;
        mDataAdapter.registerGridDataObserver(this);
    }

    private DataCollectionGridCell(Parcel in) {
        super(in);
    }

    //public methods
    public int getDataSize() {
        return mDataAdapter.getGridData().size();
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
