package com.example.wooogi.database0531;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test extends AppCompatActivity {

    Double array[], array2[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        URLConnector urlConnector = new URLConnector(MainActivity.IP + "alldata.php");
        urlConnector.start();
        try {
            urlConnector.join();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String result = urlConnector.getResult();

        try {

            JSONObject root = new JSONObject(result);
            JSONArray jsonArray = root.getJSONArray("result");
            if(jsonArray.length() == 0) {
                Toast.makeText(Test.this, "Empty Server", Toast.LENGTH_SHORT).show();
            }
            array = new Double[jsonArray.length()];
            array2 = new Double[jsonArray.length()];

            for(int i = 0; i < jsonArray.length(); i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                Double temp = jsonObject.getDouble("temp");
                Double humi = jsonObject.getDouble("humi");
                String timestamp = jsonObject.getString("timestamp");
                array[i] = temp;
                array2[i] = humi;
            }
        }

        catch (JSONException e) {
            Toast.makeText(Test.this, "Server Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        GraphView graphView = (GraphView)findViewById(R.id.graphView);
        DataPoint[] dataPoints;
        LineGraphSeries<DataPoint> series;

        dataPoints = new DataPoint[array.length + 1];
        dataPoints[0] = new DataPoint(0, 0);
        for ( int i = 0; i < array.length; i++ ) {
            dataPoints[i + 1] = new DataPoint(i + 1, array[i]);
        }
        series = new LineGraphSeries<DataPoint>(dataPoints);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setMinX(0.0);
        graphView.getViewport().setMinY(0.0);
        graphView.getViewport().setMaxX(100.0);
        graphView.getViewport().setMaxY(8.0);

        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        graphView.addSeries(series);
    }


}
