package com.modura.app.util.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import org.jetbrains.skia.Image
import org.jetbrains.skia.makeFromEncoded
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSize
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIViewController
import platform.darwin.NSObject


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberCameraManager(onResult: (ImageBitmap?) -> Unit): () -> Unit {
    val imagePickerDelegate = remember {
        ImagePickerDelegate { image ->
            onResult(image?.toImageBitmap())
        }
    }

    val topViewController = UIApplication.sharedApplication.keyWindow?.rootViewController

    return remember {
        {
            if (topViewController != null) {
                val picker = UIImagePickerController()
                picker.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
                picker.cameraCaptureMode = UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto
                picker.delegate = imagePickerDelegate
                topViewController.presentViewController(picker, animated = true, completion = null)
            } else {
                println("iOS 최상위 ViewController를 찾을 수 없습니다.")
                onResult(null)
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private class ImagePickerDelegate(
    private val onImageSelected: (UIImage?) -> Unit
) : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>
    ) {
        val image = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
            ?: didFinishPickingMediaWithInfo[UIImagePickerControllerEditedImage] as? UIImage

        onImageSelected(image?.resized())
        picker.dismissViewControllerAnimated(true, null)
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        onImageSelected(null)
        picker.dismissViewControllerAnimated(true, null)
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun UIImage.resized(maxSize: Double = 1024.0): UIImage? {
    val oldSize = this.size.useContents { Pair(width, height) }
    val scale = if (oldSize.first > oldSize.second) {
        maxSize / oldSize.first
    } else {
        maxSize / oldSize.second
    }

    if (scale >= 1.0) return this

    val newWidth = oldSize.first * scale
    val newHeight = oldSize.second * scale
    val newSize = CGSizeMake(newWidth, newHeight)

    UIGraphicsBeginImageContextWithOptions(newSize, false, 0.0)
    this.drawInRect(CGRectMake(0.0, 0.0, newWidth, newHeight))
    val newImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()
    return newImage
}

private fun UIImage.toImageBitmap(): ImageBitmap {
    val  byteArray = UIImageJPEGRepresentation(this, 1.0)
        ?: throw IllegalStateException("UIImage를 ByteArray로 변환하는데 실패했습니다.")    // ByteArray를 Skia Image로 디코딩한 후, Compose ImageBitmap으로 변환합니다.
    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}