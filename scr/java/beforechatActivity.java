package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class beforechatActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforchatting);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.enterchatBtn).setOnClickListener(onClickListener);
    }

    public void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.enterchatBtn:
                    enterchat();
                    break;
            }
        }
    };

    private void enterchat() {

        String nickname = ((EditText) findViewById(R.id.enterchatnicknameEditText)).getText().toString();
        String phone_num = ((EditText) findViewById(R.id.enterchatphonenumEditText)).getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message_user").child(nickname).child(phone_num);

        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("nickname", nickname);
        intent.putExtra("phone num", phone_num);

        startActivity(intent);

    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

