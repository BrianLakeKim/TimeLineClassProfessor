package com.example.timelineclassprofessor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    YouTubePlayerView youTubePlayerView;


    EditText commentEdit;
    TextView timeText;
    ImageButton timeBtn;
    Button commentBtn;
    TextView commentTimeTv;


    ArrayList<Comment> commentList;
    RecyclerView commentRecyclerView;
    LinearLayoutManager layoutManager;
    CommentAdapter commentAdapter;
    String videoId;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    String time;
    String timeMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        timeText = findViewById(R.id.timeText);
        timeBtn = findViewById(R.id.timeBtn);
        commentBtn = findViewById(R.id.commentBtn);
        commentEdit = findViewById(R.id.commentEdit);

        commentTimeTv = findViewById(R.id.videoTime);

        videoId = "JGgvS42r48M";

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {

            @Override
            public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float second) {
                time = second + "";
            }

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);
                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer,
                        getLifecycle(),
                        videoId,
                        0
                );
            }

        };

        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);


        YouTubePlayerTracker tracker = new YouTubePlayerTracker();
        youTubePlayerView.addYouTubePlayerListener(tracker);



        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        commentRecyclerView.setLayoutManager(layoutManager);
        commentList = new ArrayList<Comment>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Comment");

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeText.setText(time);
                timeMarker = timeText.getText().toString();
            }
        });

        commentAdapter = new CommentAdapter(commentList, this);
        commentRecyclerView.setAdapter(commentAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment comment = snapshot.getValue(Comment.class);
                commentAdapter.addItem(comment);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = User.name;
                String content = commentEdit.getText().toString();
                Comment comment = new Comment(name, timeMarker, content);

                FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                DatabaseReference commentRef = firebase.getReference("Comment");
                commentRef.push().setValue(comment);
                commentEdit.setText("");

                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            }
        });
        commentAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String data) {
                float moveTime = Float.parseFloat(data);
                System.out.println(moveTime);
                youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                    youTubePlayer.seekTo(moveTime);
                });
                System.out.println(tracker.getVideoId());
            }
        });
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            youTubePlayerView.enterFullScreen();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            youTubePlayerView.enterFullScreen();
        }
    }
}