package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var taux: Double = 0.0
    private val TAUX_E_D = 0.99
    private val TAUX_E_Y = 143.82
    private val TAUX_D_Y = 144.43
    private lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        binding.infoData = dataViewModel
        binding.lifecycleOwner = this@MainActivity
        dataViewModel.initData()
        //mettreAJourLabels(TAUX_E_D, R.string.euro, R.string.dollard)
        dataViewModel.data.observe(this, Observer<Data> { data ->
            Log.i("observe", "Observer $data.toString()")
        })
        try {
            binding.buttonHaut.setOnClickListener {
                when {
                    binding.editTextBas.text.isNotEmpty() -> {
                        dataViewModel.updateData(Data(
                            null,
                            null,
                            binding.editTextBas.text.toString().toDouble() * taux,
                            binding.editTextBas.text.toString().toDouble()))

                    }

                    binding.editTextBas.text.isEmpty() -> Toast.makeText(
                        this, "Conversion impossible!", Toast.LENGTH_LONG
                    ).show()
                }
            }

            binding.buttonBas.setOnClickListener {
                when {
                    binding.editTextHaut.text.isNotEmpty() -> {
                        dataViewModel.updateData(Data(
                            null,
                            null,
                            binding.editTextHaut.text.toString().toDouble(),
                            binding.editTextHaut.text.toString().toDouble() / taux))


                    }

                    binding.editTextHaut.text.isEmpty() -> Toast.makeText(
                        this, "Conversion impossible!", Toast.LENGTH_LONG
                    ).show()
                }
            }

            binding.rbEuroDollar.setOnClickListener {
                mettreAJourLabels(TAUX_E_D, R.string.euro, R.string.dollard)
            }

            binding.rbEuroYen.setOnClickListener {
                mettreAJourLabels(TAUX_E_Y, R.string.euro, R.string.yen)
            }

            binding.rbDollarYen.setOnClickListener {
                mettreAJourLabels(TAUX_D_Y, R.string.dollard, R.string.yen)
            }

        } catch (e: Exception) {
        }
    }

    private fun mettreAJourLabels(taux: Double, labelHautId: Int, labelBasId: Int) {
        this.taux = taux
        dataViewModel.updateData(Data(getString(labelHautId), getString(labelBasId),null,null))

    }
}