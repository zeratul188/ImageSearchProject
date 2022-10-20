package com.example.imagesearchproject

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchproject.databinding.ActivityMainBinding
import com.example.imagesearchproject.room.ImageDao
import com.example.imagesearchproject.room.ImageDatabase
import com.example.imagesearchproject.room.ImageItem
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private lateinit var items: ArrayList<ImageItem>
    private lateinit var imageAdapter: ImageRecyclerAdapter

    //옵저버블 통합 제거를 위한 Disposable
    private var myCompositeDisposable = CompositeDisposable()

    private val handler = Handler()
    private lateinit var db: ImageDatabase
    private lateinit var dao : ImageDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = viewModel

        db = ImageDatabase.getInstance(this)!!
        dao = db?.imageDao()!!
        CoroutineScope(Dispatchers.IO).launch {
            items = (dao?.getAll() as ArrayList<ImageItem>?)!!
            with(binding) {
                imageAdapter = ImageRecyclerAdapter(items)
                listView.adapter = imageAdapter
                val spaceDecoration = VerticalSpaceItemDecoration(20)
                listView.addItemDecoration(spaceDecoration)
            }
        }

        /*val dataObserver = Observer<String> { data ->
            binding.edtSearch.hint = data
        }
        viewModel.data.observe(this, dataObserver)*/

        /*binding.fabAdd.setOnClickListener {
            viewModel.data.value = binding.edtContent.text.toString()
        }*/

        // 검색 EditText 옵저버블
        val edtSearchChangeObservable = binding.edtSearch.textChanges()
        val edtSearchSubcripion : Disposable =
            // 옵저버블에 오퍼레이터를 추가
            edtSearchChangeObservable
                // 글자가 입력되고 1초(1000ms) 후에 onNext 이벤트 데이터 흘러보내기
                .debounce(1000, TimeUnit.MILLISECONDS)
                // IO 스레드에서 실행
                // 네트워 요청, 파일 읽기/쓰기, DB처리
                .subscribeOn(Schedulers.io())
                // 구독을 통해 이벤트 응답
                .subscribeBy(
                    onNext = {
                        Log.d("Rx", "onNext: $it")
                        searchResult(binding.edtSearch.text.toString())
                    },
                    onComplete = {
                        Log.d("Rx", "onComplete")
                    },
                    onError = {
                        Log.d("Rx", "onError : $it")
                    }
                )
        myCompositeDisposable.add(edtSearchSubcripion)
    }

    fun searchResult(word: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (word == "") {
                items.clear()
                items.addAll(dao?.getAll()!!)
                Log.d("ListInfo(Empty)", "Items size : ${items.size}")
            } else {
                items.clear()
                items.addAll(dao?.searchData(word)!!)
                Log.d("ListInfo", "Items size : ${items.size}")
            }
            runOnUiThread {
                imageAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            items.clear()
            val list = dao?.getAll()!!
            items.addAll(list)
            imageAdapter.notifyDataSetChanged()
        }
        /*
        handler.post(Runnable {
            imageAdapter.notifyDataSetChanged()
        })
         */
        /*val runable = Runnable {
            try {
                items.clear()
                val db = ImageDatabase.getInstance(this)
                val dao = db?.imageDao()
                val list = dao?.getAll()!!
                items.addAll(list)
                handler.post(Runnable {
                    imageAdapter.notifyDataSetChanged()
                })
            } catch (e: Exception) {
                Log.d("error", "Error - $e")
            }
        }

        val thread = Thread(runable)
        thread.start()*/
    }

    override fun onDestroy() {
        //Disposable 모두 삭제
        myCompositeDisposable.clear()
        super.onDestroy()
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}