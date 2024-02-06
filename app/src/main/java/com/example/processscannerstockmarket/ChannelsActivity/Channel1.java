package com.example.processscannerstockmarket.ChannelsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.processscannerstockmarket.MainActivity;
import com.example.processscannerstockmarket.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Channel1 extends AppCompatActivity {


    ImageView backBtn;
    private LineChart lineChart;
    private TextView timeTextView, dateTextView;
    private Handler handler;

    ImageView expandIcon , exportBtn;


    private int fillColor = Color.argb(150, 51, 181, 229);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel1);

        lineChart = findViewById(R.id.chart1);

        backBtn = findViewById(R.id.backArrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Channel1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        timeTextView = findViewById(R.id.timeTv);
        dateTextView = findViewById(R.id.dateTv);
        handler = new Handler(Looper.getMainLooper());

        // Start updating time every second
        startUpdatingTime();

        setDataForLineChart();

        expandIcon = findViewById(R.id.expandIcon);
        expandIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Channel1.this, ChartFullScreen.class);
                startActivity(intent);
            }
        });


        exportBtn = findViewById(R.id.exportBtn);
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View rootView = findViewById(android.R.id.content);

                PdfExportUtil.exportPageToPdf(
                        rootView,
                        "chart_export_channel1",
                        Channel1.this,
                        new PdfExportUtil.PdfExportListener() {
                            @Override
                            public void onPdfExportProgress(int progress) {
                                // Handle progress updates if needed
                            }

                            @Override
                            public void onPdfExportComplete() {
                                // Handle completion if needed
                            }
                        }

                );
            }
        });
    }
    private void startUpdatingTime() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTime();
                // Repeat the update every second
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        // Display current date
        updateDate();
    }

    private void updateTime() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = timeFormat.format(calendar.getTime());

        // Set the current time to the TextView
        timeTextView.setText(currentTime);
    }

    private void updateDate() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        // Set the current date to the TextView
        dateTextView.setText(currentDate);
    }

    @Override
    protected void onDestroy() {
        // Remove the callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();

    }

    private void setDataForLineChart() {
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawBorders(false);
        lineChart.setPinchZoom(false);

        Legend l = lineChart.getLegend();
        l.setEnabled(true);

        // Hide X-axis and Y-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setEnabled(true);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(true);

        setData(50, 100);
    }

//    private void setData(int count, float range) {
//        ArrayList<Entry> yVals = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) (Math.random() * range) - 200; // Adjust range to include negative values
//            yVals.add(new Entry(i, val));
//        }
//
//        ArrayList<Entry> yVals1 = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            float val = (float) (Math.random() * range) - 200; // Adjust range to include negative values
//            yVals1.add(new Entry(i, val));
//        }
//
//        LineDataSet set1, set2;
//
//        set1 = new LineDataSet(yVals, "Data Set1");
//        set1.setColor(Color.RED);
//        set1.setDrawCircles(true);
//        set1.setDrawFilled(true);
//        set1.setFillColor(fillColor);
//
////        set2 = new LineDataSet(yVals1, "Data Set2");
////        set2.setColor(Color.BLUE); // Change color if needed
////        set2.setDrawCircles(true);
////        set2.setDrawFilled(true);
////        set2.setFillColor(fillColor);
//
//        // Customizing axis minimum to include negative values
//        YAxis leftAxis = lineChart.getAxisLeft();
//        leftAxis.setAxisMinimum(-200); // Adjust as per your requirement
//
////        LineData data = new LineData(set2, set1);
//        LineData data = new LineData( set1);
//        data.setDrawValues(true);
//        lineChart.setData(data);
//    }



    private void setData(int count, float range) {
        ArrayList<Entry> yVals = new ArrayList<>();
        boolean hasNegativeValues = false;

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) - (range / 2); // Include negative values
            yVals.add(new Entry(i, val));

            if (val < 0) {
                hasNegativeValues = true;
            }
        }

        LineDataSet dataSet = new LineDataSet(yVals, "Data Set");

        if (hasNegativeValues) {
            // Adjust the axis minimum to include negative values
            YAxis leftAxis = lineChart.getAxisLeft();
            leftAxis.setAxisMinimum(getMinimumValue(yVals));
        }

        LineData data = new LineData(dataSet);
        data.setDrawValues(true);
        lineChart.setData(data);
    }

    private float getMinimumValue(ArrayList<Entry> entries) {
        float min = Float.MAX_VALUE;
        for (Entry entry : entries) {
            if (entry.getY() < min) {
                min = entry.getY();
            }
        }
        // Add some padding to the minimum value for better visualization
        return min - 10; // Adjust the padding as needed
    }
}