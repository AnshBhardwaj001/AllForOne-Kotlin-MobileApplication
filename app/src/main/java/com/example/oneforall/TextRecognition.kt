
 package com.example.oneforall

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Context.CAMERA_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.oneforall.databinding.FragmentTextRecognitionBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

 class TextRecognition : Fragment() {

    private lateinit var binding: FragmentTextRecognitionBinding
    val REQUEST_IMAGE_CAPTURE: Int = 1
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    var imageBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_text_recognition , container , false)

        imageView = binding.imageviewTextreco
        textView = binding.textViewTextreco
        val scanBtn = binding.button

        scanBtn.setOnClickListener {
            textView.setText("No Text in the Image.")
            if(checkPermission()){
                CaptureImage()
                DetectImage()
            }else{
                requestPermission()
            }
        }


        return binding.root
    }

    private fun CaptureImage(){
        var takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(context?.let { takePicture.resolveActivity(it.packageManager) } != null){
            startActivityForResult(takePicture,REQUEST_IMAGE_CAPTURE)
        }
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
             var extras: Bundle? = data?.extras;
             if (extras != null) {
                 imageBitmap = (extras.get("data")as? Bitmap)!!
             }
             imageView.setImageBitmap(imageBitmap)
             DetectImage()
         }
     }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.size > 0){
            var cameraPermission: Boolean = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if(cameraPermission){
                Toast.makeText(context,"Permission Granted",Toast.LENGTH_SHORT).show()
                CaptureImage()
            }else {
                Toast.makeText(context , "Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun DetectImage(){0
        if(imageBitmap != null){
            var image : InputImage? = imageBitmap?.let { InputImage.fromBitmap(it,0) }
            var recognizer : TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            var result : Task<Text>? = image?.let {
                recognizer.process(it).addOnSuccessListener{ text ->

                    var resultbuilder : StringBuilder = java.lang.StringBuilder()
                    for( block in text.textBlocks){
                        var blockText : String = block.text
                        var blockCornerPoint: Array<out Point>? = block.cornerPoints
                        var blockFrame: Rect? = block.boundingBox

                        for(line in block.lines){
                            var lineText: String = line.text
                            var lineCornerPoint: Array<out Point>? = line.cornerPoints
                            var lineRect: Rect? = line.boundingBox

                            for(element in line.elements){
                                var elementText: String = element.text
                                resultbuilder.append(elementText)
                            }

                            //Displaying the result
                            resultbuilder.append("\n")
                            textView.setText(resultbuilder)
                        }
                    }

                }.addOnFailureListener(OnFailureListener {
                    Toast.makeText(context , "Failed to Detect text from Image..." , Toast.LENGTH_SHORT).show()
                })
            }
        }else{
            Toast.makeText(context , "Bitmap is empty..." , Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPermission() {
        var PERMISSION_CODE: Int = 200
        requestPermissions(arrayOf( android.Manifest.permission.CAMERA), PERMISSION_CODE)
    }

    private fun checkPermission(): Boolean {
        var carmeraPermission : Int = ContextCompat.checkSelfPermission(requireContext() , CAMERA_SERVICE)
        return carmeraPermission == PackageManager.PERMISSION_GRANTED
    }
}