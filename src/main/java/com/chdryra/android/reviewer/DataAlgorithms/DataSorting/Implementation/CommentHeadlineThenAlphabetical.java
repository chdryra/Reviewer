package com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentHeadlineThenAlphabetical implements Comparator<DataComment> {
    //Overridden
    @Override
    public int compare(DataComment lhs, DataComment rhs) {
        int comp;
        if (lhs.isHeadline() && !rhs.isHeadline()) {
            comp = -1;
        } else if (!lhs.isHeadline() && rhs.isHeadline()) {
            comp = 1;
        } else {
            comp = lhs.getComment().compareToIgnoreCase(rhs.getComment());
        }

        return comp;
    }
}
