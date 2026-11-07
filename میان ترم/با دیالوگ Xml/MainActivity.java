package com.mehrsamweb.mojtabadialog;

// کتابخانه‌های موردنیاز
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    // تعریف متغیرهای مربوط به عناصر صفحه اصلی
    Button btnAddStudent;
    TextView txtStudentInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // اتصال فایل XML صفحه اصلی به Activity
        setContentView(R.layout.activity_main);


        // شناسایی دکمه ثبت دانشجو
        btnAddStudent = findViewById(R.id.btnAddStudent);

        // شناسایی TextView نمایش اطلاعات
        txtStudentInfo = findViewById(R.id.txtStudentInfo);


        // تعریف رویداد کلیک برای دکمه ثبت دانشجو
        btnAddStudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // با کلیک روی دکمه، فرم ثبت دانشجو نمایش داده می‌شود
                showAddStudentDialog();

            }
        });

    }


    // =====================================================
    // متد نمایش فرم ثبت دانشجو
    // =====================================================

    private void showAddStudentDialog() {

        /*
            LayoutInflater برای تبدیل فایل XML
            به یک View قابل استفاده در برنامه است.
        */

        LayoutInflater inflater = getLayoutInflater();


        // بارگذاری فایل XML فرم ثبت دانشجو
        View dialogView = inflater.inflate(
                R.layout.dialog_add_student,
                null
        );


        /*
            شناسایی EditTextهای موجود در فایل XML فرم

            نکته:
            چون این EditTextها داخل فایل XML فرم هستند،
            باید از dialogView برای پیدا کردن آن‌ها استفاده کنیم.
        */

        EditText edtStudentName =
                dialogView.findViewById(R.id.edtStudentName);

        EditText edtStudentField =
                dialogView.findViewById(R.id.edtStudentField);


        // ساخت AlertDialog
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);


        // تعیین عنوان Dialog
        builder.setTitle("ثبت دانشجو");


        // قرار دادن فایل XML فرم داخل Dialog
        builder.setView(dialogView);


        /*
            ایجاد دکمه ثبت

            فعلاً مقدار null قرار می‌دهیم
            تا بعد از نمایش Dialog، رویداد کلیک آن را
            خودمان مدیریت کنیم.

            دلیل این کار:
            اگر از setPositiveButton با کد معمولی استفاده کنیم،
            Dialog پس از کلیک بسته می‌شود؛ حتی اگر اطلاعات خالی باشد.
        */

        builder.setPositiveButton("ثبت", null);


        // ایجاد دکمه انصراف
        builder.setNegativeButton("انصراف", (dialog, which) -> {

            // بستن Dialog
            dialog.dismiss();

        });


        // ساخت Dialog از روی تنظیمات Builder
        AlertDialog dialog = builder.create();


        // نمایش Dialog به کاربر
        dialog.show();


        /*
            دریافت دکمه ثبت از Dialog
            و تعریف رویداد کلیک برای آن
        */

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // دریافت نام واردشده توسط کاربر
                        String name = edtStudentName.getText()
                                .toString()
                                .trim();


                        // دریافت رشته تحصیلی واردشده
                        String field = edtStudentField.getText()
                                .toString()
                                .trim();


                        // بررسی خالی نبودن نام دانشجو
                        if (name.isEmpty()) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "لطفاً نام دانشجو را وارد کنید",
                                    Toast.LENGTH_SHORT
                            ).show();

                            // جلوگیری از ادامه اجرای برنامه
                            return;
                        }


                        // بررسی خالی نبودن رشته تحصیلی
                        if (field.isEmpty()) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "لطفاً رشته تحصیلی را وارد کنید",
                                    Toast.LENGTH_SHORT
                            ).show();

                            // جلوگیری از ادامه اجرای برنامه
                            return;
                        }


                        /*
                            اگر اطلاعات خالی نباشند،
                            آن‌ها را در یک رشته ترکیب می‌کنیم.
                        */

                        String studentInfo =
                                "نام دانشجو: " + name +
                                        "\nرشته تحصیلی: " + field;


                        // نمایش اطلاعات دانشجو در صفحه اصلی
                        txtStudentInfo.setText(studentInfo);


                        // نمایش پیام موفقیت
                        Toast.makeText(
                                MainActivity.this,
                                "دانشجو با موفقیت ثبت شد",
                                Toast.LENGTH_SHORT
                        ).show();


                        // بستن Dialog پس از ثبت موفق
                        dialog.dismiss();

                    }
                });

    }

}
