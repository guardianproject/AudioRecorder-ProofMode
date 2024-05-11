package com.proofmode.proofmodelib.worker

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.proofmode.proofmodelib.utils.ProofModeUtils
import org.witness.proofmode.ProofMode
import org.witness.proofmode.service.MediaWatcher
import timber.log.Timber
import java.util.Date

class GenerateProofWorker(
        private val context: Context,
        workParams: WorkerParameters
) : Worker(context.applicationContext, workParams) {
    override fun doWork(): Result {
        val audioUriString = inputData.getString(ProofModeUtils.MEDIA_KEY)
        val audioUri = Uri.parse(audioUriString)
        Timber.d("Worker uri path ${audioUri.path}")
        val existingHash = ProofModeUtils.proofExistsForMedia(context, audioUri)
        if (existingHash.isNullOrEmpty()) {
            val hash: String? = MediaWatcher.getInstance(context.applicationContext).processUri(audioUri,true,Date())   //ProofMode.generateProof(context, Uri.parse(audioUriString))
            if (!hash.isNullOrEmpty()) {
                return Result.success(workDataOf(ProofModeUtils.MEDIA_HASH to hash))
            }
            return Result.failure()

        }
        return Result.success(workDataOf(ProofModeUtils.MEDIA_HASH to existingHash))
    }
}