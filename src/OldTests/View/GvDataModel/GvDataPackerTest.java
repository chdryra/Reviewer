/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 December, 2014
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataPackerTest extends TestCase {
    private static final ArrayList<GvDataType> TYPES = GvDataMocker.TYPES;

    public void testPackItemUnpackItemBundleStatic() {
        for (GvDataType dataType : TYPES) {
            testPackItemUnpackItemStatic(dataType, true, false);
        }
    }

    public void testPackItemUnpackItemIntentStatic() {
        for (GvDataType dataType : TYPES) {
            testPackItemUnpackItemStatic(dataType, false, false);
        }
    }

    public void testPackItemUnpackItemBundle() {
        for (GvDataType dataType : TYPES) {
            testPackItemUnpackItemStatic(dataType, true, true);
        }
    }

    public void testPackItemUnpackItemIntent() {
        for (GvDataType dataType : TYPES) {
            testPackItemUnpackItemStatic(dataType, false, true);
        }
    }

    private void testPackItemUnpackItemStatic(GvDataType
                                                      dataType, boolean bundle, boolean
                                                      useUnpacker) {
        testPackItemUnpackItem(GvDataMocker.getDatum(dataType),
                GvDataMocker.getDatum(dataType), bundle, useUnpacker);
    }

    private <T extends GvData> void testPackItemUnpackItem(T itemCurrent, T itemNew,
                                                           boolean bundle, boolean useUnpacker) {
        assertNotSame(itemCurrent, itemNew);

        ParcelablePacker<T> unpacker = useUnpacker ? new ParcelablePacker<T>() : null;
        Bundle args = new Bundle();
        Intent intent = new Intent();

        GvData unpackCurrent = bundle ? unpack(ParcelablePacker.CurrentNewDatum.CURRENT,
                args, null) : unpack(ParcelablePacker.CurrentNewDatum.CURRENT, intent, unpacker);
        GvData unpackNew = bundle ? unpack(ParcelablePacker.CurrentNewDatum
                .NEW, args, null) : unpack(ParcelablePacker.CurrentNewDatum.NEW, intent, unpacker);
        assertNull(unpackCurrent);
        assertNull(unpackNew);

        if (bundle) {
            ParcelablePacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, itemCurrent, args);
            ParcelablePacker.packItem(ParcelablePacker.CurrentNewDatum.NEW, itemNew, args);
        } else {
            ParcelablePacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, itemCurrent, intent);
            ParcelablePacker.packItem(ParcelablePacker.CurrentNewDatum.NEW, itemNew, intent);
        }

        unpackCurrent = bundle ? unpack(ParcelablePacker.CurrentNewDatum.CURRENT,
                args, null) : unpack(ParcelablePacker.CurrentNewDatum.CURRENT, intent, unpacker);
        unpackNew = bundle ? unpack(ParcelablePacker.CurrentNewDatum
                .NEW, args, null) : unpack(ParcelablePacker.CurrentNewDatum.NEW, intent, unpacker);
        assertNotNull(unpackCurrent);
        assertNotNull(unpackNew);
        assertSame(itemCurrent, unpackCurrent);
        assertSame(itemNew, unpackNew);
    }

    private <T extends GvData> GvData unpack(ParcelablePacker.CurrentNewDatum
                                                     currentNew, Bundle args, ParcelablePacker<T>
                                                     unpacker) {
        if (unpacker != null) {
            return unpacker.unpack(currentNew, args);
        } else {
            return ParcelablePacker.unpackItem(currentNew, args);
        }
    }

    private <T extends GvData> GvData unpack(ParcelablePacker.CurrentNewDatum
                                                     currentNew, Intent intent, ParcelablePacker<T>
                                                     unpacker) {
        if (unpacker != null) {
            return unpacker.unpack(currentNew, intent);
        } else {
            return ParcelablePacker.unpackItem(currentNew, intent);
        }
    }
}
