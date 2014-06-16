package com.smalltricks.photosearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchActivity extends Activity {
	EditText etSearchText;
	GridView gvResults;
	Button btSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_search_activity);
        setupViews();
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id) {
        		// TODO Auto-generated method stub
        	Intent i = new Intent(getApplicationContext(), FullImage.class);
        	ImageResult imageResult = imageResults.get(position);
        	i.putExtra("url",imageResult.getFullUrl());
        	startActivity(i);
        	}
		});
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	return super.onOptionsItemSelected(item);
    }
    public void setupViews(){
    	etSearchText = (EditText)findViewById(R.id.etSearchText);
    	gvResults = (GridView)findViewById(R.id.gvResults);
    	btSearch = (Button)findViewById(R.id.btSearch);
    }
    
    public void searchForResults(View v){
    	String searchQuery = etSearchText.getText().toString();
    	
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + 
    			"start=" + 0 + "&v=1.0&q=" + Uri.encode(searchQuery), 
    			new JsonHttpResponseHandler() {
    			@Override
    			public void onSuccess(JSONObject response){
    				JSONArray imageJsonResults = null;
    				try {
						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				imageResults.clear();
    				imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    				Log.d("DEBUG",imageResults.toString());
    			}
    		
    			});
    Log.d("DEBUG","https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + 
			"start=" + 0 + "v=1.0&q=" + Uri.encode(searchQuery));	
    	
    	
    	
    	
    	
    }
}
