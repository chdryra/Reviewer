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
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataHandlerTest extends AndroidTestCase {
    private static final GvDataList.GvDataType[] TYPES = GvDataMocker.TYPES;
    private static final int                 NUMDATA = 30;

    @SmallTest
    public void testAdd() {
        for (GvDataList.GvDataType dataType : TYPES) {
            testAdd(dataType);
        }
    }

    @SmallTest
    public void testReplace() {
        for (GvDataList.GvDataType dataType : TYPES) {
            testReplace(dataType);
        }
    }

    @SmallTest
    public void testDelete() {
        for (GvDataList.GvDataType dataType : TYPES) {
            testDelete(dataType);
        }
    }

    @SmallTest
    public void testAddConstraint() {
        GvTagList data = (GvTagList) getData(GvTagList.TYPE);
        GvDataHandler noAddHandler = new GvDataHandler<>(data, getNoAddAddConstraint(data));
        GvDataHandler alwaysAddHandler = new GvDataHandler<>(data, getAlwaysAddAddConstraint(data));

        GvTagList addData1 = (GvTagList) getData(GvTagList.TYPE);
        GvTagList addData2 = (GvTagList) getData(GvTagList.TYPE);

        int originalSize = data.size();
        for (int i = 0; i < addData1.size(); ++i) {
            assertEquals(originalSize, data.size());
            addItem(noAddHandler, addData1.getItem(i));
        }

        assertEquals(originalSize, data.size());

        for (int i = 0; i < addData2.size(); ++i) {
            assertEquals(originalSize + i, data.size());
            addItem(alwaysAddHandler, addData2.getItem(i));
        }

        assertEquals(originalSize + addData2.size(), data.size());
    }

    private <T extends GvDataList.GvData> GvDataHandler.AddConstraint<T> getNoAddAddConstraint
            (GvDataList<T> dummy) {
        return new GvDataHandler.AddConstraint<T>() {
            @Override
            public boolean passes(GvDataList<T> data, T datum) {
                return false;
            }
        };
    }

    private <T extends GvDataList.GvData> GvDataHandler.AddConstraint<T> getAlwaysAddAddConstraint
            (GvDataList<T> dummy) {
        return new GvDataHandler.AddConstraint<T>() {
            @Override
            public boolean passes(GvDataList<T> data, T datum) {
                return true;
            }
        };
    }

    private void testAdd(GvDataList.GvDataType dataType) {
        GvDataList data = getData(dataType);
        GvDataHandler handler = FactoryGvDataHandler.newHandler(data);

        int originalSize = data.size();
        GvDataList addData = getData(dataType, originalSize / 2);
        for (int i = 0; i < addData.size(); ++i) {
            assertEquals(originalSize + i, data.size());
            GvDataList.GvData addItem = (GvDataList.GvData) addData.getItem(i);
            assertFalse(contains(data, addItem));
            addItem(handler, addItem);
            assertTrue(contains(data, addItem));
        }

        assertEquals(originalSize + addData.size(), data.size());

        originalSize = data.size();
        for (int i = 0; i < addData.size(); ++i) {
            addItem(handler, (GvDataList.GvData) addData.getItem(i));
            assertEquals(originalSize, data.size());
        }
    }

    private void testDelete(GvDataList.GvDataType dataType) {
        //Test normal deletion
        GvDataList data = getData(dataType);
        int originalSize = data.size();
        GvDataHandler handler = FactoryGvDataHandler.newHandler(data);
        for (int i = 0; i < originalSize; ++i) {
            assertEquals(originalSize - i, data.size());
            GvDataList.GvData deleteItem = (GvDataList.GvData) data.getItem(originalSize - i - 1);
            assertTrue(contains(data, deleteItem));
            deleteItem(handler, deleteItem);
            assertFalse(contains(data, deleteItem));
        }
        assertEquals(0, data.size());

        //Test no deletion when item not in data
        data = getData(dataType);
        originalSize = data.size();
        handler = FactoryGvDataHandler.newHandler(data);
        GvDataList noDeleteData = getData(dataType);
        for (int i = 0; i < noDeleteData.size(); ++i) {
            assertEquals(originalSize, data.size());
            deleteItem(handler, (GvDataList.GvData) noDeleteData.getItem(i));
        }
        assertEquals(originalSize, data.size());
    }

    private void testReplace(GvDataList.GvDataType dataType) {
        //Test normal deletion
        GvDataList data = getData(dataType);
        int originalSize = data.size();
        int replaceSize = originalSize / 2;
        GvDataList replaceData = getData(dataType, replaceSize);
        assertEquals(replaceSize, replaceData.size());

        GvDataHandler handler = FactoryGvDataHandler.newHandler(data);
        for (int i = 0; i < replaceSize; ++i) {
            GvDataList.GvData toReplace = (GvDataList.GvData) data.getItem(i);
            GvDataList.GvData replaceWith = (GvDataList.GvData) replaceData.getItem(i);

            assertEquals(originalSize, data.size());
            assertTrue(contains(data, toReplace));
            assertFalse(contains(data, replaceWith));

            replaceItem(handler, toReplace, replaceWith);

            assertEquals(originalSize, data.size());
            assertFalse(contains(data, toReplace));
            assertTrue(contains(data, replaceWith));
        }

    }

    private GvDataList getData(GvDataList.GvDataType dataType) {
        return getData(dataType, NUMDATA);
    }

    private GvDataList getData(GvDataList.GvDataType dataType, int size) {
        return GvDataMocker.getData(dataType, size);
    }

    //These are here to avoid unchecked casts....
    private <T extends GvDataList.GvData> void deleteItem(GvDataHandler<T> handler, T item) {
        handler.delete(item);
    }

    private <T extends GvDataList.GvData> void addItem(GvDataHandler<T> handler, T item) {
        handler.add(item, getContext());
    }

    private <T extends GvDataList.GvData> void replaceItem(GvDataHandler<T> handler, T toReplace,
            T replaceWith) {
        handler.replace(toReplace, replaceWith, getContext());
    }

    private <T extends GvDataList.GvData> boolean contains(GvDataList<T> data, T item) {
        return data.contains(item);
    }
}
