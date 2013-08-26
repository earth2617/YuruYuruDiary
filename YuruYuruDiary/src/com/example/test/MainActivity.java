package com.example.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import open_weather_map.OpenWeatherMap_Data;
import open_weather_map.WeatherData;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	/*********** メンバ定数 ***********/

	String SOURCE_NAME = "MainActivity: ";

	/********** ウィジェット **********/

	private TextView info;
	private ImageView iconView;

	/********************************/


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* テキストエリアのフォントの設定 */
		info = (TextView)findViewById(R.id.txtView);
//		info.setTypeface(Typeface.createFromAsset(getAssets(), "mikachan_puchi.ttf"));
		try {
			info.setTypeface(fontUnzipFromAssets("mikachan_puchi.zip"));
		} catch (IOException e1) {
			Log.w("exception", SOURCE_NAME + "フォントの解凍に失敗しました", e1);
		}

		//天気情報取得用インスタンス
		OpenWeatherMap_Data data = new OpenWeatherMap_Data();

		/* JSON形式での天気情報取得 */
		String json = null;
		try {
			json = data.getJsonWeatherData("http://api.openweathermap.org/data/2.5/find?lat=35.3951&lon=139.4152&cnt=1");
		} catch (IOException e) {
			Log.w("exception", "", e);
			/* エラーダイアログの表示 */
			new AlertDialog.Builder(this)
			.setTitle("エラー")
			.setMessage("天気情報の取得にエラーが発生しました")
			.setPositiveButton("OK", null)
			.show();
		}

		/* JSON形式から天気オブジェクトへの変換 */
		WeatherData weather = null;
		try {
			weather = data.getWeatherData(json);
		} catch (JSONException e) {
			Log.w("exception", SOURCE_NAME, e);
			/* エラーダイアログの表示 */
			new AlertDialog.Builder(this)
			.setTitle("エラー")
			.setMessage("天気情報の変換にエラーが発生しました")
			.setPositiveButton("OK", null)
			.show();
		}

		//ビューに取得した情報を表示
		info.setText(weather.getData());

		//イメージビューにアイコンを表示
		iconView = (ImageView)findViewById(R.id.iconView);
		try {
			iconView.setImageBitmap(data.getWeatherIcon(weather.getIconID()));
		} catch (IOException e) {
			Log.w("exception", SOURCE_NAME, e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/********** メソッド **********/
	 /* @throws IOException
	  */

	@SuppressWarnings("unused")
	public Typeface fontUnzipFromAssets(String name) throws IOException{
		//エントリーの取得
		AssetManager am = getResources().getAssets();
		InputStream is = am.open(name, AssetManager.ACCESS_STREAMING);
		ZipInputStream zis = new ZipInputStream(is);
		ZipEntry zip = zis.getNextEntry();
		Log.d("debug", "zip.getName = " + zip.getName());

		//ZIPの解凍
		String path = null;
		if(zip != null){
			path = getFilesDir().toString() + "/" + zip.getName();
			FileOutputStream fos = new FileOutputStream(path, false);
			byte[] buf = new byte[1024];

			for(int size=0; size>-1; size=zis.read(buf, 0, buf.length)){
				fos.write(buf, 0, size);
			}
			fos.close();
			zis.closeEntry();
		}else{
			Log.w("exception", SOURCE_NAME + "zipエントリの取得に失敗しました");
		}
		zis.close();

		//解凍したフォントを返す
		if(path != null){
			return Typeface.createFromFile(path);
		}else{
			return null;
		}
	}

	/********************************/

}
