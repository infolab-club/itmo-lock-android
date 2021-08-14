package club.infolab.itmo_lock.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import club.infolab.itmo_lock.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
    }
}