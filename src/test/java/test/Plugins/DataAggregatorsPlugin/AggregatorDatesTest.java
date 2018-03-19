/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api
        .DataAggregatorsApi;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import test.TestUtils.RandomDataDate;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorDatesTest extends AggregatedDistinctItemsTest<DataDate> {
    @NonNull
    @Override
    protected DataAggregator<DataDate> newAggregator(DataAggregatorsApi factory,
                                                     DataAggregatorParams params) {
        return factory.newDatesAggregator(params.getSimilarDate());
    }

    @Override
    @NonNull
    protected DataDate newSimilarDatum(ReviewId reviewId, DataDate template) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(template.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 1 + getRAND().nextInt(20));
        cal.set(Calendar.MINUTE, 1 + getRAND().nextInt(50));
        Date date = cal.getTime();
        return new DatumDate(reviewId, date.getTime());
    }

    @Override
    @NonNull
    protected DataDate randomDatum() {
        return RandomDataDate.nextDate();
    }

    @Override
    protected DataDate getExampleCanonical(ReviewId id, ArrayList<DataDate> data) {
        ArrayList<DataDate> copy = new ArrayList<>(data);
        Collections.sort(copy, new Comparator<DataDate>() {
            @Override
            public int compare(DataDate lhs, DataDate rhs) {
                if (lhs.getTime() > rhs.getTime()) return -1;
                if (lhs.getTime() < rhs.getTime()) return 1;
                return 0;
            }
        });
        return new DatumDate(id, copy.get(0).getTime());
    }


}
