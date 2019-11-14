package com.example.classassessment2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.classassessment2.model.User;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    EditText name, dob, phone, email, img;
    RadioGroup radioGroup;
    RadioButton rb_male, rb_female, rb_other;
    Spinner sp_country;
    Button btn_submit,btn_view_user;
    String Name, DOB, Phn, Email, country, gender, Img;
    String[] countries = {"-CHOOSE ONE-", "Nepal", "India", "Srilanka", "Jap" +
            "an", "Korea", "Bhutan", "Pakistan", "Afganistan"};
    final Calendar myCalendar = Calendar.getInstance();
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.Name);
        dob = findViewById(R.id.DoB);
        phone = findViewById(R.id.Phone);
        email = findViewById(R.id.Email);
        rb_male = findViewById(R.id.RBMale);
        rb_female = findViewById(R.id.RBFemale);
        rb_other = findViewById(R.id.RBOther);
        sp_country = findViewById(R.id.Spinner);
        img = findViewById(R.id.img);
        btn_submit = findViewById(R.id.btnSubmit);
        btn_view_user = findViewById(R.id.btnView);
        radioGroup = findViewById(R.id.RG);

        ArrayAdapter adapter= new ArrayAdapter(this, R.layout.spinner_values, countries);
        sp_country.setAdapter(adapter);

        radioGroup.setOnCheckedChangeListener(this);
        setSpinnerValues();
        btn_submit.setOnClickListener(this);
        dob.setOnClickListener(this);
        btn_view_user.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Name = name.getText().toString();
        DOB = dob.getText().toString();
        Phn = phone.getText().toString();
        Email = email.getText().toString();
        Img = img.getText().toString();
        String imgg = "@drawable/" + Img;
        int resID = getResources().getIdentifier(imgg, null, getPackageName());

        if(v.getId() == R.id.btnSubmit){
            if(validate()){
                userList.add(new User(Name, gender, DOB, country, Phn, Email, resID));
                Toast.makeText(this, "ALL OK", Toast.LENGTH_SHORT).show();
            }
        }

        // TODO Auto-generated method stub
        if(v.getId() == R.id.DoB){
            new DatePickerDialog(MainActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }

        if(v.getId() == R.id.btnView){
            Intent intent = new Intent(this, ViewUsers.class);
            intent.putExtra("allusers", (Serializable) userList);
            startActivity(intent);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.RBMale){
            gender = "Male";
        }
        if(checkedId == R.id.RBFemale){
            gender = "Female";
        }
        if(checkedId == R.id.RBOther){
            gender = "Other";
        }
    }

    private void setSpinnerValues(){
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    country = parent.getSelectedItem().toString();
                }else{
                    Toast.makeText(MainActivity.this, "SELECT A COUNTRY", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean validate(){
        if(TextUtils.isEmpty(Name)){
            name.setError("Enter Name");
            name.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(DOB)){
            dob.setError("Enter dob");
            dob.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(gender)){
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (country.equals("-CHOOSE ONE-")) {
            Toast.makeText(this, "Select Country", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(country)) {
            Toast.makeText(this, "Select Country", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(Phn)){
            phone.setError("Enter Number");
            phone.requestFocus();
            return false;
        }
        if(!TextUtils.isDigitsOnly(Phn)){
            phone.setError("Enter Valid number");
            phone.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(Email)){
            email.setError("Enter Email");
            email.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("Invalid Email");
            email.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(Img)){
            email.setError("Provide Image");
            email.requestFocus();
            return false;
        }
        return true;
    }
}
