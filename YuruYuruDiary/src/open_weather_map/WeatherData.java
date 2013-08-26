package open_weather_map;

//天気情報を格納した構造体
public class WeatherData {

	/********** メンバ変数 **********/

	private int pointID;				//地点ID
	private String cityName;			//地点名
	private float currentTemperature;	//現在の気温
	private float minTemprerature;		//今日の最低気温
	private float maxTemprerature;		//今日の最高気温
	private int humidity;				//現在の湿度
	private long time;					//これらを取得した時間
	private String iconID;				//天気アイコンを取得する際のID

	/********************************/

	public int getPointID() {
		return pointID;
	}
	public void setPointID(int pointID) {
		this.pointID = pointID;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public float getCurrentTemperature() {
		return currentTemperature;
	}
	public void setCurrentTemperature(float currentTemperature){
		this.currentTemperature = currentTemperature;
	}
	public float getMinTemprerature() {
		return minTemprerature;
	}
	public void setMinTemprerature(float minTemprerature) {
		this.minTemprerature = minTemprerature;
	}
	public float getMaxTemprerature() {
		return maxTemprerature;
	}
	public void setMaxTemprerature(float maxTemprerature) {
		this.maxTemprerature = maxTemprerature;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getIconID() {
		return iconID;
	}
	public void setIconID(String iconID) {
		this.iconID = iconID;
	}


	/********** コンストラクタ **********/

	public WeatherData(int pointID, String cityName, float currentTemperature, float minTemprerature,
			float maxTemprerature, int humidity, long time, String iconID){
		this.pointID = pointID;
		this.cityName = cityName;
		this.currentTemperature = currentTemperature;
		this.minTemprerature = minTemprerature;
		this.maxTemprerature = maxTemprerature;
		this.humidity = humidity;
		this.time = time;
		this.iconID = iconID;
	}


	/********** メソッド **********/

	public String getData(){
		return (
						"地点ID: " + pointID +
						"\n点名: " + cityName +
						"\n現在気温: " + currentTemperature +
						"\n最低気温: " + minTemprerature +
						"\n最高気温: " + maxTemprerature +
						"\n現在湿度: " + humidity +
						"\n取得時間: " + time +
						"\nアイコンID: " + iconID
				);
	}

	/********************************/

}
