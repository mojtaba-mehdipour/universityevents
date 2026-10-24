package com.mehrsamweb.universityevents;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // اتصال Activity به فایل XML جزئیات
        setContentView(R.layout.activity_event_detail);


        // پیدا کردن عناصر صفحه
        TextView txtEventTitle =
                findViewById(R.id.txtEventTitle);

        TextView txtEventDate =
                findViewById(R.id.txtEventDate);

        TextView txtEventLocation =
                findViewById(R.id.txtEventLocation);

        TextView txtEventCategory =
                findViewById(R.id.txtEventCategory);

        TextView txtEventDescription =
                findViewById(R.id.txtEventDescription);

        Button btnBack =
                findViewById(R.id.btnBack);


        // دریافت Intent ارسال‌شده از صفحه اصلی
        Intent intent = getIntent();


        // دریافت عنوان
        String title = intent.getStringExtra(
                MainActivity.EXTRA_EVENT_TITLE
        );

        // دریافت تاریخ
        String date = intent.getStringExtra(
                MainActivity.EXTRA_EVENT_DATE
        );

        // دریافت محل برگزاری
        String location = intent.getStringExtra(
                MainActivity.EXTRA_EVENT_LOCATION
        );

        // دریافت دسته‌بندی
        String category = intent.getStringExtra(
                MainActivity.EXTRA_EVENT_CATEGORY
        );

        // دریافت توضیحات
        String description = intent.getStringExtra(
                MainActivity.EXTRA_EVENT_DESCRIPTION
        );


        // اگر اطلاعاتی ارسال نشده باشد، مقدار پیش‌فرض نمایش می‌دهیم.
        if (title == null || title.trim().isEmpty()) {
            title = "عنوان مشخص نشده است";
        }

        if (date == null || date.trim().isEmpty()) {
            date = "تاریخ مشخص نشده است";
        }

        if (location == null || location.trim().isEmpty()) {
            location = "محل مشخص نشده است";
        }

        if (category == null || category.trim().isEmpty()) {
            category = "دسته‌بندی مشخص نشده است";
        }

        if (description == null || description.trim().isEmpty()) {
            description = "توضیحی ثبت نشده است";
        }


        // نمایش اطلاعات دریافت‌شده در رابط کاربری
        txtEventTitle.setText(title);

        txtEventDate.setText("تاریخ: " + date);

        txtEventLocation.setText("محل برگزاری: " + location);

        txtEventCategory.setText("دسته‌بندی: " + category);

        txtEventDescription.setText("توضیحات: " + description);


        // بازگشت به صفحه قبلی
        btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}
