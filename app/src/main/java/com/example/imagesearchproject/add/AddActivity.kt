package com.example.imagesearchproject.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.imagesearchproject.R
import com.example.imagesearchproject.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private val viewModel: AddViewModel by viewModels() {
        AddViewModelFactory(this)
    }
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        binding.addViewModel = viewModel

        with(binding) {
            edtTitle.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.item.value?.title = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
            edtContent.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.item.value?.content = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
            edtURL.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.item.value?.url = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }
}