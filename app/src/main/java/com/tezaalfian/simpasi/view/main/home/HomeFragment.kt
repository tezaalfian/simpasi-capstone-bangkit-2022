package com.tezaalfian.simpasi.view.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.tezaalfian.simpasi.core.data.model.Food
import com.tezaalfian.simpasi.databinding.FragmentHomeBinding
import org.tensorflow.lite.Interpreter
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var interpreter: Interpreter
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        lifecycleScope.launch {
//            client?.load()
//            val recommendations: List<Result> = client!!.recommend(listOf(Food()))
//            Log.d("HomeFragment", recommendations.toString())
//        }
//        lifecycleScope.launch {
//            // Run inference with TF Lite.
//            Log.d(TAG, "Run inference with TFLite model.")
//            val movies = listOf(Food())
//            client?.recommend(movies)?.run {
//                Log.d(TAG, toString())
//            }
//        }

        val conditions = CustomModelDownloadConditions.Builder()
            .build()
        FirebaseModelDownloader.getInstance()
            .getModel("simpasi", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND,
                conditions)
            .addOnFailureListener {
                it.printStackTrace()
                Log.d(TAG+"Error", it.message.toString())
            }
            .addOnSuccessListener { model: CustomModel? ->
                val modelFile = model?.file
                if (modelFile != null) {
                    interpreter = Interpreter(modelFile)
                    val input = arrayOf(Food().userId)
//                    val output: MutableMap<Int, Any> = HashMap()
//                    interpreter.runForMultipleInputsOutputs(input, output)
//                    val bufferSize = 1000 * java.lang.Float.SIZE / java.lang.Byte.SIZE

//                    val input: ByteBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.nativeOrder())
//                    input.put(Food().userId.toByte())
                    val bufferSize = 1000 * java.lang.Float.SIZE / java.lang.Byte.SIZE
                    val output = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())
//                    val output = listOf<String>()
                    // Creates inputs for reference.
//                    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 3), DataType.INT32)
//                    inputFeature0.loadBuffer(byteBuffer)
//
//                    // Runs model inference and gets result.
//                    val outputs = interpreter.process
//                    val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

//                    val bufferSize = 1000 * java.lang.Float.SIZE / java.lang.Byte.SIZE
                    interpreter.run(input, output)
//                    interpreter.runForMultipleInputsOutputs(input, output)
//                    Log.d(TAG, output.float.toString())
                    output.rewind()
//                    val probabilities = output.asFloatBuffer()
                    try {
                        Log.d(TAG, output.toString())
                        for (i in 0 until output.capacity()) {
                            val probability = output.get(i)
                            Log.d(TAG, "$probability")
                        }
                    } catch (e: IOException) {
                        // File not found?
                    }
                }
            }
    }

    companion object {
        private const val TAG = "HomeFragment";
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}