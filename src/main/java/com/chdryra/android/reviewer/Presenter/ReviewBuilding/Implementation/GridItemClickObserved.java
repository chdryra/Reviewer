package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemActionNone;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemClickObserved<T extends GvData> extends GridItemActionNone<T>
        implements GridItemAction<T> {
    private ArrayList<ClickObserver<T>> mObservers;

    public interface ClickObserver<T extends GvData> {
        void onGridItemClick(T item, int position, View v);
        void onGridItemLongClick(T item, int position, View v);
    }

    public GridItemClickObserved() {
        mObservers = new ArrayList<>();
    }

    @Override
    public void onGridItemClick(T item, int position, View v) {
        for(ClickObserver<T> observer : mObservers) {
            observer.onGridItemClick(item, position, v);
        }
    }

    @Override
    public void onGridItemLongClick(T item, int position, View v) {
        for(ClickObserver<T> observer : mObservers) {
            observer.onGridItemLongClick(item, position, v);
        }
    }

    public void registerObserver(ClickObserver<T> observer) {
        mObservers.add(observer);
    }
}
