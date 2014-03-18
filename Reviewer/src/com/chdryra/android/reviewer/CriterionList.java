package com.chdryra.android.reviewer;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class CriterionList {

	private LinkedHashMap<String, Criterion> mCriteria = new LinkedHashMap<String, Criterion>();
	private float mAverageRating;
	private boolean mValidAverage = false;
	
	public static enum Result {ADDED, DELETED, CHANGED, EXISTS, NOT_EXIST, NULLNAME};
	
	public CriterionList() {
	}

	public LinkedHashMap<String, Criterion> getCriterionHashMap() {		
			return mCriteria;
	}
	
	private void invalidAverage() {
		mValidAverage = false;
	}
	
	private void validAverage() {
		mValidAverage = true;
	}
	
	public  Result addCriterion(String name) {
      	if(name.length() > 0)
		{      		      		
      		if(!criterionExists(name)) {
      			mCriteria.put(name, new Criterion(name));
      			invalidAverage();
      			return Result.ADDED;
			}      		
      		
      		return Result.EXISTS;
		};
		
		return Result.NULLNAME;
	}
	
	public Result deleteCriterion(String name) {
		
		if(criterionExists(name)) {
			mCriteria.remove(name);
			invalidAverage();
			return Result.DELETED;
		} 
		
		return Result.NOT_EXIST;
	}

	public Criterion getCriterion(String criterionName) {
		return mCriteria.get(criterionName);
	}
	
	public Result changeCriterionName(String currentName, String newName) {
		if(newName != null)
		{
			if(criterionExists(currentName)) {
				Criterion c = mCriteria.remove(currentName);
				c.setName(newName);
				mCriteria.put(newName, c);
				return Result.CHANGED;
			}
			
			return Result.NOT_EXIST;
		}
		
		return Result.NULLNAME;
	};
	
	public Result changeCriterionRating(String name, float rating) {
		if(criterionExists(name)) {
			mCriteria.get(name).setRating(rating);
			invalidAverage();
			return Result.CHANGED;
		}
		
			return Result.NOT_EXIST;
	};
	
	public boolean criterionExists(String name) {
		return mCriteria.get(name) != null;
	}

	public int size() {
		return mCriteria.size();
	}
	
	public float getAverageRating() {
		if(!mValidAverage) {
			float total = 0;
			Iterator<Criterion> it = mCriteria.values().iterator();
			while (it.hasNext())
			  total += it.next().getRating();
			mAverageRating = total / size();
			validAverage();
		}
		
		return mAverageRating;		
	}
	
	public void deleteComments() {
		Iterator<Criterion> it = mCriteria.values().iterator();
		while (it.hasNext())
		  it.next().deleteComment();
	}
}