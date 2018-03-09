package l10s18bok.naver.com.self5the1_0.Analysis;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.BaseFragment;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;

public class Fragment_PieChart extends Fragment {
    private View rootView;
    public String currentDateString;
    PieChart pieChart;
    Realm realm;
    RealmListener rListener;
    List<String> menuname;
    List<Integer> menuAmount;
    ArrayList<Piedata>  yyValues;
    ArrayList<PieEntry> yValues;
    DateCommon dateCommon = new DateCommon();
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {

        }else {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        pieChart = (PieChart) rootView.findViewById(R.id.piechart);
        goPieChart();
        return rootView;
    }

    public PieData dataLoad() {
        rListener = new RealmListener();
        realm = rListener.realmDefaultConfiguration(100);
        menuname = rListener.getmenunameAmountDB();
        menuAmount = rListener.getMenuAmountDB(currentDateString);
        yyValues = new ArrayList<>();
        yValues = new ArrayList<>();

        for (int i = 0; i < menuname.size(); i++) {
            if(menuAmount.get(i) != 0)
            yyValues.add(new Piedata(menuAmount.get(i), menuname.get(i)));
        }
        Collections.sort(yyValues,sortByMaxCall);

        if (yyValues.size() > 10) {
            for (int j = yyValues.size()-1; j >= yyValues.size() - 11; j--) {
                Piedata s = yyValues.get(j);
                yValues.add(new PieEntry(s.menuamount, s.menuname));
            }


            long etc = 0;
            for (int k = yyValues.size() - 11; k >= 0; k--) {
                Piedata sPlus = yyValues.get(k);
                etc += sPlus.menuamount;
            }
            yyValues.add(new Piedata(etc, "기타"));
            yValues.add(new PieEntry(etc, "기타"));

        } else
            for (int j = yyValues.size()-1; j >= 0; j--) {
                Piedata s = yyValues.get(j);
                yValues.add(new PieEntry(s.menuamount, s.menuname));
            }

        PieDataSet dataSet = new PieDataSet(yValues, "<판매순위>");
        dataSet.setSliceSpace(2);
        //dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
        data.setValueTextSize(10f);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.rgb(100,140,255));
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.DKGRAY);
        colors.add(Color.BLUE);
        colors.add(Color.GRAY);
        colors.add(Color.rgb(10,80,10));
        colors.add(Color.rgb(255,140,100));
        colors.add(Color.rgb(20,24,50));
        colors.add(Color.rgb(200,100,50));
        dataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        data.setValueTextColor(Color.BLACK);
        data.getDataSetCount();
        return data;

    }

    public void goPieChart() {
        currentDateString = dateCommon.getDate();
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10, 10, 10, 5);
        pieChart.setCenterText("상위 TOP:10 메뉴\nPercent(%)");
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setDragDecelerationFrictionCoef(95f);
        pieChart.setRotationEnabled(false);
        pieChart.setBackgroundColor(Color.rgb(255,200,240));
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setData(dataLoad());
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d("H <--- 이거요: ",h.toString());   // 로그 참조
                int pos = h.toString().indexOf("x: ");      // 내가 원하는 "x: " 의 주소값 get
                String pos2 = h.toString().substring(pos +3);   // int pos 주소값 이하 ..String 으로 ..
                String[] arrDate = pos2.trim().split(","); // 원하는 값 추출
                float aFloat = Float.parseFloat(arrDate[0]);    // float 값
                pos = (int)aFloat;                              // float -> int 형변환
                Piedata piedata = null;
                if(pos == 10) {
                    piedata = yyValues.get(yyValues.size()-1);
                }else {
                    piedata = yyValues.get((yyValues.size() -2) - pos);
                }
                Toast.makeText(getContext(), "판매수량 : "+piedata.menuamount, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });



    }

    private final static Comparator<Piedata> sortByMaxCall = new Comparator<Piedata>() {
        @Override
        public int compare(Piedata o1, Piedata o2) {
            return Float.compare(o1.menuamount, o2.menuamount);
        }
    };


    public class Piedata {
        public String menuname;
        public long menuamount;

        public Piedata(long menuamount, String menuname ) {
            this.menuname = menuname;
            this.menuamount = menuamount;
        }
    }
    public class DecimalRemover extends PercentFormatter {

        protected DecimalFormat mFormat;

        public DecimalRemover(DecimalFormat format) {
            this.mFormat = format;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            //if(value < 10) return "";
            return mFormat.format(value) + " %";
        }
    }

}
