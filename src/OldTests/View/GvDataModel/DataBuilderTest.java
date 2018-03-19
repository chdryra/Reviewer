/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.startouch.test.View.GvDataModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderTest extends AndroidTestCase {
    private static final ArrayList<GvDataType> TYPES = GvDataMocker.TYPES;
    private static final int NUMDATA = 30;

    @SmallTest
    public void testAdd() {
        for (GvDataType dataType : TYPES) {
            testAdd(dataType);
        }
    }

    @SmallTest
    public void testReplace() {
        for (GvDataType dataType : TYPES) {
            testReplace(dataType);
        }
    }

    @SmallTest
    public void testDelete() {
        for (GvDataType dataType : TYPES) {
            testDelete(dataType);
        }
    }

    @SmallTest
    public void testAddConstraint() {
        GvTagList data = (GvTagList) getData(GvTag.TYPE);
        DataBuilder noAddHandler = new DataBuilder<>(data, getNoAddAddConstraint(data));
        DataBuilder alwaysAddHandler = new DataBuilder<>(data, getAlwaysAddAddConstraint(data));

        GvTagList addData1 = (GvTagList) getData(GvTag.TYPE);
        GvTagList addData2 = (GvTagList) getData(GvTag.TYPE);

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

    private <T extends GvData> DataBuilder.AddConstraint<T> getNoAddAddConstraint
            (GvDataList<T> dummy) {
        return new DataBuilder.AddConstraint<T>() {
            //Overridden
            @Override
            public boolean passes(GvDataList<T> data, T datum) {
                return false;
            }
        };
    }

    private <T extends GvData> DataBuilder.AddConstraint<T> getAlwaysAddAddConstraint
            (GvDataList<T> dummy) {
        return new DataBuilder.AddConstraint<T>() {
            //Overridden
            @Override
            public boolean passes(GvDataList<T> data, T datum) {
                return true;
            }
        };
    }

    private void testAdd(GvDataType dataType) {
        GvDataList data = getData(dataType);
        DataBuilder handler = FactoryDataBuilder.newDataBuilder(data);

        int originalSize = data.size();
        GvDataList addData = getData(dataType, originalSize / 2);
        for (int i = 0; i < addData.size(); ++i) {
            assertEquals(originalSize + i, data.size());
            GvData addItem = (GvData) addData.getItem(i);
            assertFalse(contains(data, addItem));
            addItem(handler, addItem);
            assertTrue(contains(data, addItem));
        }

        assertEquals(originalSize + addData.size(), data.size());

        originalSize = data.size();
        for (int i = 0; i < addData.size(); ++i) {
            addItem(handler, (GvData) addData.getItem(i));
            assertEquals(originalSize, data.size());
        }
    }

    private void testDelete(GvDataType dataType) {
        //Test normal deletion
        GvDataList data = getData(dataType);
        int originalSize = data.size();
        DataBuilder handler = FactoryDataBuilder.newDataBuilder(data);
        for (int i = 0; i < originalSize; ++i) {
            assertEquals(originalSize - i, data.size());
            GvData deleteItem = (GvData) data.getItem(originalSize - i - 1);
            assertTrue(contains(data, deleteItem));
            deleteItem(handler, deleteItem);
            assertFalse(contains(data, deleteItem));
        }
        assertEquals(0, data.size());

        //Test no deletion when item not in data
        data = getData(dataType);
        originalSize = data.size();
        handler = FactoryDataBuilder.newDataBuilder(data);
        GvDataList noDeleteData = getData(dataType);
        for (int i = 0; i < noDeleteData.size(); ++i) {
            assertEquals(originalSize, data.size());
            deleteItem(handler, (GvData) noDeleteData.getItem(i));
        }
        assertEquals(originalSize, data.size());
    }

    private void testReplace(GvDataType dataType) {
        //Test normal deletion
        GvDataList data = getData(dataType);
        int originalSize = data.size();
        int replaceSize = originalSize / 2;
        GvDataList replaceData = getData(dataType, replaceSize);
        assertEquals(replaceSize, replaceData.size());

        DataBuilder handler = FactoryDataBuilder.newDataBuilder(data);
        for (int i = 0; i < replaceSize; ++i) {
            GvData toReplace = (GvData) data.getItem(i);
            GvData replaceWith = (GvData) replaceData.getItem(i);

            assertEquals(originalSize, data.size());
            assertTrue(contains(data, toReplace));
            assertFalse(contains(data, replaceWith));

            replaceItem(handler, toReplace, replaceWith);

            assertEquals(originalSize, data.size());
            assertFalse(contains(data, toReplace));
            assertTrue(contains(data, replaceWith));
        }

    }

    private GvDataList getData(GvDataType dataType) {
        return getData(dataType, NUMDATA);
    }

    private GvDataList getData(GvDataType dataType, int size) {
        return GvDataMocker.getData(dataType, size);
    }

    //These are here to avoid unchecked casts....
    private <T extends GvData> void deleteItem(DataBuilder<T> handler, T item) {
        handler.delete(item);
    }

    private <T extends GvData> void addItem(DataBuilder<T> handler, T item) {
        handler.add(item, getContext());
    }

    private <T extends GvData> void replaceItem(DataBuilder<T> handler, T toReplace,
                                                T replaceWith) {
        handler.replace(toReplace, replaceWith, getContext());
    }

    private <T extends GvData> boolean contains(GvDataList<T> data, T item) {
        return data.contains(item);
    }
}
