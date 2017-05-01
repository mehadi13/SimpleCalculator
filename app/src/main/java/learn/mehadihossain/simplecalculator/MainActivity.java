package learn.mehadihossain.simplecalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher{

    EditText editor;
    String number1,number2,operator,editorText,previousText;
    TextView previousTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            editorText = "0";
            previousText = "";
            number1 = null;
        }else {
            editorText = savedInstanceState.getString("editorText","0");
            previousText = savedInstanceState.getString("previousText","");
            number1 = savedInstanceState.getString("number1",null);
            operator = savedInstanceState.getString("operator",null);
           // Toast.makeText(this,"previous Text:"+previousText,Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_main);
        editor = (EditText) findViewById(R.id.editorText);
        editor.addTextChangedListener(this);
        editor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.onTouchEvent(motionEvent);
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null){
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                }
                return true;
            }
        });
        previousTextView = (TextView) findViewById(R.id.previousTextView);
        previousTextView.setText(previousText);
        editor.setFocusable(true);
        editor.setText(editorText);

       /* clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"clear",Toast.LENGTH_SHORT).show();
                editor.setText("0");
                previousTextView.setText("");
                number1 = null;
            }
        });*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("previousText",previousTextView.getText().toString());
        outState.putString("editorText",editor.getText().toString());
        outState.putString("number1",number1);
        outState.putString("operator",operator);
       // Toast.makeText(this,"onsave:"+previousTextView.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    public void clearText(View view) {
       // Toast.makeText(getApplicationContext(),"clear",Toast.LENGTH_SHORT).show();
        editor.setText("0");
        previousTextView.setText("");
        number1 = null;
    }

    public void numberClick(View view){
        if(editor.getText().toString().equals("0")){
            editor.setText(((Button)view).getText());
        }else
            editor.setText(editor.getText().toString()+((Button)view).getText());
    }

    public void operatorClick(View view) {
        if(number1==null){
            number1 = editor.getText().toString();
        }else{
            number2 = editor.getText().toString();
        }

        if(operator!=null && number1!=null && number2!=null){
            doOpetation();
        }
        operator = ((Button)view).getText().toString();
        if(!operator.equals("=")) {
            previousTextView.setText(number1 + " " + operator);
            editor.setText("0");
        }
        else{
            previousTextView.setText("");
            editor.setText(number1);
            number1=null;
        }

    }

    public void doOpetation(){
        try {
            Double num1 = Double.parseDouble(number1);
            Double num2 = Double.parseDouble(number2);
            switch (operator) {
                case "+":
                    number1 = Double.toString(num1+num2);
                    break;
                case "-":
                    number1 = Double.toString(num1-num2);
                    break;
                case "x":
                    number1 = Double.toString(num1*num2);
                    break;
                case "÷":
                    if(num2!=0){
                        number1 = Double.toString(num1/num2);
                    }
                    break;
            }
            number2 = null;
        }catch (Exception e){

        }
    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        //Toast.makeText(this,"Called",Toast.LENGTH_SHORT).show();
        editor.setSelection(editor.getText().length());
    }
}
