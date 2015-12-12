package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderGridCell<T extends GvData> extends GvDataListImpl<T>
        implements GridDataObservable.GridDataObserver {

    public static final Parcelable.Creator<DataBuilderGridCell> CREATOR = new Parcelable.Creator<DataBuilderGridCell>() {
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
