package com.tezaalfian.simpasi.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.model.Food
import com.tezaalfian.simpasi.core.ui.FoodViewModelFactory
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
    private lateinit var viewModel: HomeViewModel

    private lateinit var interpreter: Interpreter
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val factory = FoodViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
        binding?.progressBar?.visibility = View.VISIBLE

        val ids = mutableListOf<Float>()
        loadModel(ids)
        viewModel.getLastFood().observe(requireActivity()){result ->
            if (result != null) {
                when(result) {
                    is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        for (i in result.data.indices){
                            ids.add(i, result.data[i].toFloat())
                        }
//                        if (ids.size == 1){
//                            callModel(ids[0], ids[0])
//                        }
                        if (ids.size == 2){
                            callModel(ids[0], ids[1])
                        }
                    }
                    is Resource.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            requireActivity(),
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun loadModel(ids: List<Float>) {
        val conditions = CustomModelDownloadConditions.Builder()
            .build()
        FirebaseModelDownloader.getInstance()
            .getModel("simpasi", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND,
                conditions)
            .addOnSuccessListener { model: CustomModel? ->
                val modelFile = model?.file
                if (modelFile != null) {
                    interpreter = Interpreter(modelFile)
                    if (ids.size == 2){
                        callModel(ids[0], ids[1])
                    }else{
                        val foodId = arrayListOf<Int>()
                        for (i in 1..77){
                            foodId.add(i)
                        }
                        loadResep(foodId)
                    }
                }
            }
    }

    private fun callModel(id1: Float, id2: Float){
        val input = ByteBuffer.allocateDirect(8).order(ByteOrder.nativeOrder())
        input.putFloat(id1)
        input.putFloat(id2)

        val bufferSize = 22 * java.lang.Float.SIZE / java.lang.Byte.SIZE
        val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())
        interpreter.run(input, modelOutput)
        modelOutput.rewind()
        val ids = modelOutput.asIntBuffer()
        try {
            val foodId = mutableListOf<Int>()
            for (i in 0 until 10) {
                foodId.add(i, ids.get(i))
            }
            loadResep(foodId)
        } catch (e: IOException) {
            Toast.makeText(requireActivity(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadResep(foodId: List<Int>) {
        val foodAdapter = ListFoodAdapter()
        binding?.rvMenu?.adapter = foodAdapter
        binding?.progressBar?.visibility = View.GONE
        try {
            val reader = BufferedReader(
                InputStreamReader(resources.assets.open("resep.csv"))
            ).readLines()
            val foods = mutableListOf<Food>()
            var i = 0
            foodId.map {
                val label: String = reader[it-1]
                val food = label.split(";")
                foods.add(i++, Food(food[0], food[1], food[2]))
            }
            foodAdapter.setData(foods)
        } catch (e: IOException) {
            Toast.makeText(requireActivity(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}