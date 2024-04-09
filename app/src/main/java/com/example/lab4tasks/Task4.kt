
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun ImageGalleryTK4() {
    val context = LocalContext.current
    val images = remember { mutableStateOf(listOf<File>()) }

    LaunchedEffect(Unit) {
        val imageFiles = getImagesFromExternalStorage(context)
        images.value = imageFiles
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Change this to the number of columns you want
        modifier = Modifier.padding(4.dp),
        contentPadding = PaddingValues(4.dp),

    ) {
        items(images.value) { imageFile ->
            val bitmap = remember { mutableStateOf(null as android.graphics.Bitmap?) }

            LaunchedEffect(key1 = imageFile) {
                val bmp = BitmapFactory.decodeFile(imageFile.absolutePath)
                bitmap.value = bmp
            }

            if (bitmap.value != null) {
                Image(bitmap = bitmap.value!!.asImageBitmap(), contentDescription = null, modifier = Modifier.size(200.dp))
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

suspend fun getImagesFromExternalStorage(context: Context): List<File> = withContext(Dispatchers.IO) {
    val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageDir = File(externalStorageDir, "images")
    imageDir.listFiles { _, name -> name.endsWith(".jpg") || name.endsWith(".png") }?.toList() ?: emptyList()
}