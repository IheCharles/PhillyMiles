package com.example.phillymiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Random;

public class create extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("group");
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView imageView=findViewById(R.id.blur);
        byte[] bitma=getIntent().getByteArrayExtra("bit");
        Bitmap bitmap= BitmapFactory.decodeByteArray(bitma,0, bitma != null ? bitma.length : 0);
        imageView.setImageBitmap(bitmap);
        CardView cardView = findViewById(R.id.card_create);
        final Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);



        final TextView m1 =findViewById(R.id.m1);
        final TextView m2 =findViewById(R.id.m2);
        final TextView m3 =findViewById(R.id.m3);
        m1.setVisibility(View.GONE);
        m2.setVisibility(View.GONE);
        m3.setVisibility(View.GONE);
        TextView head=findViewById(R.id.textview_popup);
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Platform-Bold.ttf");
        head.setTypeface(tf);
        final EditText groupname = findViewById(R.id.editText);
        final EditText username= findViewById(R.id.editText2);
        final EditText groupid = findViewById(R.id.editText3);

        CardView back = findViewById(R.id.card_cancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        groupid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2!=8){
                    m3.setText("ID must be 8 characters long");
                    m3.setVisibility(View.VISIBLE);

                }else {
                    m3.setVisibility(View.GONE);
                    FirebaseDatabase.getInstance().getReference().child("group").child(groupid.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                m3.setText("Taken");
                                m3.setVisibility(View.VISIBLE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                                } else {
                                    //deprecated in API 26
                                    v.vibrate(200);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    /*myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if (dataSnapshot.child("groupid").getValue() == groupid.getText().toString().trim()){
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        groupname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m1.setVisibility(View.GONE);
            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m2.setVisibility(View.GONE);
            }
        });

        groupid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m3.setVisibility(View.GONE);
            }
        });


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Boolean[] exist = {false};
                final String GroupName = groupname.getText().toString().trim();
                final String Username = username.getText().toString().trim();
                final String GroupID = groupid.getText().toString().trim();
                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                final int randomColor = Color.rgb(r,g,b);


                if (!TextUtils.isEmpty(GroupID) && !TextUtils.isEmpty(Username) && !TextUtils.isEmpty(GroupName)){

                    m3.setVisibility(View.GONE);
                    final Progressing alert=new Progressing(create.this);
                    alert.show();
                    alert.textView1.setText("Creating...");
                    alert.textView2.setText("");
                    final DatabaseReference databaseReference = myRef.child(GroupID);
                    myRef.child(GroupID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                                databaseReference.child("groupname").setValue(GroupName);
                                databaseReference.child("username").setValue(Username);
                                databaseReference.child("color").setValue(randomColor);
                                databaseReference.child("groupid").setValue(GroupID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        final DatabaseReference databaseReference = myRef.child(GroupID).child("members").child(GroupID);
                                        databaseReference.child("username").setValue(Username);
                                        databaseReference.child("userid").setValue(GroupID);
                                        databaseReference.child("color").setValue(randomColor);
                                        databaseReference.child("steps").setValue(0);
                                        databaseReference.child("group").setValue(GroupID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                SharedPreferences pref = getApplicationContext().getSharedPreferences("ID", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("GroupID", GroupID);
                                                editor.putString("UserID",GroupID);
                                                editor.apply();
                                                alert.dismiss();
                                                Intent intent=new Intent(create.this,main.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("key",databaseReference.getKey());
                                                intent.putExtra("ID",GroupID);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                                finish();
                                            }
                                        });
                                    }
                                });
                            }else{
                                alert.dismiss();
                                m3.setText("Taken");
                                m3.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(200);
                    }
                    if (TextUtils.isEmpty(GroupID)){
                        m3.setVisibility(View.VISIBLE);
                        m3.setText("Missing Field");

                    }
                    if (TextUtils.isEmpty(Username)){
                        m2.setVisibility(View.VISIBLE);
                        m2.setText("Missing Field");
                    }
                    if (TextUtils.isEmpty(GroupName)){
                        m1.setVisibility(View.VISIBLE);

                    }

                }

            }
        });







    }
}
