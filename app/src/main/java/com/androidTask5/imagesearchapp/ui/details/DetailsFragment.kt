package com.androidTask5.imagesearchapp.ui.details

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment.DIRECTORY_PICTURES
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androidTask5.imagesearchapp.R
import com.androidTask5.imagesearchapp.data.CatPhoto
import com.androidTask5.imagesearchapp.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlin.LazyThreadSafetyMode.NONE

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()
    private val photo: CatPhoto by lazy(NONE) { args.photo }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binder = FragmentDetailsBinding.bind(view)

        binder.apply {
            Glide.with(this@DetailsFragment)
                .load(photo.url)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewDescription.isVisible = true
                        textViewDescription.isVisible = photo.id != null
                        buttonSave.isVisible = true
                        return false
                    }
                })
                .into(imageView)

            textViewDescription.text = photo.id
            buttonSave.setText(R.string.save_image)
            buttonSave.setOnClickListener {
                if (checkWritePermission()) {
                    gallerySaveImage(photo.url)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            WRITE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.firstOrNull() == PERMISSION_GRANTED) {
                    gallerySaveImage(photo.url)
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun checkWritePermission(): Boolean {
        return when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.P -> true
            ContextCompat.checkSelfPermission(
                requireContext(),
                WRITE_EXTERNAL_STORAGE
            ) == PERMISSION_GRANTED -> {
                true
            }
            // Seems like there is no code for showing permission
            // rationale: https://developer.android.com/training/permissions/requesting#manage-request-code-yourself
            else -> {
                requestPermissions(
                    arrayOf(WRITE_EXTERNAL_STORAGE),
                    WRITE_PERMISSION_REQUEST_CODE
                )
                false
            }
        }
    }

    private fun gallerySaveImage(url: String) {
        val filename = url.substringAfterLast("/")
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(filename)
            .setDestinationInExternalPublicDir(DIRECTORY_PICTURES, filename)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        (requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager)
            .enqueue(request)
    }

    private companion object {
        const val WRITE_PERMISSION_REQUEST_CODE = 1
    }
}