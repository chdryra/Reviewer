/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvDataPacker;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataPackerTest extends TestCase {
    private static final GvDataList.GvDataType[] TYPES = GvDataMocker.TYPES;

    public void testPackItemUnpackItemBundleStatic() {
        for (GvDataList.GvDataType dataType : TYPES) {
            testPackItemUnpackItemStatic(dataType, true, false);
        }
    }

    public void testPackItemUnpackItemIntentStatic() {
        for (GvDataList.GvDataType dataType : TYPES) {
            testPackItemUnpackItemStatic(dataType, false, false);
        }
    }

    public void testPackItemUnpackItemBundle() {
        for (GvDataList.GvDataType dataType : TYPES) {
            testPackItemUnpackItemStatic(dataType, true, true);
        }
    }

    public void testPackItemUnpackItemIntent() {
        for (GvDataList.GvDataType dataType : TYPES) {
            testPackItemUnpackItemStatic(dataType, false, true);
        }
    }

    private void testPackItemUnpackItemStatic(GvDataList.GvDataType
            dataType, boolean bundle, boolean useUnpacker) {
        testPackItemUnpackItem(GvDataMocker.getDatum(dataType),
                GvDataMocker.getDatum(dataType), bundle, useUnpacker);
    }

    private <T extends GvDataList.GvData> void testPackItemUnpackItem(T itemCurrent, T itemNew,
            boolean bundle, boolean useUnpacker) {
        assertNotSame(itemCurrent, itemNew);

        GvDataPacker<T> unpacker = useUnpacker ? new GvDataPacker<T>() : null;
        Bundle args = new Bundle();
        Intent intent = new Intent();

        GvDataList.GvData unpackCurrent = bundle ? unpack(GvDataPacker.CurrentNewDatum.CURRENT,
                args, null) : unpack(GvDataPacker.CurrentNewDatum.CURRENT, intent, unpacker);
        GvDataList.GvData unpackNew = bundle ? unpack(GvDataPacker.CurrentNewDatum
                .NEW, args, null) : unpack(GvDataPacker.CurrentNewDatum.NEW, intent, unpacker);
        assertNull(unpackCurrent);
        assertNull(unpackNew);

        if (bundle) {
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, itemCurrent, args);
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.NEW, itemNew, args);
        } else {
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, itemCurrent, intent);
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.NEW, itemNew, intent);
        }

        unpackCurrent = bundle ? unpack(GvDataPacker.CurrentNewDatum.CURRENT,
                args, null) : unpack(GvDataPacker.CurrentNewDatum.CURRENT, intent, unpacker);
        unpackNew = bundle ? unpack(GvDataPacker.CurrentNewDatum
                .NEW, args, null) : unpack(GvDataPacker.CurrentNewDatum.NEW, intent, unpacker);
        assertNotNull(unpackCurrent);
        assertNotNull(unpackNew);
        assertSame(itemCurrent, unpackCurrent);
        assertSame(itemNew, unpackNew);
    }

    private <T extends GvDataList.GvData> GvDataList.GvData unpack(GvDataPacker.CurrentNewDatum
            currentNew, Bundle args, GvDataPacker<T> unpacker) {
        if (unpacker != null) {
            return unpacker.unpack(currentNew, args);
        } else {
            return GvDataPacker.unpackItem(currentNew, args);
        }
    }

    private <T extends GvDataList.GvData> GvDataList.GvData unpack(GvDataPacker.CurrentNewDatum
            currentNew, Intent intent, GvDataPacker<T> unpacker) {
        if (unpacker != null) {
            return unpacker.unpack(currentNew, intent);
        } else {
            return GvDataPacker.unpackItem(currentNew, intent);
        }
    }
}
