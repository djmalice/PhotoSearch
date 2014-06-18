package com.smalltricks.photosearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchActivity extends Activity {
	EditText etSearchText;
	GridView gvResults;
	Button btSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_search_activity);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
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
        
        // Endless Scrolling
        gvResults.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				customLoadMoreDataApi(totalItemsCount);
			}
		});
    }
    
    public void customLoadMoreDataApi(int offset){
    	String searchQuery = etSearchText.getText().toString();
    	
    	String imageSize = pref.getString("imageSize", "");
    	String imageType = pref.getString("imageType", "");
    	String colorFilter = pref.getString("colorFilter", "");
    	String siteFilter = pref.getString("siteFilter", "");
    	if(isNetworkAvailable()){
    		AsyncHttpClient client = new AsyncHttpClient();
        	String urlParams = new String("&imgsz=" + imageSize + "&imgtype=" + imageType + "&imgcolor=" + colorFilter + "&as_sitesearch=" + siteFilter);
        	
        	client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + 
        			"start=" + offset + "&v=1.0&q=" + Uri.encode(searchQuery) + urlParams, 
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
        				if(imageJsonResults != null)
        					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
        				
        				Log.d("DEBUG",imageResults.toString());
        			}
        		
        			});
        Log.d("DEBUG","https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + 
    			"start=" + 0 + "&v=1.0&q=" + Uri.encode(searchQuery)+ urlParams);
    	}
     }
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	getMenuInflater().inflate(R.menu.main_menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    public void showSettings(MenuItem item){
    	Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
    	startActivity(i);
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
    
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    
    public void searchForResults(View v){
    	String searchQuery = etSearchText.getText().toString();
    	
    	String imageSize = pref.getString("imageSize", "");
    	String imageType = pref.getString("imageType", "");
    	String colorFilter = pref.getString("colorFilter", "");
    	String siteFilter = pref.getString("siteFilter", "");
		if (isNetworkAvailable()) {
			AsyncHttpClient client = new AsyncHttpClient();
			String urlParams = new String("&imgsz=" + imageSize + "&imgtype="
					+ imageType + "&imgcolor=" + colorFilter
					+ "&as_sitesearch=" + siteFilter);

			client.get(
					"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
							+ "start=" + 0 + "&v=1.0&q="
							+ Uri.encode(searchQuery) + urlParams,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject response) {
							JSONArray imageJsonResults = null;
							try {
								imageJsonResults = response.getJSONObject(
										"responseData").getJSONArray("results");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (imageJsonResults != null) {
								imageResults.clear();
								imageAdapter.addAll(ImageResult
										.fromJSONArray(imageJsonResults));
							}
							Log.d("DEBUG", imageResults.toString());
						}

					});

			Log.d("DEBUG",
					"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
							+ "start=" + 0 + "&v=1.0&q="
							+ Uri.encode(searchQuery) + urlParams);
    	} else {
    		Toast.makeText(this,"Check Network Connectivity",Toast.LENGTH_SHORT).show();
    	}
 	
    }
}
