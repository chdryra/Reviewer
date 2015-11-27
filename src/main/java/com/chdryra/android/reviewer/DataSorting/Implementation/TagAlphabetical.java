package com.chdryra.android.reviewer.DataSorting.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataTag;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagAlphabetical implements Comparator<DataTag> {
    //Overridden
    @Override
    public int compare(DataTag lhs, DataTag rhs) {
        return lhs.getTag().compareToIgnoreCase(rhs.getTag());
    }
}
