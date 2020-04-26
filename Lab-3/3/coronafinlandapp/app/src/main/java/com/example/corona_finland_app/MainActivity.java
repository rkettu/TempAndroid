package com.example.corona_finland_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String address = "https://w3qa5ydb4l.execute-api.eu-west-1.amazonaws.com/prod/finnishCoronaHospitalData";
    public static final String TAG = "ERROR ERROR ERROR";
    private RequestQueue queue;
    private CalendarView calendarView;
    private ProgressBar progressBar;
    private List<TextView> twList;
    private List<MyCustomCorona> cList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twList = new ArrayList<>();
        twList.add((TextView)findViewById(R.id.line1));
        twList.add((TextView)findViewById(R.id.line2));
        twList.add((TextView)findViewById(R.id.line3));
        twList.add((TextView)findViewById(R.id.line4));
        twList.add((TextView)findViewById(R.id.line5));
        progressBar = findViewById(R.id.progressBar);
        calendarView = findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String sMonth;
                String sDayOfMonth;
                if(month < 10) sMonth = "0" + (month+1);
                else sMonth = "" + (month+1);
                if(dayOfMonth < 10) sDayOfMonth = "0" + dayOfMonth;
                else sDayOfMonth = "" + dayOfMonth;
                Log.d("HEI", year + "/" + sMonth + "/" + sDayOfMonth);
                updateTextViews( year + "/" + sMonth + "/" + sDayOfMonth);
            }
        });

        queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, address, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Got a response
                Log.d("JOTAIN", "jotain");

                new MyParseTask().execute(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error
                Log.d("ERRORILOGI", error.toString());
            }
        });
        queue.add(jsonReq);
    }

    private class MyParseTask extends AsyncTask<JSONObject, Void, List<MyCustomCorona>>
    {
        @Override
        protected List<MyCustomCorona> doInBackground(JSONObject... jsonObjects) {
            List<MyCustomCorona> list = new ArrayList<>();
            list = MyCustomParser.parse(jsonObjects[0]);

            return list;
        }

        @Override
        protected void onPostExecute(List<MyCustomCorona> myCustomCoronas) {
            Log.d("TAALLA OLLAAN", "Hei");

            cList = myCustomCoronas;

            progressBar.setVisibility(View.GONE);
            calendarView.setVisibility(View.VISIBLE);

            twList.get(0).setText("PICK DATE TO START");
            twList.get(1).setText("Earliest data: " + cList.get(0).getDate());
            twList.get(2).setText("Latest data: " + cList.get(cList.size()-1).getDate());
        }
    }

    private void updateTextViews(String timeString)
    {
        int index = 0;
        clearTextViews();
        for(MyCustomCorona c : cList)
        {
            if(c.getDate().equals(timeString))
            {
                twList.get(index).setText("Area: " + c.getArea() + ", Total Hospitalised: " + c.getTotalHospitalised() +
                        ", In ICU: " + c.getInIcu() + ", Dead: " + c.getDead());
                if(index < twList.size()-1) index++;
            }
        }
        if(index == 0)
        {
            // date invalid
            clearTextViews();
            twList.get(0).setText("No info for selected Date");
            twList.get(1).setText("Earliest data: " + cList.get(0).getDate());
            twList.get(2).setText("Latest data: " + cList.get(cList.size()-1).getDate());
        }
    }

    private void clearTextViews()
    {
        for(TextView tw : twList)
        {
            tw.setText("");
        }
    }
}
