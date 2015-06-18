/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import com.chdryra.android.mygenerallibrary.ViewHolderData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhDate extends VhText {
    public VhDate() {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                GvDateList.GvDate date = (GvDateList.GvDate) data;
                DateFormat format = SimpleDateFormat.getDateInstance();
                return format.format(date.getDate());
            }
        });
    }
}
