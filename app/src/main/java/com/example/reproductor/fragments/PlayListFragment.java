package com.example.reproductor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reproductor.Models.Song;
import com.example.reproductor.R;
import com.example.reproductor.adapters.recyclers.PlayListAdapter;
import com.example.reproductor.main.CurrentPlayListViewModel;
import com.example.reproductor.main.MainActivity;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 * This kind of fragment will represent any playlist.
 *
 */
public class PlayListFragment extends Fragment implements PlayListAdapter.ViewHolderSong.ClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recycler_songsCurrentlyPlaying;
    PlayListAdapter playListAdapter;
 //   LinearLayoutManager layoutManager =
   //         new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) ;
    private List<Song> songList;
    private CurrentPlayListViewModel currentPlayListViewModel;

    private String tipo_carga;

    public PlayListFragment() {
        // Required empty public constructor

    }




    public PlayListFragment(List<Song> songList) {
        this.songList = songList;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static PlayListFragment newInstance(/*String param1, String param2*/) {
        PlayListFragment fragment = new PlayListFragment();
       /* Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_play_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //final PlayListAdapter.ViewHolderSong.ClickListener clickListenerThis = this;

        currentPlayListViewModel = new ViewModelProvider(requireActivity()).get(CurrentPlayListViewModel.class);
        this.recycler_songsCurrentlyPlaying =  view.findViewById(R.id.recycler_songsCurrentlyPlaying);

        //the list of songs showed will be different according to:
            //Directory
            //Current Play List
            //Or any other, that only will require a list given in the constructor of this class.
        if(getArguments()!=null){
            this.tipo_carga = getArguments().getString(MainActivity.TIPO_CARGA);
            //refactorizar
            if(tipo_carga.equals(MainActivity.DIRECTORY_PLAY_LIST)){
                List<Song> songsDirectoryObserved = currentPlayListViewModel.getDirectoryPlayListCurrentObservedMutableLiveData().getValue();
                this.playListAdapter = new PlayListAdapter(this,songsDirectoryObserved);
                recycler_songsCurrentlyPlaying.setAdapter(playListAdapter);//o anonima
            }else if(tipo_carga.equals(MainActivity.CURRENT_PLAY_LIST)){ //
                List<Song> currentPlayListPlaying = currentPlayListViewModel.getCurrentPlayingSongListMutableLiveData().getValue();
                this.playListAdapter = new PlayListAdapter(this,currentPlayListPlaying);
                this.recycler_songsCurrentlyPlaying.setAdapter(this.playListAdapter);

            }else if(tipo_carga.equals(MainActivity.ANY_PLAY_LIST)){
                this.playListAdapter = new PlayListAdapter(this,songList);
                this.recycler_songsCurrentlyPlaying.setAdapter(playListAdapter);
            }

        }

        recycler_songsCurrentlyPlaying.setItemAnimator(new DefaultItemAnimator());
        //recycler_songsCurrentlyPlaying.setLayoutManager(layoutManager);
        recycler_songsCurrentlyPlaying.setLayoutManager( new LinearLayoutManager(getActivity()));


    }

    @Override
    public void onItemClicked(int positionSong) {
        List<Song> currentPlayingList = currentPlayListViewModel.getCurrentPlayingSongListMutableLiveData().getValue();
        //set the new play list, only if it's different to the current
        if( currentPlayingList != this.playListAdapter.getSongList()){
            currentPlayListViewModel.getCurrentPlayingSongListMutableLiveData().setValue(this.playListAdapter.getSongList());
            currentPlayingList = currentPlayListViewModel.getCurrentPlayingSongListMutableLiveData().getValue();//to get the refresh.
            System.out.println("PlayList changed");
        }

        //If a song is clicked, and that is diferent to the current, then is changed.
        if(currentPlayListViewModel.getCurrentSongMutableLiveData().getValue() != currentPlayingList.get(positionSong) ){
            currentPlayListViewModel.getCurrentSongMutableLiveData().setValue(
                    currentPlayListViewModel.getCurrentPlayingSongListMutableLiveData().getValue().get(positionSong)
            );
            System.out.println("Song changed");
        }

    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }
}