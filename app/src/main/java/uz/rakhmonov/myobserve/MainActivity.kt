package uz.rakhmonov.myobserve

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.rakhmonov.myobserve.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        var myObservable=createObservableString()
        myObservable.subscribe { binding.tv2.text=it}
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce (5L, TimeUnit.SECONDS)
//                .filter { it.length==5 || it.length==10 }
//                .map { it.reversed() }
                .subscribe {
                    Log.d("TAG", "bizning yozuv: ")
//                binding.tv.text=it
            }




    }
    private fun createObservableString(): Observable<String> {
        return Observable.create { emitter->
            binding.edt.addTextChangedListener {
                emitter.onNext(it.toString())
            }

        }
    }
}