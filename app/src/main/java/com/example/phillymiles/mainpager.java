package com.example.phillymiles;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.Random;

import static android.content.Context.VIBRATOR_SERVICE;
import static androidx.core.graphics.ColorUtils.blendARGB;

public class mainpager extends Fragment {
    long DURATION = 500;
    private boolean on_attach = true;
    FirebaseRecyclerAdapter<card,mainpager.Postviewholder>  firebaseRecyclerAdapter;
    View mview;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences pref;
    public TextView tvtitle;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final  DatabaseReference myRef = database.getReference("group");
    Vibrator v;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainpager, container, false);
        mview=view;
        pref = view.getContext().getSharedPreferences("ID", Context.MODE_PRIVATE);
         v = (Vibrator) view.getContext().getSystemService(VIBRATOR_SERVICE);
        recyclerView =  view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        mLayoutManager=(new HPLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setLayoutManager(mLayoutManager);
        firstload();


        ImageButton setting=view.findViewById(R.id.imagebutton_setting);
         tvtitle=view.findViewById(R.id.textivew_title);
        Typeface tf = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/Platform-Bold.ttf");

        myRef.child(pref.getString("GroupID",null)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 tvtitle.setText(snapshot.child("groupname").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tvtitle.setTypeface(tf);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //myRef.child(pref.getString("GroupID",null)).child("members").child(pref.getString("UserID",null)).child("color").setValue(randomColor);
                Intent intent=new Intent(view.getContext(),settings.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void firstload(){
        // Toast.makeText(getActivity(),"poll",Toast.LENGTH_SHORT).show();


        final Query query = myRef.child(pref.getString("GroupID",null)).child("members").orderByChild("steps").limitToFirst(30);
        query.keepSynced(true);

        FirebaseRecyclerOptions<card> options =
                new FirebaseRecyclerOptions.Builder<card>()
                        .setQuery(query, card.class)
                        .build();


        firebaseRecyclerAdapter  = new FirebaseRecyclerAdapter<card, mainpager.Postviewholder>(options){
            @Override
            protected void onBindViewHolder(@NonNull Postviewholder postviewholder, final int i, @NonNull card card) {
                final String postKey=getRef(0).getKey();

                postviewholder.setUsername(card.getUsername());
                postviewholder.setSteps(card.getSteps());
                postviewholder.setcolor(card.getColor());

                postviewholder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Objects.equals(pref.getString("UserID", null), postKey)){
                            Random rand = new Random();
                            int r = rand.nextInt(255);
                            int g = rand.nextInt(255);
                            int b = rand.nextInt(255);
                            final int randomColor = Color.rgb(r,g,b);

                            myRef.child(pref.getString("GroupID",null)).child("members").child(getRef(i).getKey()).child("color").setValue(randomColor);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(200);
                            }

                        }
                    }
                });


            }


            @NonNull
            @Override
            public Postviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
                mainpager.Postviewholder viewHolder = new mainpager.Postviewholder(rowView);

                // setAnimation(viewHolder.itemView, viewType);

                return viewHolder;
            }



        };

        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.setAdapter(firebaseRecyclerAdapter);

                firebaseRecyclerAdapter.notifyDataSetChanged();
            }
        });





    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public static class Postviewholder extends RecyclerView.ViewHolder {

        View mView;
        ImageView upvote;
        ImageView imageicon;
        ImageView post_Image;
        private DatabaseReference mDatabaseUpvote;
        ImageButton imageButton;
        ImageButton imageButton2;
        TextView upvotecounter;
        TextView eyecount;
        CardView cardView;

        public Postviewholder(View itemView) {
            super(itemView);
            mView = itemView;
            cardView=mView.findViewById(R.id.card);
            //imageButton=mView.findViewById(R.id.imageButtondelete);
            //imageButton2=mView.findViewById(R.id.imageButton_views);
            // imageicon =  mView.findViewById(R.id.Post_imiageicon);
            //post_Image =  mView.findViewById(R.id.CardImage);
            // upvotecounter=mView.findViewById(R.id.upvote_counter);
            //   eyecount=mView.findViewById(R.id.textView_eyescounter);
            //   upvote =  mView.findViewById(R.id.upvotec);

        }

        public void setTitle(Context ctx, String title, String texttype, String story) {
            // if (story != null && mView.findViewById(R.id.cardPreview)!=null){
            // TextView preview=mView.findViewById(R.id.cardPreview);
        }
        public void setUsername(String username) {
            Typeface tf = Typeface.createFromAsset(mView.getContext().getAssets(), "fonts/Platform-Bold.ttf");
            TextView post_username = mView.findViewById(R.id.name);
            TextView a = mView.findViewById(R.id.textview_letter);
            post_username.setText(username);
            a.setText(String.valueOf(username.charAt(0)).toUpperCase());
            a.setTypeface(tf);
            post_username.setTypeface(tf);
        }
        /*public   void setimage_icon(final Context ctx, final String imagedicon) {
            Glide.with(mView.getContext())
                    .load(imagedicon)
                    .into(imageicon);

        }*/

        public void setSteps(int steps) {
            Typeface tf = Typeface.createFromAsset(mView.getContext().getAssets(), "fonts/Platform-Bold.ttf");
            TextView s = mView.findViewById(R.id.textview_steps);
            s.setText(String.valueOf(steps*-1));
            s.setTypeface(tf);

        }

        public void setcolor(int color) {
            CardView circle_card=mView.findViewById(R.id.cardview_color);
            CardView card =mView.findViewById(R.id.card);
            TextView textView=mView.findViewById(R.id.textview_letter);
            TextView post_username = mView.findViewById(R.id.name);
            CardView cardView_step = mView.findViewById(R.id.cardview_step);

            card.setCardBackgroundColor(color);
            textView.setTextColor(color);
            cardView_step.setCardBackgroundColor(blendARGB(color,getContrastColor(color),0.2f));
            circle_card.setCardBackgroundColor(blendARGB(color,getContrastColor(color),0.2f));
            post_username.setTextColor(blendARGB(color,getContrastColor(color),0.5f));

            /*if (color==0){
                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                int randomColor = Color.rgb(r,g,b);
                card.setCardBackgroundColor(randomColor);
                circle_card.setCardBackgroundColor(blendARGB(randomColor,getContrastColor(randomColor),0.2f));
                cardView_step.setCardBackgroundColor(blendARGB(randomColor,getContrastColor(randomColor),0.2f));
                textView.setTextColor(randomColor);
                SharedPreferences pref = mView.getContext().getSharedPreferences("ID", Context.MODE_PRIVATE);
                Toast.makeText(mView.getContext(),pref.getString("GroupID",null),Toast.LENGTH_SHORT).show();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("group").child(pref.getString("GroupID",null)).child("members").child(pref.getString("UserID",null)).child("color").setValue(randomColor);

            }else{
            }*/
        }
    }

    private void setAnimation(View itemView, int i) {
        if(!on_attach){
            i = -1;
        }
        boolean isNotFirstItem = i == -1;
        i++;
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(itemView, "alpha", 0.f, 0.5f, 1.0f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animator.setStartDelay(isNotFirstItem ? DURATION / 2 : (i * DURATION / 3));
        animator.setDuration(500);
        animatorSet.play(animator);
        animator.start();
    }

    @ColorInt
    public static int getContrastColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return a < 0.5 ? Color.BLACK : Color.WHITE;
    }

    private static  String IntegerShortner(int integer){
        if (integer>999 && integer<10000){
            return  Float.valueOf((float) integer /1000).toString().substring(0,3)+"K";
        }else  if (integer>9999 && integer<100000){
            return (Float.valueOf((float) integer /1000).toString().substring(0,4)+"K");
        }else if (integer>99999 && integer<1000000){
            return (Float.valueOf((float) integer /1000).toString().substring(0,5)+"K");
        }else if (integer>999999 && integer<10000000 || integer>100000000){
            return (Float.valueOf((float) integer /1000000).toString().substring(0,3)+"M");
        }else if (integer>9999999 && integer<100000000){
            return (Float.valueOf((float) integer /1000000).toString().substring(0,4)+"M");
        }else{
            return String.valueOf(integer);
        }
    }
}
