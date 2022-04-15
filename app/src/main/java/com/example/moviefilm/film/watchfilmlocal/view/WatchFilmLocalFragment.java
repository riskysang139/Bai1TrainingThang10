package com.example.moviefilm.film.watchfilmlocal.view;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviefilm.R;
import com.example.moviefilm.databinding.FragmentWatchFilmLocalBinding;
import com.example.moviefilm.film.watchfilmlocal.dialog.BottomSheetDialog;
import com.example.moviefilm.film.watchfilmlocal.model.MediaFile;
import com.example.moviefilm.film.watchfilmlocal.adapter.WatchFilmLocalAdapter;

import java.util.ArrayList;
import java.util.List;

public class WatchFilmLocalFragment extends Fragment implements WatchFilmLocalAdapter.setOnClickListener, BottomSheetDialog.bottomSheetDialogInterface {
    private List<MediaFile> mediaFileList;
    FragmentWatchFilmLocalBinding binding;
    private WatchFilmLocalAdapter folderAdapter;
    private BottomSheetDialog bottomSheetDialog;

    public static WatchFilmLocalFragment getInstance() {
        Bundle args = new Bundle();
        WatchFilmLocalFragment fragment = new WatchFilmLocalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_watch_film_local, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
    }

    private List<MediaFile> getListFileFilm() {
        mediaFileList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Video.Media.DATA + " like?";
        String[] selctionArg = new String[]{"%" + "Movie_Funny" + "%"};
        Cursor cursor = getContext().getContentResolver().query(uri, null, selection, selctionArg, null);
        if (cursor != null && cursor.moveToNext()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                @SuppressLint("Range") String dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
                if (Long.parseLong(size) != 0) {
                    MediaFile mediaFile = new MediaFile(id, title, displayName, size, duration, path, dateAdded);
                    mediaFileList.add(mediaFile);
                }
            } while (cursor.moveToNext());
        }
        return mediaFileList;
    }


    private void initAdapter() {
        folderAdapter = new WatchFilmLocalAdapter(getListFileFilm(), getContext());
        folderAdapter.setSetOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rcvFilmLocal.setLayoutManager(layoutManager);
        binding.rcvFilmLocal.setAdapter(folderAdapter);
    }

    @Override
    public void onClick(MediaFile mediaFile) {
        bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setData(mediaFile);
        bottomSheetDialog.setRenameFileInteface(this);
        bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    @Override
    public void onRenameFileSucces() {
        Toast.makeText(getContext(), "Rename file successfully !", Toast.LENGTH_LONG).show();
        bottomSheetDialog.dismiss();
        folderAdapter.setMediaFileList(getListFileFilm());
        SystemClock.sleep(200);
    }

    @Override
    public void onRenameFileFailed() {
        Toast.makeText(getContext(), "Rename failed !", Toast.LENGTH_LONG).show();
        bottomSheetDialog.dismiss();
    }

    @Override
    public void onShareVideo() {
        bottomSheetDialog.dismiss();
    }

    @Override
    public void onDeleteFilmSuccess() {
        Toast.makeText(getContext(), "Delete file successfully !", Toast.LENGTH_LONG).show();
        bottomSheetDialog.dismiss();
        folderAdapter.setMediaFileList(getListFileFilm());
        SystemClock.sleep(200);
    }

    @Override
    public void onDeleteFilmFailed() {
        Toast.makeText(getContext(), "Delete file failed !", Toast.LENGTH_LONG).show();
        bottomSheetDialog.dismiss();
    }

    @Override
    public void onShowProperties() {
        bottomSheetDialog.dismiss();
    }
}