package com.chdryra.android.reviewer.DataSorting.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorAlphabetical implements Comparator<DataAuthor> {
    @Override
    public int compare(DataAuthor lhs, DataAuthor rhs) {
        return lhs.getName().compareToIgnoreCase(rhs.getName());
    }
}
