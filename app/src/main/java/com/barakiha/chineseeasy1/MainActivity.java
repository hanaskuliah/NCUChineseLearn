package com.barakiha.chineseeasy1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.barakiha.chineseeasy1.Model.CharLesson;
import com.barakiha.chineseeasy1.Util.FlingCardListener;
import com.barakiha.chineseeasy1.Util.SwipeFlingAdapterView;
import com.barakiha.chineseeasy1.Util.XMLPullParserHandler;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;

    //updating status
    private TextView statusSeqChar;
    private int seqNumber;
    private int numOfChars;
    private int numRememberedChar;
    private int numForgottenChar;


//    private ArrayList<Data> al;
    private List<CharLesson> chinesechars = null;
    private SwipeFlingAdapterView flingContainer;
    //to do delay
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            // do what you need to do here after the delay
            Intent intent=new Intent(getApplicationContext(),CharEvaluationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("numOfChars", numOfChars);
            intent.putExtra("numOfRemembered", numRememberedChar);
            getApplicationContext().startActivity(intent);

        }
    };

    public static void removeBackground() {
        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initial set to zero
        seqNumber=0;
        numOfChars=0;
        numForgottenChar=0;
        numRememberedChar=0;
        statusSeqChar=(TextView) findViewById(R.id.status_seq_char);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        //xml read
        Intent mIntent = getIntent();
        int lessonNumber = mIntent.getIntExtra("LessonNumber", 0)+1;
        String fileName="lesson"+lessonNumber+".xml";
        try {
            XMLPullParserHandler parser = new XMLPullParserHandler();
            chinesechars = parser.parse(getAssets().open(fileName));
            numOfChars=chinesechars.size();
        } catch (IOException e) {
            //e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No material found",Toast.LENGTH_SHORT);
            Intent intent = new Intent(this,Main2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("LessonNumber", 1);
            getApplicationContext().startActivity(intent);
        }

        statusSeqChar.setText(seqNumber+" / "+numOfChars);

        myAppAdapter = new MyAppAdapter(chinesechars, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //not remembered yet
                chinesechars.remove(0);
                myAppAdapter.notifyDataSetChanged();
                numForgottenChar++;
                checkAvailableItems();
               // Toast.makeText(getApplicationContext(),"onleftexit",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //already remembered
                chinesechars.remove(0);
                myAppAdapter.notifyDataSetChanged();
                numRememberedChar++;
                checkAvailableItems();
               // Toast.makeText(getApplicationContext(),"onrightexit",Toast.LENGTH_SHORT).show();
            }

            public void checkAvailableItems(){
                seqNumber++;
                statusSeqChar.setText(seqNumber+" / "+numOfChars);
                if(myAppAdapter!=null){
                    if(myAppAdapter.getCount()<1){
                        mHandler.postDelayed(mUpdateTimeTask, 300);
                    }
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
               // Toast.makeText(getApplicationContext(),myAppAdapter.getCount()+" about to empty "+itemsInAdapter,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                if(view!=null){
                    view.findViewById(R.id.background).setAlpha(0);
                    view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);

                }
        }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView clue;
        public TextView useExample;
        public TextView characterChinese;
        public TextView pinyinTone;
        public TextView meaning;
        public TextView clueText;
        public TextView useExampletext;
        public  FrameLayout mainFrame;
    }

    public class MyAppAdapter extends BaseAdapter {


        public List<CharLesson> parkingList;
        public Context context;

        Typeface chineseTypeface = Typeface.createFromAsset(getAssets(), "chinese.ttf");


        private MyAppAdapter(List<CharLesson> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.clue = (TextView) rowView.findViewById(R.id.clue);
                viewHolder.useExample = (TextView) rowView.findViewById(R.id.use_example);
                viewHolder.clueText = (TextView) rowView.findViewById(R.id.kata_clue);
                viewHolder.useExampletext = (TextView) rowView.findViewById(R.id.kata_example);
                viewHolder.pinyinTone = (TextView) rowView.findViewById(R.id.pinyin_tone);
                viewHolder.meaning = (TextView) rowView.findViewById(R.id.meaning);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.characterChinese = (TextView) rowView.findViewById(R.id.chinese_char);
                viewHolder.mainFrame=(FrameLayout)rowView.findViewById(R.id.main_frame);
                viewHolder.characterChinese.setTypeface(chineseTypeface);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.clue.setText((parkingList.get(position). getClue()+ ""));

            //Glide.with(MainActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            switch (parkingList.get(position).getTone()){
                case 1:
                    viewHolder.mainFrame.setBackgroundColor(getResources().getColor(R.color.tone1));
                    break;
                case 2:
                    viewHolder.mainFrame.setBackgroundColor(getResources().getColor(R.color.tone2));
                    break;
                case 3:
                    viewHolder.mainFrame.setBackgroundColor(getResources().getColor(R.color.tone3));
                    break;
                case 4:
                    viewHolder.mainFrame.setBackgroundColor(getResources().getColor(R.color.tone4));
                    break;
                case 5:
                    viewHolder.mainFrame.setBackgroundColor(getResources().getColor(R.color.tone5));
                    break;

            }
            viewHolder.characterChinese.setText(parkingList.get(position).getCharacter() + "");
            viewHolder.characterChinese.setTypeface(chineseTypeface);
            viewHolder.pinyinTone.setText(parkingList.get(position).getPinyin() + "");
            viewHolder.meaning.setText(parkingList.get(position).getMean() + "");
            viewHolder.useExample.setText(parkingList.get(position).getUse() + "");
            viewHolder.useExample.setTypeface(chineseTypeface);
            viewHolder.clueText.setText("Clue");
            viewHolder.useExampletext.setText("Example");
            return rowView;
        }
    }
}

