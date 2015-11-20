package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuEditTags extends MenuDataEdit<GvTagList.GvTag> {
    private static final GvDataType<GvTagList.GvTag> TYPE = GvTagList.GvTag.TYPE;
    private TagAdjuster mTagAdjuster;


    public MenuEditTags(TagAdjuster tagAdjuster) {
        super(TYPE);
        mTagAdjuster = tagAdjuster;
    }

    @Override
    protected void doDoneSelected() {
        mTagAdjuster.adjustTagsIfNecessary(getEditor());
        super.doDoneSelected();
    }

    @Override
    protected void doDeleteSelected() {
        super.doDeleteSelected();
        mTagAdjuster.setCurrentSubjectTag(null);
        mTagAdjuster.adjustTagsIfNecessary(getEditor());
    }
}
