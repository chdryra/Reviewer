package com.chdryra.android.reviewer.View.Implementation.Configs;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class LaunchablesList implements Iterable<AddEditViewClasses<?>> {
    private final ArrayList<AddEditViewClasses<?>> mClasses = new ArrayList<>();
    private final Class<? extends LaunchableUi> mReviewBuilderLaunchable;
    private final Class<? extends LaunchableUi> mMapEditorLaunchable;

    protected LaunchablesList(Class<? extends LaunchableUi> reviewBuilderLaunchable,
                            Class<? extends LaunchableUi> mapEditorLaunchable) {
        mReviewBuilderLaunchable = reviewBuilderLaunchable;
        mMapEditorLaunchable = mapEditorLaunchable;
    }

    protected <T extends GvData> void add(AddEditViewClasses<T> classes) {
        mClasses.add(classes);
    }

    public Class<? extends LaunchableUi> getReviewBuilderLaunchable() {
        return mReviewBuilderLaunchable;
    }

    public Class<? extends LaunchableUi> getMapEditorLaunchable() {
        return mMapEditorLaunchable;
    }

    @Override
    public Iterator<AddEditViewClasses<?>> iterator() {
        return mClasses.iterator();
    }
}
