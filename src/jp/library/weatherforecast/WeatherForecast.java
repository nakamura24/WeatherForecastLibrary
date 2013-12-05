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

import java.text.SimpleDateFormat;
import java.util.*;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

public class WeatherForecast {
	private static final String TAG = "WeatherForecastLibrary";
	private static final HashMap<Integer, String> Locations = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(1100, "道北 宗谷地方(稚内)");
			put(1200, "道北 上川地方(旭川)");
			put(1300, "道北 留萌地方(留萌)");
			put(1400, "道央 石狩地方(札幌)");
			put(1500, "道央 空知地方(岩見沢)");
			put(1600, "道央 後志地方(倶知安)");
			put(1710, "道東 網走地方(網走)");
			put(1720, "道東 北見地方(北見)");
			put(1730, "道東 紋別地方(紋別)");
			put(1800, "道東 根室地方(根室)");
			put(1900, "道東 釧路地方(釧路)");
			put(2000, "道東 十勝地方(帯広)");
			put(2100, "道南 胆振地方(室蘭)");
			put(2200, "道南 日高地方(浦河)");
			put(2300, "道南 渡島地方(函館)");
			put(2400, "道南 檜山地方(江差)");
			put(3110, "青森県 津軽(青森)");
			put(3120, "青森県 下北(むつ)");
			put(3130, "青森県 三八上北(八戸)");
			put(3210, "秋田県 沿岸(秋田)");
			put(3220, "秋田県 内陸(横手)");
			put(3310, "岩手県 内陸(盛岡)");
			put(3320, "岩手県 沿岸北部(宮古)");
			put(3330, "岩手県 沿岸南部(大船渡)");
			put(3410, "宮城県 東部(仙台)");
			put(3420, "宮城県 西部(白石)");
			put(3510, "山形県 村山(山形)");
			put(3520, "山形県 置賜(米沢)");
			put(3530, "山形県 庄内(酒田)");
			put(3540, "山形県 最上(新庄)");
			put(3610, "福島県 中通り(福島)");
			put(3620, "福島県 浜通り(小名浜)");
			put(3630, "福島県 会津(若松)");
			put(5010, "静岡県 中部(静岡)");
			put(5020, "静岡県 伊豆(網代)");
			put(5030, "静岡県 東部(三島)");
			put(5040, "静岡県 西部(浜松)");
			put(5110, "愛知県 西部(名古屋)");
			put(5120, "愛知県 東部(豊橋)");
			put(5210, "岐阜県 美濃地方(岐阜)");
			put(5220, "岐阜県 飛騨地方(高山)");
			put(5310, "三重県 北中部(津)");
			put(5320, "三重県 南部(尾鷲)");
			put(5510, "富山県 東部(富山)");
			put(5520, "富山県 西部(伏木)");
			put(5610, "石川県 加賀(金沢)");
			put(5620, "石川県 能登(輪島)");
			put(5710, "福井県 嶺北(福井)");
			put(5720, "福井県 嶺南(敦賀)");
			put(5410, "新潟県 下越(新潟)");
			put(5420, "新潟県 中越(長岡)");
			put(5430, "新潟県 上越(高田)");
			put(5440, "新潟県 佐渡(相川)");
			put(4010, "茨城県 北部(水戸)");
			put(4020, "茨城県 南部(土浦)");
			put(4110, "栃木県 南部(宇都宮)");
			put(4120, "栃木県 北部(大田原)");
			put(4210, "群馬県 南部(前橋)");
			put(4220, "群馬県 北部(みなかみ)");
			put(4310, "埼玉県 南部(さいたま)");
			put(4320, "埼玉県 北部(熊谷)");
			put(4330, "埼玉県 秩父地方(秩父)");
			put(4410, "東京都 東京地方(東京)");
			put(4420, "東京都 伊豆諸島北部(大島)");
			put(4430, "東京都 伊豆諸島南部(八丈島)");
			put(9600, "東京都 小笠原諸島(父島)");
			put(4510, "千葉県 北西部(千葉)");
			put(4520, "千葉県 北東部(銚子)");
			put(4530, "千葉県 南部(館山)");
			put(4610, "神奈川県 東部(横浜)");
			put(4620, "神奈川県 西部(小田原)");
			put(4810, "長野県 北部(長野)");
			put(4820, "長野県 中部(松本)");
			put(4830, "長野県 南部(飯田)");
			put(4910, "山梨県 中・西部(甲府)");
			put(4920, "山梨県 東部・富士五湖(河口湖)");
			put(6010, "滋賀県 南部(大津)");
			put(6020, "滋賀県 北部(彦根)");
			put(6100, "京都府 南部(京都)");
			put(400, "京都府 北部(舞鶴)");
			put(6200, "大阪府 大阪(大阪)");
			put(6310, "兵庫県 南部(神戸)");
			put(6320, "兵庫県 北部(豊岡)");
			put(6410, "奈良県 北部(奈良)");
			put(6420, "奈良県 南部(風屋)");
			put(6510, "和歌山県 北部(和歌山)");
			put(6520, "和歌山県 南部(潮岬)");
			put(6610, "岡山県 南部(岡山)");
			put(6620, "岡山県 北部(津山)");
			put(6710, "広島県 南部(広島)");
			put(6720, "広島県 北部(庄原)");
			put(6810, "島根県 東部(松江)");
			put(6820, "島根県 西部(浜田)");
			put(6830, "島根県 隠岐(西郷)");
			put(6910, "鳥取県 東部(鳥取)");
			put(6920, "鳥取県 中・西部(米子)");
			put(8110, "山口県 西部(下関)");
			put(8120, "山口県 中部(山口)");
			put(8130, "山口県 東部(柳井)");
			put(8140, "山口県 北部(萩)");
			put(7110, "徳島県 北部(徳島)");
			put(7120, "徳島県 南部(日和佐)");
			put(7200, "香川県 高松(高松)");
			put(7310, "愛媛県 中予(松山)");
			put(7320, "愛媛県 東予(新居浜)");
			put(7330, "愛媛県 南予(宇和島)");
			put(7410, "高知県 中部(高知)");
			put(7420, "高知県 東部(室戸岬)");
			put(7430, "高知県 西部(清水)");
			put(8210, "福岡県 福岡地方(福岡)");
			put(8220, "福岡県 北九州地方(八幡)");
			put(8230, "福岡県 筑豊地方(飯塚)");
			put(8240, "福岡県 筑後地方(久留米)");
			put(8310, "大分県 中部(大分)");
			put(8320, "大分県 北部(中津)");
			put(8330, "大分県 西部(日田)");
			put(8340, "大分県 南部(佐伯)");
			put(8410, "長崎県 南部(長崎)");
			put(8420, "長崎県 北部(佐世保)");
			put(700, "長崎県 壱岐・対馬(厳原)");
			put(800, "長崎県 五島(福江)");
			put(8510, "佐賀県 南部(佐賀)");
			put(8520, "佐賀県 北部(伊万里)");
			put(8610, "熊本県 熊本地方(熊本)");
			put(8620, "熊本県 阿蘇地方(阿蘇乙姫)");
			put(8630, "熊本県 天草・芦北地方(牛深)");
			put(8640, "熊本県 球磨地方(人吉)");
			put(8710, "宮崎県 南部平野部(宮崎)");
			put(8720, "宮崎県 北部平野部(延岡)");
			put(8730, "宮崎県 南部山沿い(都城)");
			put(8740, "宮崎県 北部山沿い(高千穂)");
			put(8810, "鹿児島県 薩摩地方(鹿児島)");
			put(8820, "鹿児島県 大隅地方(鹿屋)");
			put(8830, "鹿児島県 種子島・屋久島地方(種子島)");
			put(1000, "鹿児島県 奄美地方(名瀬)");
			put(9110, "沖縄県 本島中南部(那覇)");
			put(9120, "沖縄県 本島北部(名護)");
			put(9130, "沖縄県 久米島(久米島)");
			put(9200, "沖縄県 大東島地方(南大東島)");
			put(9300, "沖縄県 宮古島地方(宮古島)");
			put(9400, "沖縄県 石垣島地方(石垣島)");
			put(9500, "沖縄県 与那国島地方(与那国島)");
		}
	};
	private static final int[] LocationKyes = { 1100, 1200, 1300, 1400, 1500,
			1600, 1710, 1720, 1730, 1800, 1900, 2000, 2100, 2200, 2300, 2400,
			3110, 3120, 3130, 3210, 3220, 3310, 3320, 3330, 3410, 3420, 3510,
			3520, 3530, 3540, 3610, 3620, 3630, 5010, 5020, 5030, 5040, 5110,
			5120, 5210, 5220, 5310, 5320, 5510, 5520, 5610, 5620, 5710, 5720,
			5410, 5420, 5430, 5440, 4010, 4020, 4110, 4120, 4210, 4220, 4310,
			4320, 4330, 4410, 4420, 4430, 9600, 4510, 4520, 4530, 4610, 4620,
			4810, 4820, 4830, 4910, 4920, 6010, 6020, 6100, 400, 6200, 6310,
			6320, 6410, 6420, 6510, 6520, 6610, 6620, 6710, 6720, 6810, 6820,
			6830, 6910, 6920, 8110, 8120, 8130, 8140, 7110, 7120, 7200, 7310,
			7320, 7330, 7410, 7420, 7430, 8210, 8220, 8230, 8240, 8310, 8320,
			8330, 8340, 8410, 8420, 700, 800, 8510, 8520, 8610, 8620, 8630,
			8640, 8710, 8720, 8730, 8740, 8810, 8820, 8830, 1000, 9110, 9120,
			9130, 9200, 9300, 9400, 9500, };
	public static OnPostExecute mOnPostExecute = null;

	public interface OnPostExecute {
		void onPostExecute();
	}

	public void setOnPostExecute(OnPostExecute onPostExecute) {
		mOnPostExecute = onPostExecute;
	}

	public int[] getLocationIDs() {
		return LocationKyes;
	}

	public String getLocationName(int id) {
		return Locations.get(id);
	}

	public int getBitmapResource(String forecast) {
		if (forecast.equals("晴")) {
			return R.drawable.weather01;
		}
		if (forecast.equals("晴時々曇")) {
			return R.drawable.weather02;
		}
		if (forecast.equals("晴時々雨")) {
			return R.drawable.weather03;
		}
		if (forecast.equals("晴時々雪")) {
			return R.drawable.weather04;
		}
		if (forecast.equals("晴のち曇")) {
			return R.drawable.weather05;
		}
		if (forecast.equals("晴のち雨")) {
			return R.drawable.weather06;
		}
		if (forecast.equals("晴のち雪")) {
			return R.drawable.weather07;
		}
		if (forecast.equals("曇")) {
			return R.drawable.weather08;
		}
		if (forecast.equals("曇時々晴")) {
			return R.drawable.weather09;
		}
		if (forecast.equals("曇時々雨")) {
			return R.drawable.weather10;
		}
		if (forecast.equals("曇時々雪")) {
			return R.drawable.weather11;
		}
		if (forecast.equals("曇のち晴")) {
			return R.drawable.weather12;
		}
		if (forecast.equals("曇のち雨")) {
			return R.drawable.weather13;
		}
		if (forecast.equals("曇のち雪")) {
			return R.drawable.weather14;
		}
		if (forecast.equals("雨")) {
			return R.drawable.weather15;
		}
		if (forecast.equals("雨時々晴")) {
			return R.drawable.weather16;
		}
		if (forecast.equals("雨時々曇")) {
			return R.drawable.weather17;
		}
		if (forecast.equals("雨時々雪")) {
			return R.drawable.weather18;
		}
		if (forecast.equals("雨のち晴")) {
			return R.drawable.weather19;
		}
		if (forecast.equals("雨のち曇")) {
			return R.drawable.weather20;
		}
		if (forecast.equals("雨のち雪")) {
			return R.drawable.weather21;
		}
		if (forecast.equals("雪")) {
			return R.drawable.weather23;
		}
		if (forecast.equals("雪時々晴")) {
			return R.drawable.weather24;
		}
		if (forecast.equals("雪時々曇")) {
			return R.drawable.weather25;
		}
		if (forecast.equals("雪時々雨")) {
			return R.drawable.weather26;
		}
		if (forecast.equals("雪のち晴")) {
			return R.drawable.weather27;
		}
		if (forecast.equals("雪のち曇")) {
			return R.drawable.weather28;
		}
		if (forecast.equals("雪のち雨")) {
			return R.drawable.weather29;
		}

		return 0;
	}

	public void getForecast(Context context, int id) {
		Log.i(TAG, "getForecast");
		try {
			// 最後に取得してから一時間経過していない
			Date lastUpdate = getLastUpdate(context, id);
			Date now = new Date();
			if((now.getTime() - lastUpdate.getTime()) < 3600000) {
				if (mOnPostExecute != null) {
					mOnPostExecute.onPostExecute();
				}
				return;
			}
			// システムから接続情報をとってくる
			ConnectivityManager conMan = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			boolean NetworkCONNECTED = false;
			NetworkInfo[] networkInfos = conMan.getAllNetworkInfo();
			for (NetworkInfo NetworkInfo : networkInfos) {
				if (NetworkInfo.getState() == State.CONNECTED) {
					NetworkCONNECTED = true;
					break;
				}
			}
			// ネットワーク接続状態じゃない場合
			if (!NetworkCONNECTED) {
				// ネットワーク未接続
				Log.e(TAG, "ネットワーク未接続");
				return;
			}
			ForecastTask task = new ForecastTask(context);
			task.execute(id);
			Log.d(TAG, "getForecast ForecastTask");
		} catch (Exception ex) {
			ExceptionLog.Log(TAG, ex);
		}
	}

	public Date getLastUpdate(Context context, int id) {
		Log.i(TAG, "getLastUpdate");
		try {
			StaticHash hash = new StaticHash(context);
			long update = hash.get(String.valueOf(id), ForecastTask.LASTUPDATE,
					0L);
			return new Date(update);
		} catch (Exception ex) {
			ExceptionLog.Log(TAG, ex);
		}
		return null;
	}

	public Date getPublicDate(Context context, int id) {
		Log.i(TAG, "getPublicDate");
		try {
			SimpleDateFormat FORMATTER = new SimpleDateFormat(
					"EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
			StaticHash hash = new StaticHash(context);
			String pubdate = hash.get(String.valueOf(id), ForecastTask.PUBDATE,
					FORMATTER.format(new Date()));
			Date date = FORMATTER.parse(pubdate);
			return date;
		} catch (Exception ex) {
			ExceptionLog.Log(TAG, ex);
		}
		return null;
	}

	public class OneDayForecast {
		public String Hour = null;
		public String Forecast = null;
		public String Temp = null;

		public OneDayForecast(String forecast) {
			Log.d(TAG, "OneDayForecast - " + forecast);
			try {
				String[] forecasts = forecast.split(" ");
				if (forecasts.length < 3)
					return;
				Hour = forecasts[0];
				Forecast = forecasts[1];
				Temp = forecasts[2];
			} catch (Exception ex) {
				ExceptionLog.Log(TAG, ex);
			}
		}
	}

	public class WeeklyForecast {
		public String Date = null;
		public String Forecast = null;
		public String Temp = null;
		public String MaxTemp = null;
		public String MinTemp = null;
		public String Probability = null;
		public ArrayList<String> Probabilitys = new ArrayList<String>();

		public WeeklyForecast(String forecast) {
			Log.d(TAG, "WeeklyForecast - " + forecast);
			try {
				String[] forecasts = forecast.split(" ");
				Date = forecasts[0];
				Forecast = forecasts[1];
				if (forecasts.length >= 4) {
					Temp = forecasts[2];
					String[] Temps = Temp.split("/");
					MaxTemp = Temps[0];
					MinTemp = Temps[1];
					Probability = forecasts[3];
					String[] probabilitys = Probability.split("/");
					for (int i = 0; i < probabilitys.length; i++) {
						Probabilitys.add(probabilitys[i]);
					}
				} else {
					Temp = "";
					MaxTemp = "";
					MinTemp = "";
					Probability = forecasts[2];
					String[] probabilitys = Probability.split("/");
					for (int i = 0; i < probabilitys.length; i++) {
						Probabilitys.add(probabilitys[i]);
					}
				}
			} catch (Exception ex) {
				ExceptionLog.Log(TAG, ex);
			}
		}
	}

	public ArrayList<OneDayForecast> getOneDayForecast(Context context, int id) {
		Log.i(TAG, "getOneDayForecast");
		try {
			ArrayList<OneDayForecast> array = new ArrayList<OneDayForecast>();
			StaticHash hash = new StaticHash(context);
			String forecast = hash.get(String.valueOf(id),
					ForecastTask.DESCRIPTION_NO + String.valueOf(4), null);
			if (forecast == null)
				return null;
			String[] forecasts = forecast.split("\n");
			for (int i = 0; i < forecasts.length; i++) {
				OneDayForecast oneDayForecast = new OneDayForecast(forecasts[i]);
				if (oneDayForecast.Forecast != null)
					array.add(oneDayForecast);
			}
			return array;
		} catch (Exception ex) {
			ExceptionLog.Log(TAG, ex);
		}
		return null;
	}

	public ArrayList<WeeklyForecast> getWeeklyForecast(Context context, int id) {
		Log.i(TAG, "getWeeklyForecast");
		try {
			ArrayList<WeeklyForecast> array = new ArrayList<WeeklyForecast>();
			StaticHash hash = new StaticHash(context);
			String forecast = hash.get(String.valueOf(id),
					ForecastTask.DESCRIPTION_NO + String.valueOf(5), null);
			if (forecast == null)
				return null;
			String[] forecasts = forecast.split("\n");
			for (int i = 0; i < forecasts.length; i++) {
				array.add(new WeeklyForecast(forecasts[i]));
			}
			return array;
		} catch (Exception ex) {
			ExceptionLog.Log(TAG, ex);
		}
		return null;
	}

	public static class ExceptionLog {
		public static void Log(String tag, Exception e) {
			for (int i = 0; i < e.getStackTrace().length; i++) {
				Log.e(tag,
						e.getStackTrace()[i].getFileName()
								+ ":"
								+ String.valueOf(e.getStackTrace()[i]
										.getLineNumber()) + ":"
								+ e.getStackTrace()[i].getMethodName());
			}
		}
	}
}
