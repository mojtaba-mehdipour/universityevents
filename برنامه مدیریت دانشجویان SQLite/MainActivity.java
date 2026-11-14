package com.mehrsamweb.studentdb;

// کتابخانه‌های عمومی اندروید
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.content.DialogInterface;

// کتابخانه‌های مربوط به عناصر گرافیکی
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

// کتابخانه‌های مربوط به دیتابیس
import android.database.Cursor;

// کتابخانه Dialog
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    // متغیر مربوط به دکمه ثبت دانشجو
    Button btnAddStudent;

    // متغیر مربوط به محل نمایش فهرست دانشجویان
    LinearLayout layoutStudentList;

    // شیء مدیریت دیتابیس
    DatabaseHelper databaseHelper;


    // =====================================================
    // متد اصلی اجرای Activity
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // اتصال صفحه اصلی به فایل XML
        setContentView(R.layout.activity_main);


        // شناسایی دکمه ثبت دانشجو
        btnAddStudent = findViewById(R.id.btnAddStudent);

        // شناسایی محل نمایش فهرست دانشجویان
        layoutStudentList = findViewById(R.id.layoutStudentList);


        // ساخت شیء مدیریت دیتابیس
        databaseHelper = new DatabaseHelper(this);


        // نمایش اطلاعات ذخیره‌شده در دیتابیس
        loadStudents();


        // تعریف رویداد کلیک دکمه ثبت دانشجو
        btnAddStudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // نمایش فرم ثبت دانشجو
                showAddStudentDialog();

            }

        });

    }


    // =====================================================
    // نمایش Dialog ثبت دانشجو
    // =====================================================

    private void showAddStudentDialog() {

        // دریافت LayoutInflater
        LayoutInflater inflater = getLayoutInflater();


        // بارگذاری فایل XML فرم
        View dialogView = inflater.inflate(
                R.layout.dialog_add_student,
                null
        );


        // شناسایی EditText نام دانشجو
        EditText edtStudentName =
                dialogView.findViewById(R.id.edtStudentName);


        // شناسایی EditText رشته تحصیلی
        EditText edtStudentField =
                dialogView.findViewById(R.id.edtStudentField);


        // ساخت Dialog
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);


        // تعیین عنوان Dialog
        builder.setTitle("ثبت دانشجو");


        // قرار دادن فرم XML داخل Dialog
        builder.setView(dialogView);


        // ایجاد دکمه ثبت
        builder.setPositiveButton("ثبت", null);


        // ایجاد دکمه انصراف
        builder.setNegativeButton(
                "انصراف",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {

                        // بستن Dialog
                        dialog.dismiss();

                    }

                }
        );


        // ساخت Dialog
        AlertDialog dialog = builder.create();


        // نمایش Dialog
        dialog.show();


        // تعریف رویداد کلیک دکمه ثبت
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // دریافت نام دانشجو
                        String name = edtStudentName.getText()
                                .toString()
                                .trim();


                        // دریافت رشته تحصیلی
                        String field = edtStudentField.getText()
                                .toString()
                                .trim();


                        // بررسی خالی نبودن نام
                        if (name.isEmpty()) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "نام دانشجو را وارد کنید",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }


                        // بررسی خالی نبودن رشته
                        if (field.isEmpty()) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "رشته تحصیلی را وارد کنید",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }


                        /*
                            ارسال اطلاعات به دیتابیس

                            متد insertStudent در کلاس DatabaseHelper
                            اطلاعات را در جدول students ذخیره می‌کند.
                        */

                        long result = databaseHelper.insertStudent(
                                name,
                                field
                        );


                        // بررسی نتیجه عملیات ذخیره‌سازی
                        if (result == -1) {

                            // اگر ثبت ناموفق بود
                            Toast.makeText(
                                    MainActivity.this,
                                    "خطا در ذخیره اطلاعات",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;

                        }


                        // نمایش پیام موفقیت
                        Toast.makeText(
                                MainActivity.this,
                                "دانشجو با موفقیت ثبت شد",
                                Toast.LENGTH_SHORT
                        ).show();


                        // به‌روزرسانی فهرست دانشجویان
                        loadStudents();


                        // بستن Dialog
                        dialog.dismiss();

                    }

                });

    }


    // =====================================================
    // خواندن اطلاعات دانشجویان از دیتابیس
    // =====================================================

    private void loadStudents() {

        // ابتدا فهرست قبلی را پاک می‌کنیم
        layoutStudentList.removeAllViews();


        // دریافت اطلاعات از دیتابیس
        Cursor cursor = databaseHelper.getAllStudents();


        // بررسی وجود اطلاعات در دیتابیس
        if (cursor.getCount() == 0) {

            // ایجاد TextView برای نمایش پیام خالی بودن فهرست
            TextView emptyText = new TextView(this);

            emptyText.setText("هنوز دانشجویی ثبت نشده است.");

            emptyText.setTextSize(16);

            emptyText.setPadding(10, 20, 10, 20);


            // اضافه کردن پیام به صفحه
            layoutStudentList.addView(emptyText);


            // بستن Cursor
            cursor.close();

            return;

        }


        /*
            حرکت روی سطرهای دیتابیس

            moveToNext باعث می‌شود Cursor
            به سطر بعدی منتقل شود.
        */

        try {

            while (cursor.moveToNext()) {

                // دریافت شناسه دانشجو
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );


                // دریافت نام دانشجو
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );


                // دریافت رشته تحصیلی
                String field = cursor.getString(
                        cursor.getColumnIndexOrThrow("field")
                );


                // ساخت یک ردیف برای نمایش دانشجو
                createStudentItem(id, name, field);

            }

        } finally {

            // آزاد کردن Cursor پس از پایان خواندن اطلاعات
            cursor.close();

        }

    }


    // =====================================================
    // ساخت قسمت نمایشی هر دانشجو
    // =====================================================

    private void createStudentItem(
            int id,
            String name,
            String field
    ) {

        // ساخت یک LinearLayout برای اطلاعات دانشجو
        LinearLayout itemLayout = new LinearLayout(this);


        // تعیین جهت عمودی عناصر
        itemLayout.setOrientation(LinearLayout.VERTICAL);


        // تنظیم فاصله داخلی
        itemLayout.setPadding(15, 15, 15, 15);


        // تعیین فاصله بیرونی
        LinearLayout.LayoutParams itemParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        itemParams.setMargins(0, 8, 0, 8);


        // ساخت TextView برای نام دانشجو
        TextView txtName = new TextView(this);

        txtName.setText("نام دانشجو: " + name);

        txtName.setTextSize(17);


        // ساخت TextView برای رشته تحصیلی
        TextView txtField = new TextView(this);

        txtField.setText("رشته تحصیلی: " + field);

        txtField.setTextSize(16);


        // ساخت دکمه حذف
        Button btnDelete = new Button(this);

        btnDelete.setText("حذف دانشجو");


        // اضافه کردن عناصر به ردیف دانشجو
        itemLayout.addView(txtName);

        itemLayout.addView(txtField);

        itemLayout.addView(btnDelete);


        // اضافه کردن ردیف به فهرست اصلی
        layoutStudentList.addView(itemLayout, itemParams);


        // تعریف رویداد کلیک دکمه حذف
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // نمایش پیام تأیید حذف
                showDeleteDialog(id, name);

            }

        });

    }


    // =====================================================
    // نمایش پیام تأیید حذف دانشجو
    // =====================================================

    private void showDeleteDialog(int id, String name) {

        // ساخت Dialog تأیید حذف
        new AlertDialog.Builder(MainActivity.this)

                // تعیین عنوان
                .setTitle("حذف دانشجو")

                // تعیین متن پیام
                .setMessage(
                        "آیا از حذف دانشجوی " + name + " مطمئن هستید؟"
                )

                // دکمه تأیید حذف
                .setPositiveButton(
                        "بله",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which
                            ) {

                                // حذف دانشجو از دیتابیس
                                int result =
                                        databaseHelper.deleteStudent(id);


                                // بررسی نتیجه حذف
                                if (result > 0) {

                                    Toast.makeText(
                                            MainActivity.this,
                                            "دانشجو حذف شد",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                } else {

                                    Toast.makeText(
                                            MainActivity.this,
                                            "حذف دانشجو ناموفق بود",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                }


                                // به‌روزرسانی فهرست
                                loadStudents();

                            }

                        }
                )

                // دکمه انصراف
                .setNegativeButton(
                        "خیر",
                        null
                )

                // نمایش Dialog
                .show();

    }

}
