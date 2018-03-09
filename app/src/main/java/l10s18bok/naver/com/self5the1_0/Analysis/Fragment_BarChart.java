package l10s18bok.naver.com.self5the1_0.Analysis;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;
import l10s18bok.naver.com.self5the1_0.realm.TimeSalseCounter;


public class Fragment_BarChart extends Fragment {
    private View rootView;
    private BarData barData;
    private HorizontalBarChart barChart;
    private String currentDateString;
    Realm realm;
    RealmListener rListener;
    ArrayList<TimeSalseCounter> orizen_timetable;
    DateCommon dateCommon = new DateCommon();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        } else {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bar_chart, container, false);
        barChart = (HorizontalBarChart) rootView.findViewById(R.id.barchart);
        goBarChart();
        return rootView;
    }

    private void goBarChart() {
        currentDateString = dateCommon.getDate();
        if(currentDateString.equals("All")){
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            currentDateString = dataFormat.format(date);
        }
        rListener = new RealmListener();
        realm = rListener.realmDefaultConfiguration(100);
        orizen_timetable = rListener.getSalesMonth(currentDateString);

        barData = new BarData(getBarDataValues());
        barData.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
        barChart.setData(barData);
        barChart.setBackgroundColor(Color.GRAY);
        barChart.setDrawGridBackground(true);
        barChart.setGridBackgroundColor(Color.DKGRAY);
        barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getXAxisValues()));
        //barChart.getXAxis().setSpaceMin(1);      // X축 한칸띄고 표시
        //barChart.setVisibleXRange(0,10);
        barChart.getAxisRight().setEnabled(true);
        barChart.getAxisLeft().setEnabled(false);


        barChart.setScaleYEnabled(false);

        barChart.getDescription().setText("X축 : 금액, Y축 : Day");
        barChart.getDescription().setTextColor(Color.GREEN);
        barChart.setNoDataText("판매내역이 없습니다");
        barChart.setScaleEnabled(true);
        barChart.setPinchZoom(true);
        barChart.animateY(3000);
        barChart.invalidate();
    }

    private BarDataSet getBarDataValues() {
        ArrayList<BarEntry> entryArrayList = new ArrayList<>();
        for (int i = 0; i < orizen_timetable.size(); i++) {
            BarEntry el = new BarEntry(i,orizen_timetable.get(i).getAmount(),i);
            entryArrayList.add(el);
        }
        BarDataSet barDataSet = new BarDataSet(entryArrayList,"월 판매내역");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setColor(Color.rgb(255,140,100));

        barDataSet.setHighLightColor(Color.RED);
        barDataSet.setValueTextSize(10f);
        barDataSet.setValueTextColor(Color.YELLOW);

        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format("%.0f",value);
            }
        });

        return barDataSet;
    }

    private List<String> getXAxisValues() {
        ArrayList<String> xvalues = new ArrayList<>();
        for (int i = 0; i < orizen_timetable.size(); i++) {
            xvalues.add(orizen_timetable.get(i).getTime()+"일");
        }
        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();
        //yAxis.setEnabled(false);

        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.DKGRAY);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setTextColor(Color.BLACK);
        leftAxis.setTextColor(Color.GRAY);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(true);


        return xvalues;
    }
    public class DecimalRemover extends PercentFormatter {

        protected DecimalFormat mFormat;

        public DecimalRemover(DecimalFormat format) {
            this.mFormat = format;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            //if(value < 10) return "";
            return mFormat.format(value) + "원";
        }
    }

}