package com.tezaalfian.simpasi.view.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import com.tezaalfian.simpasi.core.ui.ListFoodAdapter
import com.tezaalfian.simpasi.databinding.FragmentHomeBinding
import org.tensorflow.lite.Interpreter
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var interpreter: Interpreter
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): NestedScrollView? {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val conditions = CustomModelDownloadConditions.Builder()
            .build()
        FirebaseModelDownloader.getInstance()
            .getModel("simpasi", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND,
                conditions)
            .addOnSuccessListener { model: CustomModel? ->
                val modelFile = model?.file
                if (modelFile != null) {
                    interpreter = Interpreter(modelFile)

                    val input = ByteBuffer.allocateDirect(8).order(ByteOrder.nativeOrder())
                    input.putFloat(8f)
                    input.putFloat(1f)

                    val bufferSize = 22 * java.lang.Float.SIZE / java.lang.Byte.SIZE
                    val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())
                    interpreter.run(input, modelOutput)
                    modelOutput.rewind()
                    val ids = modelOutput.asIntBuffer()
                    try {
                        val foodId = mutableListOf<Int>()
                        for (i in 0 until 10) {
                            Log.d(TAG, ids.get(i).toString())
                            foodId.add(i, ids.get(i))
                        }
                        loadResep(foodId)
                    } catch (e: IOException) {
                        Log.e(TAG, e.message.toString())
                    }
                }
            }
    }

    private fun loadResep(foodId: List<Int>) {
        val foodAdapter = ListFoodAdapter()
        binding?.rvMenu?.adapter = foodAdapter
        try {
            val reader = BufferedReader(
                InputStreamReader(resources.assets.open("resep.csv"))
            ).readLines()
            val foods = mutableListOf<FoodEntity>()
            var i = 0
            foodId.map {
                val label: String = reader[it-1]
                val food = label.split(";")
                foods.add(i++, FoodEntity(food[0], food[1], food[2]))
            }
            foodAdapter.setData(foods)
        } catch (e: IOException) {
            Log.d(TAG, e.message.toString())
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}