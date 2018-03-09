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
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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


public class Fragment_LineChart extends Fragment {
    private View rootView;
    private LineChart lineChart;
    private LineData lineData;
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
        rootView = inflater.inflate(R.layout.fragment_line_chart, container, false);
        lineChart = (LineChart) rootView.findViewById(R.id.linechart);
        goLineChart();
        return rootView;
    }

    private void goLineChart() {
        currentDateString = dateCommon.getDate();
        if(currentDateString.equals("All")){
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            currentDateString = dataFormat.format(date);
        }
        rListener = new RealmListener();
        realm = rListener.realmDefaultConfiguration(100);
        orizen_timetable = rListener.getSalesTime(currentDateString);
        if(orizen_timetable.size() != 0){

            lineData = new LineData(getLineDataValues());
            lineData.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
            lineChart.setData(lineData);
            lineChart.setBackgroundColor(Color.CYAN);
            lineChart.getXAxis().setTextColor(Color.MAGENTA);
            lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getXAxisValues()));
            //lineChart.getXAxis().setSpaceMin(1);      // X축 한칸띄고 표시
            lineChart.getDescription().setText("X축 : 시간, Y축 : 판매수량");
            lineChart.setNoDataText("판매내역이 없습니다");
            lineChart.setScaleEnabled(true);
            lineChart.getAxisRight().setEnabled(true);
            lineChart.setPinchZoom(true);
            lineChart.animateY(3000);
            lineChart.invalidate();
        }
        //else Toast.makeText(getContext(), "표시할 데이타가 없습니다.", Toast.LENGTH_SHORT).show();

    }

    private List<ILineDataSet> getLineDataValues() {
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> entryArrayList = new ArrayList<>();

        for (int i = 0; i < orizen_timetable.size(); i++) {
            Entry el = new Entry(i,orizen_timetable.get(i).getAmount());
            entryArrayList.add(el);
        }
        LineDataSet lineDataSet = new LineDataSet(entryArrayList,"시간대별 판매현황");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(3);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setValueTextSize(14f);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSets.add(lineDataSet);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        return lineDataSets;
    }

    private List<String> getXAxisValues() {
        ArrayList<String> xvalues = new ArrayList<>();
        for (int i = 0; i < orizen_timetable.size(); i++) {
            xvalues.add(orizen_timetable.get(i).getTime()+"시");
        }
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setTextColor(Color.BLACK);

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
            return mFormat.format(value) + "개";
        }
    }

}