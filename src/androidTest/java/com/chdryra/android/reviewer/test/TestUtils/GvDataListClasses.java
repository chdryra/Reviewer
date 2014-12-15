/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.GvUrlList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataListClasses implements Iterable<Class<? extends GvDataList>> {
    private ArrayList<Class<? extends GvDataList>> mClasses = new ArrayList<>();

    private GvDataListClasses() {
        mClasses.add(GvTagList.class);
        mClasses.add(GvChildrenList.class);
        mClasses.add(GvCommentList.class);
        mClasses.add(GvFactList.class);
        mClasses.add(GvImageList.class);
        mClasses.add(GvLocationList.class);
        mClasses.add(GvUrlList.class);
    }

    @Override
    public Iterator<Class<? extends GvDataList>> iterator() {
        return mClasses.iterator();
    }
}
