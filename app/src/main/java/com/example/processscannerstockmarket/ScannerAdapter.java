package com.example.processscannerstockmarket;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ScannerAdapter extends BaseAdapter {
    private List<ScannerData> scannerDataList;
    private Context context;

    public ScannerAdapter(Context context, List<ScannerData> scannerDataList) {
        this.context = context;
        this.scannerDataList = scannerDataList;
    }

    @Override
    public int getCount() {
        return scannerDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return scannerDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.scanner_items, parent, false);

            holder = new ViewHolder();
            holder.scannerTypeTextView = convertView.findViewById(R.id.scannerType);
            holder.channelTextView = convertView.findViewById(R.id.channel1);
            holder.processValueTextView = convertView.findViewById(R.id.processValue);
            holder.chart = convertView.findViewById(R.id.chart1);
            holder.alertImageView = convertView.findViewById(R.id.alertImageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ScannerData scannerData = scannerDataList.get(position);

        holder.scannerTypeTextView.setText(scannerData.getScannerType());
        holder.channelTextView.setText(scannerData.getChannel());
        holder.processValueTextView.setText(scannerData.getProcessValue());

        configureLineChart(holder.chart);
        setData(holder.chart, 10, 100, getLineColor(position), getFillColor(position));

        // Conditionally show the alert image for specific positions
        if (position == 2) {
            holder.alertImageView.setVisibility(View.VISIBLE);
            blinkView(holder.alertImageView, 1000); // Blink every 500 milliseconds
        } else if (position == 3) {
            holder.alertImageView.setVisibility(View.VISIBLE);
            blinkView(holder.alertImageView, 2000); // Blink every 1000 milliseconds
        } else {
            holder.alertImageView.setVisibility(View.GONE);
        }


        return convertView;
    }
    private void blinkView(final View view, final int blinkInterval) {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view.getVisibility() == View.VISIBLE) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }

                handler.postDelayed(this, blinkInterval);
            }
        }, blinkInterval);
    }
    private static class ViewHolder {
        TextView scannerTypeTextView;
        TextView channelTextView;
        TextView processValueTextView;
        LineChart chart;
        ImageView alertImageView;
    }

    private void setData(LineChart lineChart, int count, float range, int lineColor, int fillColor) {
        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 100;
            yVals.add(new Entry(i, val));
        }

        LineDataSet dataSet = new LineDataSet(yVals, "Data Set");
        dataSet.setColor(lineColor);
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(fillColor);
        dataSet.setValueTextSize(0f);

        LineData data = new LineData(dataSet);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    private void configureLineChart(LineChart lineChart) {
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setViewPortOffsets(0f, 0f, 0f, 0f);
        lineChart.setGridBackgroundColor(Color.TRANSPARENT);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(false);

    }

    private int getLineColor(int index) {
        switch (index) {
            case 0:
                return ContextCompat.getColor(context,R.color.blue);
            case 1:
                return ContextCompat.getColor(context,R.color.red);
            case 2:
                return ContextCompat.getColor(context,R.color.yellow);
            case 3:
                return ContextCompat.getColor(context,R.color.blue);
            case 4:
                return ContextCompat.getColor(context,R.color.purple);
            case 5:
                return ContextCompat.getColor(context,R.color.red);
            case 6:
                return ContextCompat.getColor(context,R.color.yellow);
            case 7:
                return ContextCompat.getColor(context,R.color.purple);

            default:
                return ContextCompat.getColor(context,R.color.black);
        }
    }

    private int getFillColor(int index) {
        switch (index) {
            case 0:
                return ContextCompat.getColor(context,R.color.blue);
            case 1:
                return ContextCompat.getColor(context,R.color.red);
            case 2:
                return ContextCompat.getColor(context,R.color.yellow);
            case 3:
                return ContextCompat.getColor(context,R.color.blue);
            case 4:
                return ContextCompat.getColor(context,R.color.purple);
            case 5:
                return ContextCompat.getColor(context,R.color.red);
            case 6:
                return ContextCompat.getColor(context,R.color.yellow);
            case 7:
                return ContextCompat.getColor(context,R.color.purple);
            default:
                return ContextCompat.getColor(context,R.color.black);
        }
    }
}