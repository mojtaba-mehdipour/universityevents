package com.mehrsamweb.universityevents;

// برای ساخت Intent و رفتن به صفحه جزئیات
import android.content.Intent;

// برای ساخت و نمایش پنجره‌های Dialog
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

// برای مدیریت چرخه حیات Activity
import android.os.Bundle;

// عناصر رابط کاربری
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

// برای نگهداری فهرست رویدادها در حافظه
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
 * صفحه اصلی برنامه
 *
 * در این جلسه مسئولیت‌های MainActivity عبارت‌اند از:
 * 1. نمایش صفحه اصلی
 * 2. باز کردن فرم ثبت رویداد
 * 3. اعتبارسنجی ورودی‌ها
 * 4. نگهداری موقت رویدادها
 * 5. نمایش فهرست رویدادها
 * 6. باز کردن صفحه جزئیات
 * 7. تأیید و انجام حذف
 */
public class MainActivity extends AppCompatActivity {

    // کلیدهای مشترک بین صفحه اصلی و صفحه جزئیات
    public static final String EXTRA_EVENT_TITLE = "event_title";
    public static final String EXTRA_EVENT_LOCATION = "event_location";
    public static final String EXTRA_EVENT_DATE = "event_date";
    public static final String EXTRA_EVENT_CATEGORY = "event_category";
    public static final String EXTRA_EVENT_DESCRIPTION = "event_description";


    /*
     * این ArrayList رویدادها را فعلاً در حافظه نگه می‌دارد.
     *
     * نکته مهم:
     * با بسته شدن کامل برنامه، این اطلاعات از بین می‌روند.
     * در جلسه چهارم آن را با SQLite جایگزین خواهیم کرد.
     */
    private final ArrayList<Event> eventList = new ArrayList<>();


    // این Layout محل قرار گرفتن Viewهای مربوط به رویدادها است.
    private LinearLayout layoutEventList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // اجرای رفتار پایه Activity
        super.onCreate(savedInstanceState);

        // اتصال Activity به طراحی جدید صفحه اصلی
        setContentView(R.layout.activity_main);


        // پیدا کردن دکمه‌های صفحه اصلی
        Button btnShowEvent = findViewById(R.id.btnShowEvent);
        Button btnAddEvent = findViewById(R.id.btnAddEvent);
        Button btnSettings = findViewById(R.id.btnSettings);

        // پیدا کردن محفظه‌ای که رویدادها در آن نمایش داده می‌شوند
        layoutEventList = findViewById(R.id.layoutEventList);


        /*
         * ساخت یک رویداد نمونه برای اینکه صفحه از ابتدا خالی نباشد.
         * این اطلاعات فقط در حافظه قرار می‌گیرند.
         */
        Event sampleEvent = new Event(
                "کارگاه برنامه‌نویسی اندروید",
                "1405/08/25",
                "کارگاه کامپیوتر",
                "آموزشی",
                "آشنایی با ساخت اپلیکیشن‌های اندرویدی با زبان Java"
        );

        // افزودن رویداد نمونه به فهرست
        eventList.add(sampleEvent);

        // نمایش اولیه فهرست رویدادها
        refreshEventList();


