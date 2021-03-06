package com.example.reproductor.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reproductor.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicLists extends Fragment {


    public MusicLists() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View containerMusicLists = inflater.inflate(R.layout.fragment_music_lists, container, false);





        return containerMusicLists;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View containerMiniPlayer = view.findViewById(R.id.container_mini_player);
        final View btnPause = containerMiniPlayer.findViewById(R.id.btnPause);
      // btnPause.setTransitionName("transitionN_btnPause");

        final View btnNextSong = containerMiniPlayer.findViewById(R.id.btn_nextSong);
        //btnPause.setTransitionName("transitionN_btnNextSong");

        final View imgCurrentSong = containerMiniPlayer.findViewById(R.id.img_song);
        final View txtSongName = containerMiniPlayer.findViewById(R.id.txt_songName);
        final View txtAuthorNAme = containerMiniPlayer.findViewById(R.id.txt_authorName);



        containerMiniPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                        .addSharedElement(btnPause, "transition_btnPause")
                        .addSharedElement(btnNextSong,"transition_btnNext")
                        .addSharedElement(imgCurrentSong,"transition_imgCurrentSong")
                        .addSharedElement(txtSongName,"transition_songName")
                        .addSharedElement(txtAuthorNAme,"transition_authorName")
                        .build();
                Navigation.findNavController(view).navigate(R.id.action_musicLists_to_reproductorScreen,
                        null,
                        /*para hacer el clear al backstack */null,
                        extras
                );
            }
        });
    }
}
