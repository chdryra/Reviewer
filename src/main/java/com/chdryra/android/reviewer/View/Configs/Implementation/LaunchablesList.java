package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class LaunchablesList implements Iterable<LaunchableClasses<?>> {
    private final ArrayList<LaunchableClasses<?>> mClasses = new ArrayList<>();

    protected <T extends GvData> void add(LaunchableClasses<T> classes) {
        mClasses.add(classes);
    }

    @Override
    public Iterator<LaunchableClasses<?>> iterator() {
        return mClasses.iterator();
    }
}
