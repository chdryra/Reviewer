/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.FactoryGvDataHandler;
import com.chdryra.android.reviewer.GvDataHandler;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataHandlerTest extends AndroidTestCase {
    private static final GvDataList.GvType[] TYPES   = {GvDataList.GvType.COMMENTS, GvDataList
            .GvType.FACTS, GvDataList.GvType.TAGS, GvDataList.GvType.LOCATIONS,
            GvDataList.GvType.URLS, GvDataList.GvType.CHILDREN, GvDataList.GvType.IMAGES};
    private static final int                 NUMDATA = 4;

    @SmallTest
    public void testAdd() {
        for (GvDataList.GvType datatype : TYPES) {
            testAddGvType(GvDataList.GvType.IMAGES);
        }
    }

    @SmallTest
    public void testReplace() {

    }

    @SmallTest
    public void testDelete() {

    }

    @SmallTest
    public void testAddConstraint() {
    }

    private void testAddGvType(GvDataList.GvType dataType) {
        GvDataList data = GvDataMocker.getData(dataType, 0);
        GvDataHandler handler = FactoryGvDataHandler.newHandler(data);
        GvDataList addData = GvDataMocker.getData(dataType, NUMDATA);
        for (int i = 0; i < NUMDATA; ++i) {
            assertEquals(i, data.size());
            addItem(handler, (GvDataList.GvData) addData.getItem(i));
        }
        assertEquals(NUMDATA, data.size());
    }

    private <T extends GvDataList.GvData> void addItem(GvDataHandler<T> handler, T item) {
        handler.add(item, getContext());
    }

}
