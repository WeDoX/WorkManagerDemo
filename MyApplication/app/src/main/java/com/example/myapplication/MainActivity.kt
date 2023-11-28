package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.util.Calendar
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    Button(onClick = {
                        //
                        doKeepAliveWork()
                        //
                        val currentDate = Calendar.getInstance()
                        val dueDate = Calendar.getInstance()
                        //

                        //
                        for(index in 1..13){
                            dueDate.add(Calendar.MINUTE, 1)
                            val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
                            val dailyWorkRequest = OneTimeWorkRequest.Builder(DailyWorker::class.java).setInitialDelay(timeDiff, TimeUnit.MILLISECONDS).build()
                            WorkManager.getInstance(this).enqueue(dailyWorkRequest)
                        }
                    }) {
                        Text(text ="按钮")
                    }
                }
            }
        }
    }

    class DailyWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
        workerParams
    ) {
        override fun doWork(): Result {
            doKeepAliveWork();
            return  Result.success()
        }

        private fun doKeepAliveWork(){
            val uploadWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
                UploadWorker::class.java,
                15,
                TimeUnit.MINUTES
            ) // Constraints
                .build()
            //
            WorkManager
                .getInstance(this.applicationContext)
                .enqueue(uploadWorkRequest)
        }
    }

    private fun doKeepAliveWork(){
        val uploadWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            UploadWorker::class.java,
            15,
            TimeUnit.MINUTES
        ) // Constraints
            .build()
        //
        WorkManager
            .getInstance(this@MainActivity)
            .enqueue(uploadWorkRequest)
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}