package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class writingActivity extends AppCompatActivity {


    Spinner sexspinner;
    Spinner pspinner;

    String selectedSex;
    String selectedPcount;

    EditText pname,pdate,pcontents;
    Button button1;
    int Cuser_id,Cuser_authority;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), join_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_post);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);



        sexspinner = findViewById(R.id.spinner);
        ArrayAdapter sexAdapter = ArrayAdapter.createFromResource(this, R.array.Sex, R.layout.spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexspinner.setSelection(0);
        sexspinner.setAdapter(sexAdapter);

        pspinner = findViewById(R.id.spinner4);
        ArrayAdapter pAdapter = ArrayAdapter.createFromResource(this, R.array.Pcount, R.layout.spinner_item);
        pAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pspinner.setSelection(0);
        pspinner.setAdapter(pAdapter);


        pname=(EditText)findViewById(R.id.jname);
        pdate=(EditText)findViewById(R.id.jdate);
        pcontents=(EditText)findViewById(R.id.jcontent);

        button1 = (Button) findViewById(R.id.button) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedSex = sexspinner.getSelectedItem().toString();
                selectedPcount = pspinner.getSelectedItem().toString();
                String pnameS=pname.getText().toString();
                String pdateS=pdate.getText().toString();
                String pcontentsS=pcontents.getText().toString();


                if(selectedSex.equals(null)){
                    Toast.makeText(writingActivity.this,"성별을 선택하세요",Toast.LENGTH_SHORT).show();
                }
                else if(selectedPcount.equals(null)){
                    Toast.makeText(writingActivity.this,"인원수를 선택해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(pnameS.equals("")){
                    Toast.makeText(writingActivity.this,"제목을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(pdateS.equals("")){
                    Toast.makeText(writingActivity.this,"날짜를 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
                else if(pcontentsS.equals(""))   {
                    Toast.makeText(writingActivity.this,"내용을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference Database = FirebaseDatabase.getInstance().getReference();

                    GlobalVariable bk = (GlobalVariable) getApplication();

                    int post_num= bk.getboardkey();
                    bk.setboardkey(post_num+1);

                    HashMap<String,Object> newpost=new HashMap();

                    Join_board j=new Join_board(post_num,selectedPcount,selectedSex,pdateS,pnameS,pcontentsS,Cuser_id);
                    Map<String,Object> userValue=j.toMap();

                    newpost.put("/JoinBoard/"+post_num,userValue);
                    Database.updateChildren(newpost);

                    Intent iiintent= new Intent(getApplicationContext(), join_show_list.class);

                    iiintent.putExtra("Cuser_id",Cuser_id);
                    iiintent.putExtra("Cuser_authority",Cuser_authority);

                    startActivity(iiintent);
                }
            }
        });
    }
}
