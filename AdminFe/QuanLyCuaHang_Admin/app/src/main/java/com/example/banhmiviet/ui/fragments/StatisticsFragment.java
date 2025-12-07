package com.example.banhmiviet.ui.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.banhmiviet.R;
import com.example.banhmiviet.data.DataRepository;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StatisticsFragment extends Fragment {

    private TextView tvTotalRevenue, tvTotalOrders;
    private Spinner spnTimeFilter;
    private LineChart lineChart;
    private LinearLayout layoutSelectDate;
    private TextView tvSelectedDate;

    private DataRepository repo;
    private final String[] FILTERS = {"Ngày", "Tuần", "Tháng", "Năm"};

    // ngày đang được chọn ở chế độ "Ngày"
    private final Calendar selectedDate = Calendar.getInstance();
    private final SimpleDateFormat dayFormat =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        tvTotalRevenue   = view.findViewById(R.id.tvTotalRevenue);
        tvTotalOrders    = view.findViewById(R.id.tvTotalOrders);
        spnTimeFilter    = view.findViewById(R.id.spnTimeFilter);
        lineChart        = view.findViewById(R.id.lineChartRevenue);
        layoutSelectDate = view.findViewById(R.id.layoutSelectDate);
        tvSelectedDate   = view.findViewById(R.id.tvSelectedDate);

        repo = DataRepository.getInstance();

        bindSummary();
        setupFilter();
        setupDatePicker();

        return view;
    }

    // hiển thị tổng doanh thu + số đơn
    private void bindSummary() {
        double totalRevenue = repo.getTotalRevenue();
        int totalOrders = repo.getTotalOrderCount();

        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        tvTotalRevenue.setText(nf.format((long) totalRevenue) + " đ");
        tvTotalOrders.setText(String.valueOf(totalOrders));
    }

    // Spinner chọn: Ngày / Tuần / Tháng / Năm
    private void setupFilter() {
        // dùng layout item_spinner_filter cho đẹp
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_spinner_filter,
                FILTERS
        );
        adapter.setDropDownViewResource(R.layout.item_spinner_filter);
        spnTimeFilter.setAdapter(adapter);

        spnTimeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // chỉ hiển thị chọn ngày khi chế độ "Ngày"
                if (position == 0) {
                    layoutSelectDate.setVisibility(View.VISIBLE);
                } else {
                    layoutSelectDate.setVisibility(View.GONE);
                }
                loadChart(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnTimeFilter.setSelection(0); // mặc định: Ngày
    }

    // DatePicker cho chế độ "Ngày"
    private void setupDatePicker() {
        // hiển thị ngày ban đầu
        tvSelectedDate.setText(dayFormat.format(selectedDate.getTime()));

        tvSelectedDate.setOnClickListener(v -> {
            int y = selectedDate.get(Calendar.YEAR);
            int m = selectedDate.get(Calendar.MONTH);
            int d = selectedDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    requireContext(),
                    (DatePicker view1, int year, int month, int dayOfMonth) -> {
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, month);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        tvSelectedDate.setText(dayFormat.format(selectedDate.getTime()));

                        // chỉ reload chart nếu đang ở chế độ "Ngày"
                        if (spnTimeFilter.getSelectedItemPosition() == 0) {
                            loadChart(0);
                        }
                    },
                    y, m, d
            );
            dialog.show();
        });
    }

    /**
     * mode:
     * 0 = Ngày  -> 7 ngày kết thúc ở selectedDate
     * 1 = Tuần  -> 4 tuần gần nhất
     * 2 = Tháng -> 12 tháng của năm hiện tại
     * 3 = Năm   -> từ 2015 đến năm hiện tại
     */
    private void loadChart(int mode) {
        double totalRevenue = repo.getTotalRevenue();

        List<Double> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH); // 0-11

        switch (mode) {
            case 0: { // NGÀY - 7 ngày kết thúc tại selectedDate
                Calendar base = (Calendar) selectedDate.clone();
                for (int i = 0; i < 7; i++) {
                    Calendar c = (Calendar) base.clone();
                    c.add(Calendar.DAY_OF_MONTH, i - 6); // i=0 -> -6; i=6 -> 0
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH) + 1;
                    labels.add(day + "/" + month);
                    values.add(0.0);
                }
                // doanh thu rơi vào ngày cuối (ngày được chọn)
                values.set(6, totalRevenue);
                break;
            }

            case 1: // TUẦN - 4 tuần gần nhất
                for (int i = 1; i <= 4; i++) {
                    labels.add("Tuần " + i);
                    values.add(0.0);
                }
                values.set(3, totalRevenue); // tuần hiện tại = tuần 4
                break;

            case 2: // THÁNG - 12 tháng của năm hiện tại
                for (int m = 1; m <= 12; m++) {
                    labels.add("Th " + m);
                    values.add(0.0);
                }
                values.set(currentMonth, totalRevenue); // doanh thu tháng hiện tại
                break;

            case 3: { // NĂM - từ 2015 đến năm hiện tại
                int startYear = 2015;
                for (int y = startYear; y <= currentYear; y++) {
                    labels.add(String.valueOf(y));
                    values.add(0.0);
                }
                values.set(values.size() - 1, totalRevenue); // năm hiện tại
                break;
            }
        }

        applyChart(values, labels);
    }

    // Vẽ chart với MPAndroidChart
    private void applyChart(List<Double> values, List<String> labels) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            entries.add(new Entry(i, values.get(i).floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Doanh thu");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setColor(Color.parseColor("#FF8800"));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setCircleColor(Color.parseColor("#FF8800"));
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(80);
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // trục X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = (int) value;
                if (index >= 0 && index < labels.size()) {
                    return labels.get(index);
                }
                return "";
            }
        });

        // trục Y VNĐ
        YAxis leftAxis = lineChart.getAxisLeft();
        NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return vnFormat.format(Math.round(value));
            }
        });
        leftAxis.setAxisMinimum(0f);
        lineChart.getAxisRight().setEnabled(false);

        Description desc = new Description();
        desc.setText("");
        lineChart.setDescription(desc);
        lineChart.getLegend().setEnabled(false);

        lineChart.invalidate();
    }
}
