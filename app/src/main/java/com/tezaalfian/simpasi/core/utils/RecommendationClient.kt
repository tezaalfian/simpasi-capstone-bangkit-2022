/*
 * Copyright 2020 Google LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *
 *
 */

package com.tezaalfian.simpasi.core.utils

import android.content.Context
import android.util.Log
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.tezaalfian.simpasi.core.data.model.Food
import org.tensorflow.lite.Interpreter
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*
import kotlinx.coroutines.*
import java.io.File

/** Interface to load TfLite model and provide recommendations.  */
class RecommendationClient(private val context: Context, private val config: Config) {
    private val candidates: MutableMap<Int, Food> = HashMap()
    private var tflite: Interpreter? = null

    /** An immutable result returned by a RecommendationClient.  */
    data class Result(
        /** Predicted id.  */
        val id: Int,
        /** Recommended item.  */
        val item: Food,
        /** A sortable score for how good the result is relative to others. Higher should be better.  */
        val confidence: Float
    ) {
        override fun toString(): String {
            return String.format("[%d] confidence: %.3f, item: %s", id, confidence, item)
        }
    }

    /** Load the TF Lite model and dictionary.  */
    suspend fun load() {
        downloadRemoteModel()
        loadLocalModel()
//        loadCandidateList()
    }

    /** Load TF Lite model.  */
    private suspend fun loadLocalModel() {
        return withContext(Dispatchers.IO) {
            try {
                val buffer: ByteBuffer = FileUtils.loadModelFile(
                    context.assets, config.modelPath
                )
                initializeInterpreter(buffer)
                Log.v(TAG, "TFLite model loaded.")
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
        }
    }

    private suspend fun initializeInterpreter(model: Any) {
        return withContext(Dispatchers.IO) {
            tflite?.apply {
                close()
            }
            if (model is ByteBuffer) {
                tflite = Interpreter(model)
            } else {
                tflite = Interpreter(model as File)
            }
            Log.v(TAG, "TFLite model loaded.")
        }
    }

    /** Load recommendation candidate list.  */
//    private suspend fun loadCandidateList() {
//        return withContext(Dispatchers.IO) {
//            val collection = FoodRepository.getInstance(context).getContent()
//            for (item in collection) {
//                candidates[item.id] = item
//            }
//            Log.v(TAG, "Candidate list loaded.")
//        }
//    }

    /** Free up resources as the client is no longer needed.  */
    fun unload() {
        tflite?.close()
        candidates.clear()
    }

    /** Given a list of selected items, preprocess to get tflite input.  */
    @Synchronized
    private suspend fun preprocess(selectedFoods: List<Food>): Array<String> {
        return withContext(Dispatchers.Default) {
            val inputContext : Array<String> = arrayOf()
            for (i in 0 until config.inputLength) {
                if (i < selectedFoods.size) {
                    val (id) = selectedFoods[i]
                    inputContext[i] = id
                } else {
                    // Padding input.
                    inputContext[i] = config.pad
                }
            }
            inputContext
        }
    }

    /** Postprocess to gets results from tflite inference.  */
    @Synchronized
    private suspend fun postprocess(
        outputIds: IntArray, confidences: FloatArray, selectedFoods: List<Food>
    ): List<Result> {
        return withContext(Dispatchers.Default) {
            val results = ArrayList<Result>()

            // Add recommendation results. Filter null or contained items.
            for (i in outputIds.indices) {
                if (results.size >= config.topK) {
                    Log.v(TAG, String.format("Selected top K: %d. Ignore the rest.", config.topK))
                    break
                }
                val id = outputIds[i]
                val item = candidates[id]
                if (item == null) {
                    Log.v(TAG, String.format("Inference output[%d]. Id: %s is null", i, id))
                    continue
                }
                if (selectedFoods.contains(item)) {
                    Log.v(TAG, String.format("Inference output[%d]. Id: %s is contained", i, id))
                    continue
                }
                val result = Result(
                    id, item,
                    confidences[i]
                )
                results.add(result)
                Log.v(TAG, String.format("Inference output[%d]. Result: %s", i, result))
            }
            results
        }
    }

    /** Given a list of selected items, and returns the recommendation results.  */
    @Synchronized
    suspend fun recommend(selectedFoods: List<Food>): List<Result> {
        return withContext(Dispatchers.Default) {
            val inputs = arrayOf<Any>(preprocess(selectedFoods))

            // Run inference.
            val outputIds = IntArray(config.outputLength)
            val confidences = FloatArray(config.outputLength)
            val outputs: MutableMap<Int, Any> = HashMap()
            outputs[config.outputIdsIndex] = outputIds
            outputs[config.outputScoresIndex] = confidences
            tflite?.let {
                it.runForMultipleInputsOutputs(inputs, outputs)
                postprocess(outputIds, confidences, selectedFoods)
            } ?: run {
                Log.e(TAG, "No tflite interpreter loaded")
                emptyList()
            }
        }
    }

    private fun downloadRemoteModel() {
        downloadModel(config.remoteModelName)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun downloadModel(modelName: String) {
        val conditions = CustomModelDownloadConditions.Builder()
            .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
            .build()
        FirebaseModelDownloader.getInstance()
            .getModel("simpasi", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND,
                conditions)
            .addOnSuccessListener { model: CustomModel? ->
                val modelFile = model?.file
                if (modelFile != null) {
                    showToast(context, "Downloaded remote model")
                    GlobalScope.launch { initializeInterpreter(modelFile) }
                }
            }
    }

    companion object {
        private const val TAG = "RecommendationClient"
    }
}