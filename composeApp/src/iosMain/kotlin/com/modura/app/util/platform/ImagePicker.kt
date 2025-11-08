package com.modura.app.util.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.darwin.NSObject

@Composable
actual fun rememberImagePicker(
    onResult: (List<String>) -> Unit
): ImagePicker {
    return remember {
        IosImagePicker(onResult)
    }
}

class IosImagePicker(
    private val onResult: (List<String>) -> Unit
) : ImagePicker {

    @OptIn(ExperimentalForeignApi::class)
    override fun pickImages() {
        val pickerDelegate = object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
                picker.dismissViewControllerAnimated(true, null)
                val imageUris = didFinishPicking.mapNotNull {
                    (it as? PHPickerResult)?.itemProvider?.let { provider ->
                        provider.registeredTypeIdentifiers.firstOrNull() as? String ?: ""
                    }
                }
                onResult(imageUris.filter { it.isNotEmpty() })
            }
        }
        val pickerViewController = PHPickerViewController(PHPickerConfiguration()).apply {
            this.delegate = pickerDelegate
        }
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.presentViewController(pickerViewController, true, null)
    }
}