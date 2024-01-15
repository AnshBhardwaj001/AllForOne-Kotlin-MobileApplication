package com.example.oneforall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.oneforall.databinding.FragmentQrScannerBinding


class QrScanner : Fragment() {

    private lateinit var binding: FragmentQrScannerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_qr_scanner , container , false)
        return binding.root
    }

}