        /*
         * کلیک روی دکمه مشاهده رویداد نمونه
         *
         * در این نسخه، اولین رویداد موجود در فهرست باز می‌شود.
         */
        btnShowEvent.setOnClickListener(view -> {

            // اگر فهرست خالی باشد، چیزی برای نمایش نداریم.
            if (eventList.isEmpty()) {

                Toast.makeText(
                        MainActivity.this,
                        "هنوز رویدادی ثبت نشده است.",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // باز کردن جزئیات اولین رویداد
            openEventDetails(eventList.get(0));
        });


        /*
         * کلیک روی دکمه افزودن رویداد
         *
         * در اینجا فرم سفارشی را داخل AlertDialog نمایش می‌دهیم.
         */
        btnAddEvent.setOnClickListener(view -> showAddEventDialog());


        /*
         * تنظیمات در این جلسه هنوز پیاده‌سازی نشده است.
         * فعلاً فقط پیام آموزشی نمایش می‌دهیم.
         */
        btnSettings.setOnClickListener(view -> {

            Toast.makeText(
                    MainActivity.this,
                    "بخش تنظیمات را در جلسه چهارم تکمیل می‌کنیم.",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }


    /*
     * نمایش فرم افزودن رویداد
     *
     * این متد:
     * 1. فایل XML فرم را بارگذاری می‌کند.
     * 2. عناصر فرم را پیدا می‌کند.
     * 3. گزینه‌های Spinner را تنظیم می‌کند.
     * 4. AlertDialog را می‌سازد.
     * 5. پس از تأیید، اطلاعات را اعتبارسنجی و ثبت می‌کند.
     */
    private void showAddEventDialog() {

        // LayoutInflater فایل XML را به View قابل استفاده تبدیل می‌کند.
        LayoutInflater inflater = getLayoutInflater();

        // ساخت View فرم از روی فایل dialog_add_event.xml
        View dialogView = inflater.inflate(
                R.layout.dialog_add_event,
                null
        );


        // پیدا کردن فیلد عنوان
        EditText edtEventTitle =
                dialogView.findViewById(R.id.edtEventTitle);

        // پیدا کردن فیلد تاریخ
        EditText edtEventDate =
                dialogView.findViewById(R.id.edtEventDate);

        // پیدا کردن فیلد محل برگزاری
        EditText edtEventLocation =
                dialogView.findViewById(R.id.edtEventLocation);

        // پیدا کردن Spinner دسته‌بندی
        Spinner spnEventCategory =
                dialogView.findViewById(R.id.spnEventCategory);

        // پیدا کردن فیلد توضیحات
        EditText edtEventDescription =
                dialogView.findViewById(R.id.edtEventDescription);


        /*
         * گزینه‌های دسته‌بندی رویداد
         *
         * این فهرست را بعداً می‌توانیم از منابع XML نیز بخوانیم.
         */
        List<String> categories = Arrays.asList(
                "آموزشی",
                "فرهنگی",
                "ورزشی",
                "مسابقه",
                "سایر"
        );


        /*
         * ArrayAdapter گزینه‌های معمولی Java را به Spinner متصل می‌کند.
         */
        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categories
                );

        // تعیین ظاهر گزینه‌های بازشونده
        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        // اتصال Adapter به Spinner
        spnEventCategory.setAdapter(categoryAdapter);


        /*
         * ساخت Dialog
         *
         * setView باعث می‌شود فرم XML داخل Dialog نمایش داده شود.
         *
         * برای اینکه خطای اعتبارسنجی بتواند فرم را باز نگه دارد،
         * دکمه ثبت را بعد از ساخت Dialog به‌صورت دستی مدیریت می‌کنیم.
         */
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("افزودن رویداد جدید")
                .setView(dialogView)

                // فعلاً عملکرد دکمه ثبت را بعداً اضافه می‌کنیم.
                .setPositiveButton("ثبت رویداد", null)

                // دکمه منفی فقط Dialog را می‌بندد.
                .setNegativeButton("انصراف", (dialogInterface, which) -> {
                    // نیازی به کد اضافی نیست.
                })

                .create();


        // ابتدا Dialog را نمایش می‌دهیم.
        dialog.show();


        /*
         * دکمه ثبت را بعد از show تنظیم می‌کنیم.
         *
         * علت:
         * اگر از ابتدا setPositiveButton را با عملیات ثبت تنظیم کنیم،
         * در حالت عادی AlertDialog پس از کلیک بسته می‌شود.
         *
         * در این روش، اگر ورودی نامعتبر باشد، فرم باز می‌ماند.
         */
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(view -> {

                    // خواندن متن عنوان و حذف فاصله‌های اضافی ابتدا و انتها
                    String title = edtEventTitle
                            .getText()
                            .toString()
                            .trim();

                    // خواندن تاریخ
                    String date = edtEventDate
                            .getText()
                            .toString()
                            .trim();

                    // خواندن محل برگزاری
                    String location = edtEventLocation
                            .getText()
                            .toString()
                            .trim();

                    // خواندن دسته‌بندی انتخاب‌شده
                    String category =
                            spnEventCategory.getSelectedItem().toString();

                    // خواندن توضیحات
                    String description = edtEventDescription
                            .getText()
                            .toString()
                            .trim();


                    /*
                     * اعتبارسنجی:
                     * عنوان، تاریخ و محل باید وارد شده باشند.
                     *
                     * در این جلسه تاریخ را به‌صورت متن دریافت می‌کنیم.
                     * اعتبارسنجی دقیق فرمت تاریخ را در صورت نیاز
                     * می‌توانیم به تمرین تکمیلی تبدیل کنیم.
                     */

                    if (title.isEmpty()) {

                        edtEventTitle.setError(
                                "لطفاً عنوان رویداد را وارد کنید."
                        );

                        edtEventTitle.requestFocus();

                        return;
                    }


                    if (date.isEmpty()) {

                        edtEventDate.setError(
                                "لطفاً تاریخ رویداد را وارد کنید."
                        );

                        edtEventDate.requestFocus();

                        return;
                    }


                    if (location.isEmpty()) {

                        edtEventLocation.setError(
                                "لطفاً محل برگزاری را وارد کنید."
                        );

                        edtEventLocation.requestFocus();

                        return;
                    }


                    /*
                     * ساخت شیء Event با اطلاعات واردشده توسط کاربر
                     */
                    Event newEvent = new Event(
                            title,
                            date,
                            location,
                            category,
                            description
                    );


                    /*
                     * افزودن رویداد به فهرست حافظه
                     *
                     * هنوز هیچ دیتابیسی نداریم.
                     */
                    eventList.add(newEvent);


                    // بازسازی فهرست برای نمایش رویداد جدید
                    refreshEventList();


                    // نمایش پیام موفقیت
                    Toast.makeText(
                            MainActivity.this,
                            "رویداد با موفقیت ثبت شد.",
                            Toast.LENGTH_SHORT
                    ).show();


                    // بستن فرم پس از ثبت موفق
                    dialog.dismiss();
                });
    }


    /*
     * این متد تمام رویدادهای موجود در ArrayList را
     * در صفحه اصلی نمایش می‌دهد.
     *
     * در جلسه سوم این منطق را با RecyclerView جایگزین می‌کنیم.
     */
    private void refreshEventList() {

        // حذف Viewهای قبلی تا موارد تکراری ایجاد نشوند.
        layoutEventList.removeAllViews();


        // اگر فهرست خالی است، پیام مناسب نمایش می‌دهیم.
        if (eventList.isEmpty()) {

            TextView emptyMessage = new TextView(this);

            emptyMessage.setText(
                    "هنوز رویدادی ثبت نشده است."
            );

            emptyMessage.setTextSize(15);

            emptyMessage.setPadding(
                    12,
                    24,
                    12,
                    24
            );

            layoutEventList.addView(emptyMessage);

            return;
        }


        /*
         * پیمایش تمام رویدادها
         *
         * در هر تکرار، متغیر event به یک رویداد اشاره می‌کند.
         */
        for (int i = 0; i < eventList.size(); i++) {

            Event event = eventList.get(i);


            // ساخت یک محفظه برای اطلاعات این رویداد
            LinearLayout itemLayout = new LinearLayout(this);

            itemLayout.setOrientation(LinearLayout.VERTICAL);

            itemLayout.setPadding(
                    16,
                    12,
                    16,
                    12
            );

            // رنگ پس‌زمینه ساده برای هر آیتم
            itemLayout.setBackgroundColor(0xFFFFFFFF);


            /*
             * نمایش عنوان رویداد
             */
            TextView txtTitle = new TextView(this);

            txtTitle.setText(event.getTitle());

            txtTitle.setTextSize(18);

            txtTitle.setTextColor(0xFF17365D);

            txtTitle.setTypeface(
                    null,
                    android.graphics.Typeface.BOLD
            );


            /*
             * نمایش تاریخ، محل و دسته‌بندی
             */
            TextView txtInfo = new TextView(this);

            txtInfo.setText(
                    "تاریخ: " + event.getDate()
                            + "\nمحل: " + event.getLocation()
                            + "\nدسته‌بندی: " + event.getCategory()
            );

            txtInfo.setTextSize(14);

            txtInfo.setTextColor(0xFF444444);


            // اضافه کردن عنوان و اطلاعات به محفظه
            itemLayout.addView(txtTitle);

            itemLayout.addView(txtInfo);


            /*
             * دکمه مشاهده جزئیات
             */
            Button btnDetails = new Button(this);

            btnDetails.setText("مشاهده جزئیات");


            // با کلیک روی دکمه، همین رویداد را باز می‌کنیم.
            btnDetails.setOnClickListener(view -> {
                openEventDetails(event);
            });


            /*
             * دکمه حذف رویداد
             */
            Button btnDelete = new Button(this);

            btnDelete.setText("حذف رویداد");


            /*
             * ابتدا تأیید کاربر را می‌گیریم.
             * اگر تأیید کند، رویداد از ArrayList حذف می‌شود.
             */
            btnDelete.setOnClickListener(view -> {
                showDeleteConfirmation(event);
            });


            // افزودن دکمه‌ها به آیتم
            itemLayout.addView(btnDetails);

            itemLayout.addView(btnDelete);


            /*
             * اضافه کردن آیتم به فهرست اصلی
             */
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

            // فاصله بین آیتم‌های فهرست
            params.setMargins(0, 0, 0, 16);

            layoutEventList.addView(
                    itemLayout,
                    params
            );
        }
    }


    /*
     * باز کردن صفحه جزئیات و انتقال اطلاعات رویداد
     */
    private void openEventDetails(Event event) {

        // ساخت Intent برای صفحه جزئیات
        Intent intent = new Intent(
                MainActivity.this,
                EventDetailActivity.class
        );


        // ارسال اطلاعات رویداد به صفحه مقصد
        intent.putExtra(
                EXTRA_EVENT_TITLE,
                event.getTitle()
        );

        intent.putExtra(
                EXTRA_EVENT_LOCATION,
                event.getLocation()
        );

        intent.putExtra(
                EXTRA_EVENT_DATE,
                event.getDate()
        );

        intent.putExtra(
                EXTRA_EVENT_CATEGORY,
                event.getCategory()
        );

        intent.putExtra(
                EXTRA_EVENT_DESCRIPTION,
                event.getDescription()
        );


        // اجرای Activity جزئیات
        startActivity(intent);
    }


    /*
     * نمایش Dialog تأیید حذف
     */
    private void showDeleteConfirmation(Event event) {

        new AlertDialog.Builder(this)

                // عنوان Dialog
                .setTitle("حذف رویداد")

                // سؤال از کاربر
                .setMessage(
                        "آیا از حذف رویداد «"
                                + event.getTitle()
                                + "» مطمئن هستید؟"
                )

                // اگر کاربر حذف را تأیید کند
                .setPositiveButton("حذف", (dialog, which) -> {

                    // حذف رویداد از فهرست حافظه
                    eventList.remove(event);

                    // بازسازی فهرست پس از حذف
                    refreshEventList();

                    // نمایش پیام موفقیت
                    Toast.makeText(
                            MainActivity.this,
                            "رویداد حذف شد.",
                            Toast.LENGTH_SHORT
                    ).show();
                })

                // اگر کاربر انصراف دهد، چیزی حذف نمی‌شود.
                .setNegativeButton("انصراف", (dialog, which) -> {
                    dialog.dismiss();
                })

                // نمایش Dialog
                .show();
    }
}
