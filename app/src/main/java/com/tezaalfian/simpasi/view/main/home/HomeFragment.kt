package com.tezaalfian.simpasi.view.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.tezaalfian.simpasi.core.data.model.Food
import com.tezaalfian.simpasi.core.utils.Config
import com.tezaalfian.simpasi.core.utils.RecommendationClient
import com.tezaalfian.simpasi.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var interpreter: Interpreter

    private var config = Config()
    private var client: RecommendationClient? = null

    // This property is only valid between onCreateView and
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
        lifecycleScope.launch {
            // Run inference with TF Lite.
            Log.d(TAG, "Run inference with TFLite model.")
            val movies = listOf(Food())
            client?.recommend(movies)?.run {
                Log.d(TAG, toString())
            }
        }
//        val conditions = CustomModelDownloadConditions.Builder()
//            .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
//            .build()
//        FirebaseModelDownloader.getInstance()
//            .getModel("simpasi", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND,
//                conditions)
//            .addOnSuccessListener { model: CustomModel? ->
//                val modelFile = model?.file
//                if (modelFile != null) {
//                    interpreter = Interpreter(modelFile)
//
//                    val input = arrayOf("tes")
//                    val output: MutableMap<Int, Any> = HashMap()
//
//                    interpreter.runForMultipleInputsOutputs(input, output)
////                    val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())
////                    interpreter.run(input, output)
//                    Log.d("MLModel", output.toString())
//                }
//            }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            client?.load()
        }
    }

    override fun onStop() {
        lifecycleScope.launch {
            client?.unload()
        }
        super.onStop()
    }

    companion object {
        private val TAG = "HomeFragment";
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}