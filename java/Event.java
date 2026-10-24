package com.mehrsamweb.universityevents;

/*
 * کلاس Event مدل اطلاعات یک رویداد دانشگاهی است.
 *
 * هر شیء از این کلاس، نماینده یک رویداد مستقل خواهد بود.
 *
 * مثال:
 * یک شیء می‌تواند مربوط به کارگاه برنامه‌نویسی باشد
 * و شیء دیگر مربوط به مسابقه طراحی وب.
 */
public class Event {

    // عنوان رویداد؛ مانند «کارگاه برنامه‌نویسی»
    private String title;

    // تاریخ برگزاری رویداد
    private String date;

    // محل برگزاری رویداد
    private String location;

    // دسته‌بندی رویداد؛ مانند آموزشی یا فرهنگی
    private String category;

    // توضیحات تکمیلی رویداد
    private String description;


    /*
     * سازنده کلاس:
     * زمانی اجرا می‌شود که با new یک Event جدید بسازیم.
     *
     * پارامترهای سازنده، اطلاعات اولیه رویداد هستند.
     */
    public Event(
            String title,
            String date,
            String location,
            String category,
            String description
    ) {

        // ذخیره عنوان دریافتی در ویژگی title
        this.title = title;

        // ذخیره تاریخ دریافتی
        this.date = date;

        // ذخیره محل برگزاری
        this.location = location;

        // ذخیره دسته‌بندی
        this.category = category;

        // ذخیره توضیحات
        this.description = description;
    }


    /*
     * متدهای Getter برای خواندن اطلاعات استفاده می‌شوند.
     * در این جلسه به کمک این متدها اطلاعات را نمایش می‌دهیم.
     */

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }


    /*
     * متدهای Setter برای تغییر اطلاعات استفاده می‌شوند.
     * در جلسات بعدی برای ویرایش رویداد نیز به آن‌ها نیاز داریم.
     */

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
