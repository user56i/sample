package com.nixsolutions.cupboard.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.nixsolutions.cupboard.R;

import butterknife.InjectView;
import rx.Subscription;


public class RxActivity extends ActionBarActivity {

    @InjectView(R.id.rxText)
    TextView textView;
    private Subscription subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);


//        Observable.interval(1000, TimeUnit.MILLISECONDS)
//                .123
//                .concatWith(Observable.just(2L))
//                .concatMap(new Func1<Integer, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(Integer s) {
//                        return Observable.just(s + 1);
//                    }
//                })
//                .filter(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer s) {
//                        return s % 2 == 0;
//                    }
//                })
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer s) {
//
//                    }
//                })
//                .doOnCompleted(new Action0() {
//                    @Override
//                    public void call() {
//                        Log.d("qq", "doOnCompleted");
//                    }
//                })
//                .
//        .subscribe(new Action1<String>() {
//                       @Override
//                       public void call(String s) {
//                           Log.d("qq", s);
//                       }
//                   }
//        );

    }
}
