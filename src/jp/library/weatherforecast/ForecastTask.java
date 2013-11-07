/*
 * Copyright (C) 2013 M.Nakamura
 *
 * This software is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 2.1 Japan License.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://creativecommons.org/licenses/by-nc-sa/2.1/jp/legalcode
 */
package jp.library.weatherforecast;

import java.util.*;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ForecastTask extends AsyncTask<Integer, Integer, Long> {
	private static final String TAG = "ForecastTask";
	public static final String LASTUPDATE = "LASTUPDATE";
	public static final String TITLE_NO = "TITLE_";
	public static final String DESCRIPTION_NO = "DESCRIPTION_";
	public static final String PUBDATE = "PUBDATE";
	private ArrayList<Message> mMessages;
	private Context mContext;
	private int id = 4410;

	public ForecastTask(Context context) {
		this.mContext = context.getApplicationContext();
	}

	@Override
	protected Long doInBackground(Integer... params) {
		if (params.length > 0)
			id = params[0];
		String uri = "http://feedproxy.google.com/hitokuchi_"
				+ String.valueOf(id);
		Log.i(TAG, "doInBackground - " + uri);
		try {
			AndroidSaxFeedParser paser = new AndroidSaxFeedParser(uri);
			mMessages = paser.parse();
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
			return 0L;
		}
		return 0L;
	}

	@Override
	protected void onPostExecute(Long result) {
		try {
			Log.i(TAG, "onPostExecute - " + String.valueOf(id));
			StaticHash hash = new StaticHash(mContext);
			hash.put(String.valueOf(id), LASTUPDATE, System.currentTimeMillis());
			if (mMessages == null)
				return;
			for (int i = 0; i < mMessages.size(); i++) {
				hash.put(String.valueOf(id), TITLE_NO + String.valueOf(i),
						mMessages.get(i).getTitle());
				String forecast = mMessages.get(i).getDescription()
						.replaceAll("<br />", "");
				forecast = forecast.replace("晴れ", "晴");
				forecast = forecast.replace("くもり", "曇");
				forecast = forecast.replace("降水確率: ", "");
				forecast = forecast.replace("\n ", "");
				hash.put(String.valueOf(id),
						DESCRIPTION_NO + String.valueOf(i), forecast);
			}
			if (mMessages.size() > 0) {
				hash.put(String.valueOf(id), PUBDATE, mMessages.get(0)
						.getDate());
			}
			if (WeatherForecast.mOnPostExecute != null) {
				WeatherForecast.mOnPostExecute.onPostExecute();
			}
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		}
	}
}
