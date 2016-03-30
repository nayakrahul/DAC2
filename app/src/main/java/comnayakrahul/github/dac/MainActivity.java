package comnayakrahul.github.dac;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private EditText n1;
    private EditText n2;
    private TextView field;
    private RadioGroup operatorGroup;
    private String op;
    private Button b;

    public void onClick(View v) {
        n1 = (EditText) findViewById(R.id.number1);
        final double operand1 = Double.parseDouble(n1.getText().toString());
        n2 = (EditText) findViewById(R.id.number2);
        final double operand2 = Double.parseDouble(n2.getText().toString());
        operatorGroup = (RadioGroup) findViewById(R.id.operators);

        int selectedOp = operatorGroup.getCheckedRadioButtonId();
        RadioButton operator = (RadioButton) findViewById(selectedOp);
        op = operator.getText().toString();
        field = (TextView) findViewById(R.id.output);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url =  "http://10.0.2.2:8080/calculator.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        field.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        field.setText("ERROR");
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("op1",String.valueOf(operand1));
                params.put("op2",String.valueOf(operand2));
                params.put("operator", op);
                return params;
            }

        };


        requestQueue.add(stringRequest);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
