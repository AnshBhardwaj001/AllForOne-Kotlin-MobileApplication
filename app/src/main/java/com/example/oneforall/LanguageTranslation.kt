package com.example.oneforall

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.oneforall.databinding.FragmentLanguageTranslationBinding

class LanguageTranslation : Fragment() {

    private lateinit var binding: FragmentLanguageTranslationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_language_translation , container , false)
        return binding.root
    }

}