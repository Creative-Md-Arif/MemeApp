package com.example.memefetcherapp
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemeActivity : AppCompatActivity() {

    private lateinit var memeImageView: ImageView
    private lateinit var memeTitleTextView: TextView
    private lateinit var nextMemeButton: Button
    private lateinit var loadingDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)

        memeImageView = findViewById(R.id.memeImageView)
        memeTitleTextView = findViewById(R.id.memeTitleTextView)
        nextMemeButton = findViewById(R.id.nextMemeButton)

        loadingDialog = ProgressDialog(this)
        loadingDialog.setMessage("Loading Meme...")
        loadingDialog.setCancelable(false)

        fetchMeme()

        nextMemeButton.setOnClickListener {
            fetchMeme()
        }
    }

    private fun fetchMeme() {
        loadingDialog.show()
        val api = RetrofitClient.instance
        api.getMeme().enqueue(object : Callback<MemeResponse> {
            override fun onResponse(call: Call<MemeResponse>, response: Response<MemeResponse>) {
                if (response.isSuccessful) {
                    val meme = response.body()
                    meme?.let {
                        Glide.with(this@MemeActivity).load(it.url).into(memeImageView)
                        memeTitleTextView.text = it.title
                    }
                    loadingDialog.dismiss()
                } else {
                    loadingDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<MemeResponse>, t: Throwable) {
                loadingDialog.dismiss()
                // ত্রুটি হ্যান্ডেলিং
            }
        })
    }
}
