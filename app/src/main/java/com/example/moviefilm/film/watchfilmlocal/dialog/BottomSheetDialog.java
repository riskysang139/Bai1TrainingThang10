package com.example.moviefilm.film.watchfilmlocal.dialog;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.moviefilm.R;
import com.example.moviefilm.base.CommonUtilis;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.databinding.FragmentBottomSheetDialogBinding;
import com.example.moviefilm.film.watchfilmlocal.model.MediaFile;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    FragmentBottomSheetDialogBinding binding;
    private MediaFile mediaFile;
    private bottomSheetDialogInterface bottomSheetDialogInterface;

    public void setData(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
    }

    public void setRenameFileInteface(bottomSheetDialogInterface bottomSheetDialogInterface) {
        this.bottomSheetDialogInterface = bottomSheetDialogInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_dialog, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.txtTitle.setText(mediaFile.getDisplayName());
        binding.btnRename.setOnClickListener(view1 -> {
            renameFile();
        });
        binding.btnShare.setOnClickListener(view12 -> {
            shareFile();
        });
        binding.btnRemove.setOnClickListener(view13 -> {
            deleteFile();
        });
        binding.btnProperties.setOnClickListener(view14 -> {
            propertiesFile();
        });
    }

    private void renameFile() {
        EditText editText = new EditText(getContext());
        final File file = new File(mediaFile.getPath());
        String videoName = file.getName();
        videoName = videoName.substring(0, videoName.lastIndexOf("."));
        editText.setText(videoName);
        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.logo_movie_app)
                .setTitle("Rename to")
                .setView(editText)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String onlyPath = file.getParentFile().getAbsolutePath();
                    String ext = file.getAbsolutePath();
                    ext = ext.substring(ext.lastIndexOf("."));
                    String newPath = onlyPath + "/" + editText.getText().toString() + ext;
                    File newFile = new File(newPath);
                    boolean rename = file.renameTo(newFile);
                    if (rename) {
                        ContentResolver resolver = getContext().getApplicationContext().getContentResolver();
                        resolver.delete(MediaStore.Files.getContentUri("external"), MediaStore.MediaColumns.DATA + "=?", new String[]{
                                file.getAbsolutePath()});
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(newFile));
                        getContext().getApplicationContext().sendBroadcast(intent);
                        bottomSheetDialogInterface.onRenameFileSucces();
                    } else {
                        dismiss();
                        bottomSheetDialogInterface.onRenameFileFailed();
                    }
                    CommonUtilis.hideKeyboard(getContext());
                }).setNegativeButton("Cancel", (dialogInterface, i) -> {
            CommonUtilis.hideKeyboard(getContext());
            bottomSheetDialogInterface.onRenameFileFailed();
        }).show();
        editText.requestFocus();
        CommonUtilis.showKeyboard(getContext());
    }

    private void shareFile() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(mediaFile.getPath());
        sharingIntent.setType("video/mp4");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(sharingIntent, "Share video via"));
        bottomSheetDialogInterface.onShareVideo();
    }

    private void deleteFile() {
        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.logo_movie_app)
                .setTitle("Delete")
                .setMessage("Do you want to delete this video ?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            Long.parseLong(mediaFile.getId()));
                    File file = new File(mediaFile.getPath());
                    boolean deleteFile = file.delete();
                    if (deleteFile) {
                        getContext().getContentResolver().delete(contentUri, null, null);
                        bottomSheetDialogInterface.onDeleteFilmSuccess();
                    } else {
                        bottomSheetDialogInterface.onDeleteFilmFailed();
                    }
                }).setNegativeButton("Cancel", (dialogInterface, i) -> {
            bottomSheetDialogInterface.onDeleteFilmFailed();
        }).show();
    }

    private void propertiesFile() {
        String frsLength = "File:  " + mediaFile.getDisplayName();

        String path = mediaFile.getPath();
        int indexPath = path.lastIndexOf("/");
        String sdLength = "Path: " + path.substring(0, indexPath);

        String thLength = "Size: " + Formatter.formatFileSize(getContext(), Long.parseLong(mediaFile.getSize()));

        String fourLength = "Length: " + Converter.convertTime(Long.parseLong(mediaFile.getDuration()));

        String nameWithFormat = mediaFile.getDisplayName();
        int index = nameWithFormat.lastIndexOf(".");
        String format = nameWithFormat.substring(index + 1);
        String fifthLength = "Format: " + format;

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mediaFile.getPath());
        String height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        String width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);

        String sixthLength = "Resolution: " + width + "x" + height;


        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.logo_movie_app)
                .setTitle("Properties")
                .setMessage(frsLength + "\n\n" + sdLength + "\n\n" + thLength + "\n\n" + fourLength + "\n\n" + fifthLength + "\n\n" + sixthLength)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    bottomSheetDialogInterface.onShowProperties();
                }).show();
        bottomSheetDialogInterface.onShowProperties();
    }

    public interface bottomSheetDialogInterface {
        void onRenameFileSucces();

        void onRenameFileFailed();

        void onShareVideo();

        void onDeleteFilmSuccess();

        void onDeleteFilmFailed();

        void onShowProperties();
    }
}
