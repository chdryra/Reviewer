package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.TreeDataGetterImpl;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Interfaces.TreeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryTreeDataGetter {
    public TreeDataGetter newTreeGetter() {
        return new TreeDataGetterImpl();
    }
}
