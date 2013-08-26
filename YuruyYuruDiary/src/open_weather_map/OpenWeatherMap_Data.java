package open_weather_map;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class OpenWeatherMap_Data {

	/* JSON形式の天気情報文字列を返す */
	public String getJsonWeatherData(String openweatherURL) throws IOException{

		/* 天気情報のリクエスト */
		String requestURL = openweatherURL;
		URL url = new URL(requestURL);
		InputStream is = url.openConnection().getInputStream();


		/* JSON形式での結果の取得 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		StringBuilder sb = new StringBuilder();
		String line;
		while((line=reader.readLine()) != null){
			sb.append(line);
		}

		//JSON形式の天気データを返す
		return sb.toString();
	}


	/* 天気情報を独自構造体で返す */
	public WeatherData getWeatherData(String jsonData) throws JSONException{

		/* 要素を宣言 */
		int pointID;
		String cityName;
		float currentTemperature, minTemprerature, maxTemprerature;
		int humidity = 0;
		long time;
		String iconID;

		/* JSON形式の情報を取得 */
		JSONObject rootObj = new JSONObject(jsonData);
		JSONArray listArray = rootObj.getJSONArray("list");
		JSONObject data = listArray.getJSONObject(0);

		// 地点ID
		pointID = data.getInt("id");

		//地点名
		cityName = data.getString("name");

		//気温(Kから℃に変換
		JSONObject temperatures = data.getJSONObject("main");
		currentTemperature = (float)(temperatures.getDouble("temp") - 273.15f);
		minTemprerature = (float)(temperatures.getDouble("temp_min") - 273.15f);
		maxTemprerature = (float)(temperatures.getDouble("temp_max") - 273.15f);

		//湿度
		if(temperatures.has("humidity")) humidity = temperatures.getInt("humidity");

		//取得時間
		time = data.getLong("dt");

		//天気
		JSONArray weatherArray = data.getJSONArray("weather");
		JSONObject weatherData = weatherArray.getJSONObject(0);
		iconID = weatherData.getString("icon");

		/* 天気オブジェクトを返す */
		return new WeatherData(pointID, cityName, currentTemperature, minTemprerature, maxTemprerature, humidity, time, iconID);
	}

	/* 指定IDの天気アイコンを返す */
	public Bitmap getWeatherIcon(String id) throws IOException{
		/* URLにコネクト */
		String requestURL = "http://openweathermap.org/img/w/"+id;
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setDoInput(true);
		con.connect();

		/* アイコンを読み込み */
		BufferedInputStream in = new BufferedInputStream(con.getInputStream());
		Bitmap icon = BitmapFactory.decodeStream(in);
		in.close();
		con.disconnect();

		//取得したBitmapアイコンを戻す
		return icon;
	}
}
