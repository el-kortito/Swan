package itai.swan.notifications;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**********************************************************************************************/

public class MyJobService extends JobService {
    @Override //return true if doing asynchronous work
    public boolean onStartJob(JobParameters params) {
        NotificationManager.postNotification(getApplicationContext());

        Intent service = new Intent(getApplicationContext(), MyJobService.class);
        getApplicationContext().startService(service);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    static public void scheduleJob(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        //for debugging
        if (sp.contains("notificationServiceJobId")) {
            Log.d("ZZZ", "attempting to schedule notification job, but seems its already scheduled");
            return;
        }

        NotificationManager.postNotification(context);

        ComponentName serviceComponent = new ComponentName(context, MyJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(0, serviceComponent)
                .setPeriodic(43200000)//12 hours
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(jobInfo);

        sp.edit().putInt("notificationServiceJobId", jobInfo.getId()).apply();
    }

    static public void removeJob(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        if (sp.contains("notificationServiceJobId")) {
            jobScheduler.cancel(sp.getInt("notificationServiceJobId", -1));
            sp.edit().remove("notificationServiceJobId").apply();
        }
    }
}
