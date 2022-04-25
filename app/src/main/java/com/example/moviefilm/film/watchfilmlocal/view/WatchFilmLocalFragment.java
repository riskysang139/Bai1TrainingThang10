package com.example.moviefilm.film.watchfilmlocal.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviefilm.R;
import com.example.moviefilm.databinding.FragmentWatchFilmLocalBinding;
import com.example.moviefilm.film.watchfilmlocal.adapter.WatchFilmLocalAdapter;
import com.example.moviefilm.film.watchfilmlocal.dialog.BottomSheetEditVideoLocalDialog;
import com.example.moviefilm.film.watchfilmlocal.dialog.BottomSheetSortDialog;
import com.example.moviefilm.film.watchfilmlocal.filmlocal.VideoLocalActivity;
import com.example.moviefilm.film.watchfilmlocal.model.MediaFile;
import com.example.moviefilm.film.watchfilmlocal.viewmodel.WatchFilmLocalViewModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WatchFilmLocalFragment extends Fragment implements WatchFilmLocalAdapter.setOnClickListener, BottomSheetEditVideoLocalDialog.bottomSheetDialogInterface, BottomSheetSortDialog.sortList {
    private static final int REQUEST_PERMISSION_SETTING = 12;
    private ArrayList<MediaFile> mediaFileList;
    FragmentWatchFilmLocalBinding binding;
    private WatchFilmLocalAdapter folderAdapter;
    private BottomSheetEditVideoLocalDialog bottomSheetEditVideoLocalDialog;
    private static final int STORAGE_PERMISSION = 1;
    private WatchFilmLocalViewModels viewModels;
    private BottomSheetSortDialog bottomSheetSortDialog;
    private SharedPreferences sharedPref;

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
        viewModels = ViewModelProviders.of(this).get(WatchFilmLocalViewModels.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnPermission.setOnClickListener(view1 -> {
            checkPermission();
        });
        binding.imgSort.setOnClickListener(view12 -> {
            bottomSheetSortDialog = new BottomSheetSortDialog();
            bottomSheetSortDialog.setSortList(this);
            bottomSheetSortDialog.show(getActivity().getSupportFragmentManager(), "TAG");
        });
        initAdapter();
    }

    private void observeListFilm() {
        viewModels.fetchListFilm(getContext());
        viewModels.getListFilmMutableLiveData().observe(getActivity(), mediaFiles -> {
            if (mediaFiles.size() == 0) {
                binding.txtNoData.setVisibility(View.VISIBLE);
            } else {
                mediaFileList = mediaFiles;
                binding.txtNoData.setVisibility(View.GONE);
                if (folderAdapter != null) {
                    folderAdapter.setMediaFileList(mediaFiles);
                }
            }
        });
    }

    private void initAdapter() {
        mediaFileList = new ArrayList<>();
        binding.txtNoData.setVisibility(View.GONE);
        folderAdapter = new WatchFilmLocalAdapter(mediaFileList, getContext());
        folderAdapter.setSetOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rcvFilmLocal.setLayoutManager(layoutManager);
        binding.rcvFilmLocal.setAdapter(folderAdapter);
    }

    @Override
    public void onClick(MediaFile mediaFile) {
        bottomSheetEditVideoLocalDialog = new BottomSheetEditVideoLocalDialog();
        bottomSheetEditVideoLocalDialog.setData(mediaFile);
        bottomSheetEditVideoLocalDialog.setRenameFileInteface(this);
        bottomSheetEditVideoLocalDialog.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    @Override
    public void onClickItem(ArrayList<MediaFile> mediaFile, int position) {
        gotoWatchVideo(mediaFile, position);
    }

    @Override
    public void onRenameFileSucces() {
        Toast.makeText(getContext(), "Rename file successfully !", Toast.LENGTH_LONG).show();
        bottomSheetEditVideoLocalDialog.dismiss();
        viewModels.fetchListFilm(getContext());
    }

    @Override
    public void onRenameFileFailed() {
        Toast.makeText(getContext(), "Rename failed !", Toast.LENGTH_LONG).show();
        bottomSheetEditVideoLocalDialog.dismiss();
    }

    @Override
    public void onShareVideo() {
        bottomSheetEditVideoLocalDialog.dismiss();
    }

    @Override
    public void onDeleteFilmSuccess() {
        Toast.makeText(getContext(), "Delete file successfully !", Toast.LENGTH_LONG).show();
        bottomSheetEditVideoLocalDialog.dismiss();
        viewModels.fetchListFilm(getContext());
        binding.rcvFilmLocal.smoothScrollToPosition(0);
    }

    @Override
    public void onDeleteFilmFailed() {
        Toast.makeText(getContext(), "Delete file failed !", Toast.LENGTH_LONG).show();
        bottomSheetEditVideoLocalDialog.dismiss();
    }

    @Override
    public void onShowProperties() {
        bottomSheetEditVideoLocalDialog.dismiss();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            binding.rlPermission.setVisibility(View.GONE);
            observeListFilm();
        } else {
            binding.rlPermission.setVisibility(View.VISIBLE);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                String per = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    binding.rlPermission.setVisibility(View.VISIBLE);
                    boolean showRational = shouldShowRequestPermissionRationale(per);
                    if (!showRational) {
                        // user click on never ask again
                        new AlertDialog.Builder(getContext())
                                .setIcon(R.drawable.logo_movie_app)
                                .setTitle("App Permission")
                                .setMessage("For playing video, you must allow this app to access video files on your device"
                                        + "\n\n" + "Now following the below steps" + "\n\n" +
                                        "Open Settings from below button" + "\n" +
                                        "Click on Permission" + "\n" + "Allow access for storage")
                                .setPositiveButton("Open Settings", (dialogInterface, i1) -> {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                }).create().show();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
                    }
                } else {
                    observeListFilm();
                    binding.rlPermission.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            binding.rlPermission.setVisibility(View.GONE);
            observeListFilm();
        } else {
            binding.rlPermission.setVisibility(View.VISIBLE);
        }
    }

    private void gotoWatchVideo(ArrayList<MediaFile> mediaFile, int position) {
        Intent intent = new Intent(getContext(), VideoLocalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("mediaFile", mediaFile);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    @Override
    public void sortSize(boolean sortSize) {
        if (mediaFileList.size() > 0) {
            if (sortSize) {
                Collections.sort(mediaFileList, new Comparator<MediaFile>() {
                    @Override
                    public int compare(MediaFile f1, MediaFile f2) {
                        if (Long.parseLong(f1.getSize()) > (Long.parseLong(f2.getSize())))
                            return 1;
                        else if (Long.parseLong(f1.getSize()) == (Long.parseLong(f2.getSize())))
                            return 0;
                        else
                            return -1;
                    }
                });

            } else {
                Collections.sort(mediaFileList, new Comparator<MediaFile>() {
                    @Override
                    public int compare(MediaFile f1, MediaFile f2) {
                        if (Long.parseLong(f1.getSize()) > (Long.parseLong(f2.getSize())))
                            return -1;
                        else if (Long.parseLong(f1.getSize()) == (Long.parseLong(f2.getSize())))
                            return 0;
                        else
                            return 1;
                    }
                });
            }
            folderAdapter.setMediaFileList(mediaFileList);
        }
        bottomSheetSortDialog.dismiss();
        binding.rcvFilmLocal.smoothScrollToPosition(0);
    }

    @Override
    public void sortName(boolean sortAZ) {
        if (mediaFileList.size() > 0) {
            if (sortAZ) {
                Collections.sort(mediaFileList, new Comparator<MediaFile>() {
                    @Override
                    public int compare(MediaFile f1, MediaFile f2) {
                        return f1.getDisplayName().compareTo(f2.getDisplayName());
                    }
                });

            } else {
                Collections.sort(mediaFileList, new Comparator<MediaFile>() {
                    @Override
                    public int compare(MediaFile f1, MediaFile f2) {
                        return f2.getDisplayName().compareTo(f1.getDisplayName());
                    }
                });
            }
            folderAdapter.setMediaFileList(mediaFileList);
        }
        bottomSheetSortDialog.dismiss();
        binding.rcvFilmLocal.smoothScrollToPosition(0);
    }

    @Override
    public void sortDate(boolean sortDate) {
        if (mediaFileList.size() > 0) {
            if (!sortDate) {
                Collections.sort(mediaFileList, new Comparator<MediaFile>() {
                    @Override
                    public int compare(MediaFile f1, MediaFile f2) {
                        return f1.getDateAdded().compareTo(f2.getDateAdded());
                    }
                });

            } else {
                Collections.sort(mediaFileList, new Comparator<MediaFile>() {
                    @Override
                    public int compare(MediaFile f1, MediaFile f2) {
                        return f2.getDateAdded().compareTo(f1.getDateAdded());
                    }
                });
            }
            folderAdapter.setMediaFileList(mediaFileList);
        }
        bottomSheetSortDialog.dismiss();
        binding.rcvFilmLocal.smoothScrollToPosition(0);
    }
}