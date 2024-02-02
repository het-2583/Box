package com.example.box





import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class AddGroundActivity  : AppCompatActivity() {
    private lateinit var databaseReference: FirebaseDatabase
    private lateinit var storageReference: StorageReference

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.data != null) {
                val selectedImageUri: Uri = data.data!!
                // Now you can use the selectedImageUri to do whatever you want with the selected image.
                // For example, display it in an ImageView:
                // imageView.setImageURI(selectedImageUri)
            }
        }
    }

    private lateinit var boxNameEditText: EditText
    private lateinit var boxPriceEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var addressEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_ground);

        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance()
        boxNameEditText = findViewById(R.id.boxName)
        boxPriceEditText = findViewById(R.id.boxPrice)
        categorySpinner = findViewById(R.id.selectSports)
        addressEditText = findViewById(R.id.address)

        val selectImageButton: Button = findViewById(R.id.selectimage)
        selectImageButton.setOnClickListener{
            openImagePicker()

        }
        val SubmitButton: Button = findViewById(R.id.selectimage)
        SubmitButton.setOnClickListener{
            if (::selectedImageUri.isInitialized) {
                uploadImage(selectedImageUri)
            } else {
                // Display an error message or handle the case where no image has been selected
            }
            saveImageToDatabase("imageUrl")

        }
    }
    private lateinit var selectedImageUri: Uri
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        pickImage.launch(Intent.createChooser(intent, "Select Picture"))
    }
    private fun uploadImage(imageUri: Uri) {
        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                saveImageToDatabase(downloadUri.toString())
            } else {
                // Handle unsuccessful upload
            }
        }
    }
    private fun saveImageToDatabase(imageUrl: String) {
        val boxName = boxNameEditText.text.toString()
        val boxPrice = boxPriceEditText.text.toString().toDoubleOrNull() ?: 0.0
        val category = categorySpinner.selectedItem.toString()
        val address = addressEditText.text.toString()



        val imagesRef = databaseReference.reference.child("images")
        val groundId = imagesRef.push().key ?: return

        val imageInfo = ImageInfo(imageUrl, groundId , boxName , boxPrice , category , address )

        imagesRef.child(groundId).setValue(imageInfo)
    }
}
data class ImageInfo(
    val imageUrl: String,
    val groundId: String,
    val boxName: String,
    val boxPrice: Double,
    val category: String,
    val address: String
)