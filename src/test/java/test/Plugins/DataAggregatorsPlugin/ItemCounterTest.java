package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ItemCounter;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.ItemGetter;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemCounterTest {
    private static final Random RAND = new Random();
    private static final int MAX_ITEMS = 5;

    @Test
    public void totalTypesOfItemIsZeroForEmptyData() {
        ArrayList<Datum> data = new ArrayList<>();

        ItemCounter<Datum, String> counter = new ItemCounter<>(newGetter());
        counter.performCount(data);
        assertThat(counter.getCountOfItemTypes(), is(0));
    }

    @Test
    public void totalTypesOfNonModeItemIsZeroForEmptyData() {
        ArrayList<Datum> data = new ArrayList<>();

        ItemCounter<Datum, String> counter = new ItemCounter<>(newGetter());
        counter.performCount(data);
        assertThat(counter.getNonModeCount(), is(0));
    }

    @Test
    public void totalTypesOfItemIsCorrect() {
        String item1 = RandomString.nextWord();
        String item2 = RandomString.nextWord();
        String item3 = RandomString.nextWord();

        ArrayList<Datum> data = new ArrayList<>();
        addItems(item1, data);
        addItems(item2, data);
        addItems(item3, data);

        ItemCounter<Datum, String> counter = new ItemCounter<>(newGetter());
        counter.performCount(data);
        assertThat(counter.getCountOfItemTypes(), is(3));
    }

    @Test
    public void totalTypesOfNonModeItemIsCorrect() {
        String item1 = RandomString.nextWord();
        String item2 = RandomString.nextWord();
        String item3 = RandomString.nextWord();

        ArrayList<Datum> data = new ArrayList<>();
        addItems(item1, data);
        addItems(item2, data);
        addItems(item3, data);

        ItemCounter<Datum, String> counter = new ItemCounter<>(newGetter());
        counter.performCount(data);
        assertThat(counter.getNonModeCount(), is(2));
    }

    @Test
    public void getModeItemIsCorrect() {
        String item1 = RandomString.nextWord();
        String item2 = RandomString.nextWord();
        String item3 = RandomString.nextWord();

        ArrayList<Datum> data = new ArrayList<>();
        int num1 = addItems(item1, data);
        int num2 = addItems(item2, data);
        int num3 = addItems(item3, data);
        String modeItem = getModeItem(item1, item2, item3, num1, num2, num3);

        ItemCounter<Datum, String> counter = new ItemCounter<>(newGetter());
        counter.performCount(data);
        assertThat(counter.getModeItem(), is(modeItem));
    }

    private String getModeItem(String item1, String item2, String item3, int num1, int num2, int
            num3) {
        ArrayList<Integer> numData = new ArrayList<>();
        numData.add(num1);
        numData.add(num2);
        numData.add(num3);
        Integer maxNum = Collections.max(numData);
        String modeItem = item1;
        if(maxNum == num2) modeItem = item2;
        if(maxNum == num3) modeItem = item3;
        return modeItem;
    }

    private ItemGetter<Datum, String> newGetter() {
        return new ItemGetter<Datum, String>() {
            @Override
            public String getItem(Datum datum) {
                return datum.getData();
            }
        };
    }
    private int addItems(String item, ArrayList<Datum> data) {
        int numItem = 1 + RAND.nextInt(MAX_ITEMS);
        for(int i = 0; i < numItem; ++i) {
            data.add(new Datum(item));
        }
        return numItem;
    }

    private class Datum {
        private String mData;
        private Datum(String data) {
            mData = data;
        }

        public String getData() {
            return mData;
        }
    }
}
