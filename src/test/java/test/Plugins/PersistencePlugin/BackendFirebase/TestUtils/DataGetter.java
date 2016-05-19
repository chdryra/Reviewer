/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.TestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 19/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DataGetter<In, Out> {
    private String mDataName;
    private List<DataGetter<Out, ?>> mOutGetters;

    public abstract Out getData(In item);

    public DataGetter(String dataName) {
        mDataName = dataName;
        mOutGetters = new ArrayList<>();
    }

    public DataGetter(String dataName, List<DataGetter<Out, ?>> outGetters) {
        mDataName = dataName;
        mOutGetters = outGetters;
    }

    public String getDataName() {
        return mDataName;
    }

    public List<DataGetter<Out, ?>> getOutGetters() {
        return mOutGetters;
    }
}
