package com.example.moviefilm.film.watchfilmlocal.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.moviefilm.R;
import com.example.moviefilm.databinding.FragmentBottomSheetDialogBinding;
import com.example.moviefilm.databinding.FragmentBottomSheetSortBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetSortDialog extends BottomSheetDialogFragment {
    FragmentBottomSheetSortBinding binding;
    private boolean sortName, sortDate, sortSize;
    private sortList sortList;

    public void setSortList(BottomSheetSortDialog.sortList sortList) {
        this.sortList = sortList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_sort, container, false);
        binding.txtSortName.setOnClickListener(view1 -> {
            if (sortName) {
                sortName = false;
                binding.lnSortName.setVisibility(View.GONE);
            } else {
                binding.lnSortName.setVisibility(View.VISIBLE);
                sortName = true;
            }
        });
        binding.txtSortDate.setOnClickListener(view12 -> {
            if (sortDate) {
                binding.lnSortDate.setVisibility(View.GONE);
                sortDate = false;
            } else {
                binding.lnSortDate.setVisibility(View.VISIBLE);

                sortDate = true;
            }
        });
        binding.txtSortSize.setOnClickListener(view13 -> {
            if (sortSize) {
                binding.lnSortSize.setVisibility(View.GONE);
                sortSize = false;
            } else {
                binding.lnSortSize.setVisibility(View.VISIBLE);
                sortSize = true;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSortNameAz.setOnClickListener(view14 -> sortList.sortName(true));
        binding.btnSortNameZa.setOnClickListener(view15 -> sortList.sortName(false));
        binding.btnSortDateNew.setOnClickListener(view16 -> sortList.sortDate(true));
        binding.btnSortDateOld.setOnClickListener(view17 -> sortList.sortDate(false));
        binding.btnSortSizeBig.setOnClickListener(view19 -> sortList.sortSize(true));
        binding.btnSortSizeSmall.setOnClickListener(view18 -> sortList.sortSize(false));
    }

    public interface sortList {
        void sortSize(boolean isAZ);

        void sortName(boolean isNew);

        void sortDate(boolean isNew);
    }
}
