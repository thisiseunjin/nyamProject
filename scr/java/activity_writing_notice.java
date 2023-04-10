package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class activity_writing_notice extends AppCompatActivity {

    EditText pname,pcontents;
    Button button1;
    int Cuser_id,Cuser_authority;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), notice_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_notice);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        pname=(EditText) findViewById(R.id.nname);
        pcontents=(EditText)findViewById(R.id.ncontents);


            button1 = (Button) findViewById(R.id.button7) ;
            button1.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String pnameS=pname.getText().toString();
                    String pcontentsS=pcontents.getText().toString();

                     if(pnameS.equals("")){
                        Toast.makeText(activity_writing_notice.this,"제목을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    }

                    else if(pcontentsS.equals(""))   {
                        Toast.makeText(activity_writing_notice.this,"내용을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        DatabaseReference Database = FirebaseDatabase.getInstance().getReference();
                        GlobalVariable bk = (GlobalVariable) getApplication();

                        int post_num= bk.getboardkey();
                        bk.setboardkey(post_num+1);

                        HashMap<String,Object> newpost=new HashMap();

                        Notice_board j=new Notice_board(post_num,pnameS,pcontentsS,Cuser_id);

                        Map<String,Object> userValue=j.toMap();

                        newpost.put("/NoticeBoard/"+post_num,userValue);
                        Database.updateChildren(newpost);

                        Intent iiiintent= new Intent(getApplicationContext(), notice_show_list.class);
                        iiiintent.putExtra("Cuser_id",Cuser_id);
                        iiiintent.putExtra("Cuser_authority",Cuser_authority);
                        startActivity(iiiintent);
                    }
                }
            });
        }
    }


