package com.github.davidji80.helloworld.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.davidji80.helloworld.R;

public class AsyncTaskActivity extends AppCompatActivity {

    // 线程变量
    MyAsyncTask mTask;

    // 主布局中的UI组件
    // 加载、取消按钮
    Button button, cancel;
    // 更新的UI组件
    TextView text;
    // 进度条
    ProgressBar progressBar;

    /**
     * 自定义AsyncTask
     */
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        /**
         * 1. 执行前，在UI线程中
         */
        @Override
        protected void onPreExecute() {
            text.setText("加载中");
        }

        /**
         * 2. 开始执行，在后台线程中
         * @param strings 接收mTask.execute("传递的String参数");传递的参数
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            String param0 = strings[0];
            int count = 0;
            try {
                int length = 1;
                while (count < 100) {

                    count += length;
                    // 可调用publishProgress()显示进度, 将count作为参数
                    publishProgress(count);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return param0 + count;
        }

        /**
         * 3. 当子线程调用publishProgress时触发，在UI线程中
         * @param progresses 接收publishProgress(count);传递的参数
         */
        @Override
        protected void onProgressUpdate(Integer... progresses) {

            progressBar.setProgress(progresses[0]);
            text.setText("loading..." + progresses[0] + "%");

        }

        /**
         * 4. 后台任务执行完成后触发，在UI线程中
         * @param result 接收doInBackground的返回值作为参数
         */
        @Override
        protected void onPostExecute(String result) {
            // 执行完毕后，则更新UI
            text.setText(result);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        button = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.cancel);
        text = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mTask = new MyAsyncTask();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 手动调用execute(Params... params) 从而执行异步线程任务
                 * 注：
                 *    a. 必须在UI线程中调用
                 *    b. 同一个AsyncTask实例对象只能执行1次，若执行第2次将会抛出异常
                 *    c. 执行任务中，系统会自动调用AsyncTask的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute()
                 *    d. 不能手动调用上述方法
                 */
                mTask.execute("传递的String参数");
            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消一个正在执行的任务,onCancelled方法将会被调用
                mTask.cancel(true);
            }
        });

    }


}