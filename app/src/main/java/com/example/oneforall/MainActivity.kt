package com.example.oneforall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationBarView
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView : BottomNavigationView
    var textRecognition: TextRecognition = TextRecognition()
    var languageTranslation: LanguageTranslation = LanguageTranslation()
    var ocrRecognition: OcrRecognition = OcrRecognition()
    var qrScanner: QrScanner = QrScanner()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        loadFragment(TextRecognition())

        bottomNavigationView.setOnItemSelectedListener{
            when (it.itemId){
                R.id.image -> {
                    loadFragment(TextRecognition())
                    true
                }
                R.id.ocr -> {
                    loadFragment(OcrRecognition())
                    true
                }
                R.id.qr_code -> {
                    loadFragment(QrScanner())
                    true
                }
                R.id.language -> {
                    loadFragment(LanguageTranslation())
                    true
                }

                else -> {
                    loadFragment(TextRecognition())
                    true
                }

            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView , fragment)
        transaction.commit()
    }

}