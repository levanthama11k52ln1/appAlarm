import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private boolean multipleChoice = false; //TODO
    private Context context;
    private List<Music> listMusic;
    private List<Music> listCurrentCheckedMusic;
    private Music currentMusic;
    private Music nowPlayingMusic;
    private MediaPlayer mediaPlayer;
    private CheckBox checkBoxPreviousChecked;
    private CheckBox checkBoxNowChecked;
    private ImageButton buttonPreviousPlay;
    private ImageButton buttonNextPlay;
    public MusicAdapter(Context context, Music currentMusic, List<Music> listMusic){
        this.context = context;
        this.currentMusic = currentMusic;
        this.listMusic = listMusic;
        this.mediaPlayer = new MediaPlayer();
    }
    public void stopMusic(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.release();
        }
    }

    public Music getCurrentMusic() {
        return currentMusic;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.item_music, viewGroup, false);
        return new MusicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicViewHolder musicViewHolder, int i) {
        final Music music = listMusic.get(i);
        if(currentMusic.getName().equals(music.getName())){
            musicViewHolder.checkBoxCheck.setChecked(true);
            checkBoxPreviousChecked = musicViewHolder.checkBoxCheck;
            checkBoxNowChecked = checkBoxPreviousChecked;
        }
        else{
            musicViewHolder.checkBoxCheck.setChecked(false);
        }
        musicViewHolder.textViewMusicName.setText(music.getName());
        musicViewHolder.textViewMusicUrl.setText(music.getUrl());

        if(music == nowPlayingMusic){
            musicViewHolder.buttonPlayMusic.setImageDrawable(context.getDrawable(R.drawable.ic_pause));
            buttonPreviousPlay = musicViewHolder.buttonPlayMusic;
        }
        else{
            musicViewHolder.buttonPlayMusic.setImageDrawable(context.getDrawable(R.drawable.ic_play));
        }
        musicViewHolder.checkBoxCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicViewHolder.checkBoxCheck.isChecked()){
                    if(!multipleChoice){
                        currentMusic = music;
                        if(checkBoxPreviousChecked != null){
                            checkBoxPreviousChecked.setChecked(false);
                        }
                        checkBoxNowChecked = musicViewHolder.checkBoxCheck;
                        checkBoxNowChecked.setChecked(true);
                        checkBoxPreviousChecked = checkBoxNowChecked;
                        currentMusic.setName(music.getName());
                    }
                }
            }
        });
        musicViewHolder.buttonPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNextPlay = (ImageButton)v;
                if(!mediaPlayer.isPlaying()){
                    nowPlayingMusic = music;
                    buttonNextPlay.setImageDrawable(context.getDrawable(R.drawable.ic_pause));
                    if(buttonPreviousPlay != null){
                        if(buttonPreviousPlay != buttonNextPlay){
                            buttonPreviousPlay.setImageDrawable(context.getDrawable(R.drawable.ic_play));
                        }
                    }
                    mediaPlayer = MediaPlayer.create(context, Uri.fromFile(new File(music.getUrl())));
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    buttonPreviousPlay = buttonNextPlay;
                }
                else{
                    nowPlayingMusic = null;
                    buttonPreviousPlay.setImageDrawable(context.getDrawable(R.drawable.ic_play));
                    if(buttonPreviousPlay == buttonNextPlay){
                        mediaPlayer.stop();
                        nowPlayingMusic = null;
                    }
                    else{
                        mediaPlayer.stop();
                        buttonNextPlay.setImageDrawable(context.getDrawable(R.drawable.ic_pause));
                        mediaPlayer = MediaPlayer.create(context, Uri.fromFile(new File(music.getUrl())));
                        mediaPlayer.setLooping(true);
                        mediaPlayer.start();
                        nowPlayingMusic = music;
                    }
                    buttonPreviousPlay = buttonNextPlay;
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return listMusic.size();
    }
    class MusicViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBoxCheck;
        private TextView textViewMusicName;
        private TextView textViewMusicUrl;
        private ImageButton buttonPlayMusic;
        MusicViewHolder(View itemView){
            super(itemView);
            checkBoxCheck = itemView.findViewById(R.id.check_box_check);
            textViewMusicName = itemView.findViewById(R.id.text_view_music_name);
            textViewMusicUrl = itemView.findViewById(R.id.text_view_music_url);
            buttonPlayMusic = itemView.findViewById(R.id.button_play_music);
        }
    }
}