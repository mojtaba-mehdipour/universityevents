package com.mehrsamweb.miyanterm;

// کتابخانه‌های موردنیاز
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // متغیرهای مربوط به عناصر صفحه
    Button btnAddStudent;
    TextView txtStudentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // اتصال صفحه به فایل XML
        setContentView(R.layout.activity_main);

        // پیدا کردن دکمه ثبت دانشجو
        btnAddStudent = findViewById(R.id.btnAddStudent);

        // پیدا کردن TextView نمایش اطلاعات
        txtStudentInfo = findViewById(R.id.txtStudentInfo);

        // مدیریت کلیک دکمه
        btnAddStudent.setOnClickListener(view -> {

            // ساخت دو فیلد ورودی
            EditText edtName = new EditText(MainActivity.this);
            EditText edtField = new EditText(MainActivity.this);

            // تعیین راهنمای ورودی‌ها
            edtName.setHint("نام دانشجو");

            edtField.setHint("رشته تحصیلی");

            // ساخت یک Layout برای قرار دادن ورودی‌ها
            android.widget.LinearLayout layout =
                    new android.widget.LinearLayout(MainActivity.this);

            layout.setOrientation(
                    android.widget.LinearLayout.VERTICAL
            );

            layout.setPadding(40, 10, 40, 10);

            // افزودن ورودی‌ها به Layout
            layout.addView(edtName);

            layout.addView(edtField);

            // ساخت Dialog
            AlertDialog dialog =
                    new AlertDialog.Builder(MainActivity.this)

                            .setTitle("ثبت دانشجو")

                            .setView(layout)

                            .setPositiveButton("ثبت", null)

                            .setNegativeButton("انصراف", (d, w) -> {
                                // بستن Dialog بدون ثبت اطلاعات
                            })

                            .create();

            // نمایش Dialog
            dialog.show();

            // مدیریت دکمه ثبت
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener(v -> {

                        // دریافت نام
                        String name = edtName.getText()
                                .toString().trim();

                        // دریافت رشته تحصیلی
                        String field = edtField.getText()
                                .toString().trim();

                        // بررسی خالی نبودن ورودی‌ها
                        if (name.isEmpty() || field.isEmpty()) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "لطفاً تمام اطلاعات را وارد کنید.",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        // نمایش اطلاعات در صفحه اصلی
                        txtStudentInfo.setText(
                                "نام دانشجو: " + name +
                                        "\nرشته تحصیلی: " + field
                        );

                        // نمایش پیام موفقیت
                        Toast.makeText(
                                MainActivity.this,
                                "اطلاعات با موفقیت ثبت شد",
                                Toast.LENGTH_SHORT
                        ).show();

                        // بستن Dialog
                        dialog.dismiss();
                    });

        });
    }
}
