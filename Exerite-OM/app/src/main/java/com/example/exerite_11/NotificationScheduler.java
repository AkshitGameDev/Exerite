package com.example.exerite_11;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

public class NotificationScheduler {

    public static void scheduleDailyNotifications(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int[] hours = {7, 12, 15, 18, 20};
        String[] titles = {
                "Morning Exercise",
                "Healthy Lunch",
                "Afternoon Stretch",
                "Evening Workout",
                "Healthy Dinner"
        };
        String[] bodies = {
                "Time to do your morning exercise!",
                "Don't forget to have a healthy lunch!",
                "Take a break and do some stretching!",
                "Time for your evening workout!",
                "Have a nutritious dinner to end your day!"
        };

        for (int i = 0; i < hours.length; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hours[i]);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Intent intent = new Intent(context, NotificationReceiver.class);
            intent.putExtra("title", titles[i]);
            intent.putExtra("body", bodies[i]);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }
}

