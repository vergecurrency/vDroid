package vergecurrency.vergewallet.models.dataproc;

import android.os.health.TimerStat;

import com.google.gson.Gson;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.models.net.layers.ClearnetGateway;
import vergecurrency.vergewallet.structs.GraphInfo;

public class GraphsDataReader {

	private String filter;

	public static GraphInfo readPriceStatistics(int filter) {
		String rawData = null;
		GraphInfo result;
		try {
			String request = String.format("%s%s", Constants.CHART_DATA_ENDPOINT, getUnixTimeframe(filter));
			rawData = new ClearnetGateway().execute(request).get();
			result = new Gson().fromJson(rawData, GraphInfo.class);
		} catch (Exception e) {
			rawData = "error";
			result = null;
		}

		return result;
	}


	public static String getUnixTimeframe(int filter) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		String now = "/" +  System.currentTimeMillis() + "000/";
		switch (filter) {
			//One day
			case 1:
				c.add(Calendar.DAY_OF_YEAR, -1);
				return c.getTimeInMillis() + now;
			//One week
			case 2:
				c.add(Calendar.WEEK_OF_YEAR, -1);
				return c.getTimeInMillis() + now;
			//One month
			case 3:
				c.add(Calendar.MONTH, -1);
				return c.getTimeInMillis() + now;
			//Three months
			case 4:
				c.add(Calendar.MONTH, -3);
				return c.getTimeInMillis()+ now;
			//One year
			case 5:
				c.add(Calendar.YEAR, -1);
				return c.getTimeInMillis() + now;
			//all time
			case 6:
			default:
				return "" ;
		}
	}


}
