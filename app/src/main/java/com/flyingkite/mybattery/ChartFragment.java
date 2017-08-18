package com.flyingkite.mybattery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.flyingkite.util.Say;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends BaseFragment {
    private LineChart chart;
    private TextView chartX;
    private TextView chartY;

    @Override
    protected int getPageLayoutId() {
        return R.layout.page_chart;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        chart = (LineChart) findViewById(R.id.chart);
        chartX = (TextView) findViewById(R.id.chartX);
        chartY = (TextView) findViewById(R.id.chartY);


        // Prepare data
        List<Entry> entry = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            entry.add(new Entry(i, i));
        }

        // Put data to dataset
        LineDataSet dataSet = new LineDataSet(entry, "L1 : y = x");
        dataSet.setColor(Color.CYAN);
        dataSet.setValueTextColor(Color.MAGENTA);
        dataSet.setFillColor(Color.RED);
        dataSet.setCircleColor(Color.GRAY);
        dataSet.setHighLightColor(Color.BLUE);

        LineData data = new LineData(dataSet);

        chart.setData(data);
        chart.setDescription(null);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Say.LogF("onValueSelected e = %s # h = %s", e, h);
                updateXY(h);
            }

            @Override
            public void onNothingSelected() {
                Say.LogF("onNothingSelected");
                updateXY(null);
            }
        });
        //chart.setAutoScaleMinMaxEnabled(true);// bad UX
        chart.invalidate();


        findViewById(R.id.values).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Say.Log("fit screen");
                //chart.resetZoom();
                chart.fitScreen();
                chart.animateXY(200, 200);
                //chart.resetViewPortOffsets();
                //chart.invalidate();
            }
        });
    }

    private void updateXY(Highlight h) {
        String x = h == null ? "--" : String.valueOf(h.getX());
        String y = h == null ? "--" : String.valueOf(h.getY());
        chartX.setText("X = " + x);
        chartY.setText("Y = " + y);
    }
}
