package com.smalltricks.photosearch;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
	private String fullUrl;
	private String thumbUrl;
	
	
	public String getFullUrl() {
		return fullUrl;
	}
	
	public String getThumbUrl() {
		return thumbUrl;
	}
	
	public String toString(){
		return thumbUrl;
	}
	
	ImageResult(JSONObject json){
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl =json.getString("tbUrl");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			this.thumbUrl =null;
			this.fullUrl = null;
		}
		
	}

	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray imageJsonResults) {
		// TODO Auto-generated method stub
		ArrayList<ImageResult> results =  new ArrayList<ImageResult>();
		for(int i=0;i<imageJsonResults.length();i++){
			try{
				results.add(new ImageResult(imageJsonResults.getJSONObject(i)));
			}catch (JSONException e){
				e.printStackTrace();
			}
		}
		
		return results;
	}
	
	
	
}
