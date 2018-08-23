package com.example.odeh.benue;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer = null;

    private DBAdapter dbAdapter;

    //Create audio manager instance
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener(){
                public  void onAudioFocusChange(int focusChange){
                    if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaPlayer();
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        mediaPlayer.start();
                    }
                }
            };

    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //create the setup to request audio
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        dbAdapter = new DBAdapter (this);

        //create an array of word
        final ArrayList<Word> words = new ArrayList<>();

        //Create intent holding the selected language
        Intent intent = getIntent();

        words.addAll(dbAdapter.getWords(this,"Numbers", intent.getStringExtra("lang"), "_id"));

        ListView listView = findViewById(R.id.list);

        WordAdapter adapter = new WordAdapter(this, words,R.color.category_numbers);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int audioId = words.get(position).getAudioResourceId();
                if (audioId == 0){
                    Context context = NumbersActivity.this;
                    String message = "No Pronunciation at this time";
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    releaseMediaPlayer();

                    //request audio focus for playback
                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                            //use the music stream
                            AudioManager.STREAM_MUSIC,
                            //request permanent focus
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                        mediaPlayer = MediaPlayer.create(NumbersActivity.this, audioId);
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(mOnCompletionListener);
                    }
                }
            }
        });

    }

    /**Function to release media after using it*/
    private void releaseMediaPlayer(){
        //if media player not equal to null it may be playing a song.
        if (mediaPlayer != null){
            mediaPlayer.release();
        }
        mediaPlayer = null;
        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}
