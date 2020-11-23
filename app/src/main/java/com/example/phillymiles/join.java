package com.example.phillymiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.Random;

public class join extends Activity {
    Boolean NewUser =true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("group");
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView imageView=findViewById(R.id.blur);
        byte[] bitma=getIntent().getByteArrayExtra("bit");
        Bitmap bitmap= BitmapFactory.decodeByteArray(bitma,0, bitma != null ? bitma.length : 0);
        imageView.setImageBitmap(bitmap);
        final EditText username= findViewById(R.id.editText_username);
        final EditText groupid = findViewById(R.id.editText_gid);
        final EditText userid=findViewById(R.id.editText_userID);
        CardView cardView = findViewById(R.id.card_join);


        RelativeLayout register=findViewById(R.id.relativelayout_register);
        RelativeLayout login =findViewById(R.id.relativelayout_login);
        TextView textView_register=findViewById(R.id.register);
        TextView textView_login=findViewById(R.id.iphonelogin);
        final ImageView imageView_register=findViewById(R.id.imageview_register);
        final ImageView imageView_login=findViewById(R.id.iphoneregister);

        imageView_login.setVisibility(View.GONE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView_login.setVisibility(View.GONE);
                imageView_register.setVisibility(View.VISIBLE);
                NewUser=true;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView_register.setVisibility(View.GONE);
                imageView_login.setVisibility(View.VISIBLE);
                NewUser = false;
            }
        });

        CardView back = findViewById(R.id.card_cancel2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Platform-Bold.ttf");
        textView_login.setTypeface(tf);
        textView_register.setTypeface(tf);

        final TextView m1 =findViewById(R.id.m22);
        final TextView m2 =findViewById(R.id.m33);
        final TextView m3=findViewById(R.id.m44);
        m1.setVisibility(View.GONE);
        m3.setVisibility(View.GONE);
        m2.setVisibility(View.GONE);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m1.setVisibility(View.GONE);
            }
        });

        groupid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m2.setVisibility(View.GONE);
            }
        });

        userid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m3.setVisibility(View.GONE);
            }
        });

        groupid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        TextView head=findViewById(R.id.textview_popup);

        head.setTypeface(tf);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user= username.getText().toString().trim();
                final String GID=groupid.getText().toString().trim();
                final String UserID=userid.getText().toString().trim();
                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                final int randomColor = Color.rgb(r,g,b);
                if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(GID) && !TextUtils.isEmpty(UserID)){
                    m2.setVisibility(View.GONE);
                    final Progressing alert=new Progressing(join.this);
                    alert.show();
                    alert.textView1.setText("Joining...");
                    alert.textView2.setText("");

                    myRef.child(GID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                myRef.child(GID).child("members").child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists() && !NewUser){
                                            if ((Objects.equals(snapshot.child("username").getValue(String.class), user)) && (Objects.equals(snapshot.child("userid").getValue(String.class), UserID))){
                                                SharedPreferences pref = getApplicationContext().getSharedPreferences("ID", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("GroupID", GID);
                                                editor.putString("UserID",UserID);
                                                editor.apply();
                                                Intent intent=new Intent(join.this,main.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("GroupID",GID);
                                                intent.putExtra("UserID",UserID);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                                alert.dismiss();
                                                finish();
                                            }else{
                                                alert.dismiss();
                                            }
                                        } else if (snapshot.exists() && NewUser){
                                            m3.setVisibility(View.VISIBLE);
                                            m3.setText("Already taken");
                                            alert.dismiss();
                                        } else if (!snapshot.exists() && !NewUser){
                                            m3.setVisibility(View.VISIBLE);
                                            m1.setVisibility(View.VISIBLE);
                                            m3.setText("ID may not exist");
                                            m1.setText("Username may not exist");
                                            alert.dismiss();

                                        } else if (!snapshot.exists() && NewUser){
                                            DatabaseReference databaseReference = myRef.child(GID).child("members").child(UserID);
                                            databaseReference.child("username").setValue(user);
                                            databaseReference.child("userid").setValue(UserID);
                                            databaseReference.child("color").setValue(randomColor);
                                            databaseReference.child("steps").setValue(0);
                                            databaseReference.child("group").setValue(GID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    alert.dismiss();
                                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("ID", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = pref.edit();
                                                    editor.putString("GroupID", GID);
                                                    editor.putString("UserID",UserID);
                                                    editor.apply();
                                                    Intent intent=new Intent(join.this,main.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.putExtra("key",UserID);
                                                    intent.putExtra("username",user);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                                    finish();
                                                }
                                            });


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                            }else {
                                m2.setVisibility(View.VISIBLE);
                                m2.setText("ID doesn't exist");
                                alert.dismiss();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else{
                    if (TextUtils.isEmpty(user)){
                        m1.setVisibility(View.VISIBLE);
                        m1.setText("Missing Field");
                    }
                    if (TextUtils.isEmpty(GID)){
                        m2.setVisibility(View.VISIBLE);
                        m2.setText("Missing Field");

                    }
                    if (TextUtils.isEmpty(UserID)){
                        m3.setVisibility(View.VISIBLE);
                        m3.setText("Missing Field");

                    }
                }
            }
        });

    }
}
