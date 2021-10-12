package com.androidTask5.imagesearchapp.ui.details

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.androidTask5.imagesearchapp.R
import com.androidTask5.imagesearchapp.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_cat_photo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binder = FragmentDetailsBinding.bind(view)

        binder.apply {
            val photo = args.photo

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
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) { gallerySaveImage(photo.url) }
                    Toast.makeText(
                        requireContext(),
                        R.string.image_uploaded,
                        Toast.LENGTH_LONG
                    ).show()

            }
        }
    }

    private fun checkWritePermission(): Boolean {

        return when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                true
            }
            else -> {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
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
}