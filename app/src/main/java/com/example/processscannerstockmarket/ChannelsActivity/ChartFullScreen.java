package com.example.processscannerstockmarket.ChannelsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.processscannerstockmarket.MainActivity;
import com.example.processscannerstockmarket.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class ChartFullScreen extends AppCompatActivity {

    private LineChart chartFullscreen;

    ImageView backBtn , exportBtn;
    private int fillColor = Color.argb(150, 51, 181, 229);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_full_screen);

        chartFullscreen = findViewById(R.id.chartFullscreen);
        setDataForchartFullscreen();


        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChartFullScreen.this, Channel1.class);
                startActivity(intent);
            }
        });

        exportBtn = findViewById(R.id.exportBtn);
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfExportUtil.exportChartToPdf(chartFullscreen, "chart_export", ChartFullScreen.this, new PdfExportUtil.PdfExportListener() {
                    @Override
                    public void onPdfExportProgress(int progress) {
                        // Handle progress updates if needed
                    }

                    @Override
                    public void onPdfExportComplete() {
                        // Handle completion if needed
                    }
                });
            }
        });
    }


    private void setDataForchartFullscreen() {
        chartFullscreen.getDescription().setEnabled(true);
        chartFullscreen.setDrawBorders(false);
        chartFullscreen.setPinchZoom(true);

        Legend l = chartFullscreen.getLegend();
        l.setEnabled(true);

        // Hide X-axis and Y-axis
        XAxis xAxis = chartFullscreen.getXAxis();
        xAxis.setEnabled(true);

        YAxis leftAxis = chartFullscreen.getAxisLeft();
        leftAxis.setEnabled(true);

        YAxis rightAxis = chartFullscreen.getAxisRight();
        rightAxis.setEnabled(true);

        setData(50, 100);
    }

    private void setData(int count, float range) {
        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 400;
            yVals.add(new Entry(i, val));
        }

        ArrayList<Entry> yVals1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 150;
            yVals1.add(new Entry(i, val));
        }

        LineDataSet set1, set2;

        set1 = new LineDataSet(yVals, "Data Set1");
        set1.setColor(Color.RED);
        set1.setDrawCircles(true);
        set1.setDrawFilled(true);
        set1.setFillColor(fillColor);

        set2 = new LineDataSet(yVals1, "Data Set1");
        set2.setColor(Color.RED);
        set2.setDrawCircles(true);
        set2.setDrawFilled(true);
        set2.setFillColor(fillColor);

        LineData data = new LineData(set2, set1);
        data.setDrawValues(true);
        chartFullscreen.setData(data);
    }
}