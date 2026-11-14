package com.mehrsamweb.studentdb;

// کتابخانه‌های موردنیاز برای کار با SQLite
import android.content.Context;
import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    // نام فایل دیتابیس
    private static final String DATABASE_NAME = "StudentDB.db";

    // نسخه دیتابیس
    private static final int DATABASE_VERSION = 1;


    // نام جدول
    private static final String TABLE_STUDENTS = "students";

    // نام ستون‌های جدول
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_FIELD = "field";


    // سازنده کلاس
    public DatabaseHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );

    }


    // =====================================================
    // این متد هنگام ساخته شدن دیتابیس اجرا می‌شود.
    // =====================================================

    @Override
    public void onCreate(SQLiteDatabase db) {

        // دستور SQL برای ساخت جدول دانشجویان
        String createTable = "CREATE TABLE " + TABLE_STUDENTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_FIELD + " TEXT NOT NULL"
                + ")";

        // اجرای دستور ساخت جدول
        db.execSQL(createTable);

    }


    // =====================================================
    // این متد هنگام تغییر نسخه دیتابیس اجرا می‌شود.
    // =====================================================

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        /*
            در این نسخه آموزشی، جدول قبلی حذف و دوباره ساخته می‌شود.

            توجه:
            در پروژه واقعی نباید بدون برنامه‌ریزی
            اطلاعات کاربران را با تغییر نسخه حذف کنیم.
        */

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);

        // ساخت مجدد جدول
        onCreate(db);

    }


    // =====================================================
    // افزودن دانشجو به دیتابیس
    // =====================================================

    public long insertStudent(String name, String field) {

        // دریافت دسترسی نوشتن در دیتابیس
        SQLiteDatabase db = this.getWritableDatabase();


        // ContentValues برای قرار دادن اطلاعات در ستون‌ها
        ContentValues values = new ContentValues();

        // قرار دادن نام دانشجو
        values.put(COLUMN_NAME, name);

        // قرار دادن رشته تحصیلی
        values.put(COLUMN_FIELD, field);


        // اجرای عملیات INSERT
        long result = db.insert(
                TABLE_STUDENTS,
                null,
                values
        );


        // برگرداندن نتیجه عملیات
        // اگر مقدار -1 باشد، ثبت اطلاعات ناموفق بوده است.
        return result;

    }


    // =====================================================
    // دریافت تمام دانشجویان از دیتابیس
    // =====================================================

    public Cursor getAllStudents() {

        // دریافت دسترسی خواندن دیتابیس
        SQLiteDatabase db = this.getReadableDatabase();


        // اجرای دستور SELECT
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_STUDENTS
                        + " ORDER BY " + COLUMN_ID + " DESC",
                null
        );


        // برگرداندن نتیجه جست‌وجو
        return cursor;

    }


    // =====================================================
    // حذف دانشجو بر اساس شناسه
    // =====================================================

    public int deleteStudent(int id) {

        // دریافت دسترسی نوشتن در دیتابیس
        SQLiteDatabase db = this.getWritableDatabase();


        // شرط حذف دانشجو با شناسه مشخص
        String whereClause = COLUMN_ID + " = ?";

        // مقدار شناسه‌ای که باید حذف شود
        String[] whereArgs = {
                String.valueOf(id)
        };


        // اجرای عملیات DELETE
        int result = db.delete(
                TABLE_STUDENTS,
                whereClause,
                whereArgs
        );


        // تعداد ردیف‌های حذف‌شده را برمی‌گرداند
        return result;

    }

}
