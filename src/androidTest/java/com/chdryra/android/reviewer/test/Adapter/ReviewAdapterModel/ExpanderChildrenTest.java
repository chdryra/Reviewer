/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ExpanderChildren;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.GridCellExpander;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerChildList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderChildrenTest extends ExpanderChildNodeTest {
    protected GridCellExpander getExpander(ViewerChildList wrapper) {
        return new ExpanderChildren(getContext(), mNode);
    }
}
