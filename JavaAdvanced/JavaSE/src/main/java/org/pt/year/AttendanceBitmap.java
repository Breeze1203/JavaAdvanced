package org.pt.year;

/**
 * @ClassName AttendanceBitmap
 * @Author pt
 * @Description
 * @Date 2025/6/26 21:16
 **/
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * 使用位图（Bitmap）来管理用户年度考勤记录的类。
 * 这个类的每个实例代表一个用户一年的考勤数据。
 */
public class AttendanceBitmap {

    // 一年最多366天（闰年），需要46个字节来存储 (366 / 8 = 45.75 -> 46 bytes)
    private static final int BITMAP_SIZE_IN_BYTES = 46;

    private final int year;
    private final byte[] absenceMap;

    /**
     * 构造函数，为指定的年份创建一个新的、全勤的考勤记录。
     * @param year 考勤年份
     */
    public AttendanceBitmap(int year) {
        this.year = year;
        // 初始化一个46字节的数组，所有字节默认为0，代表没有任何缺勤记录
        this.absenceMap = new byte[BITMAP_SIZE_IN_BYTES];
    }

    /**
     * 记录某一天为缺勤。
     * @param date 缺勤的日期
     */
    public void setAbsence(LocalDate date) {
        validateDate(date);

        // 1. 计算出这是当年的第几天 (1-366)
        int dayOfYear = date.getDayOfYear();

        // 2. 计算出对应的 bit 索引 (0-365)
        int bitIndex = dayOfYear - 1;

        // 3. 计算出这个 bit 在哪个字节 (byte) 里
        int byteIndex = bitIndex / 8;

        // 4. 计算出在这个字节里的第几位
        int bitInByte = bitIndex % 8;

        // 5. 生成一个操作数（mask），只有目标位是1
        //    例如，要设置一个字节的第3位(bitInByte=2)，mask就是 1 << 2，即 00000100
        byte mask = (byte) (1 << bitInByte);

        // 6. 使用“按位或”运算，将该位置为1，同时不影响其他位
        absenceMap[byteIndex] |= mask;
    }

    /**
     * 检查某一天是否缺勤。
     * @param date 要检查的日期
     * @return 如果缺勤则返回 true，否则返回 false
     */
    public boolean isAbsent(LocalDate date) {
        validateDate(date);

        int dayOfYear = date.getDayOfYear();
        int bitIndex = dayOfYear - 1;
        int byteIndex = bitIndex / 8;
        int bitInByte = bitIndex % 8;
        byte mask = (byte) (1 << bitInByte);

        // 使用“按位与”运算，检查目标位是否为1
        // 如果结果不为0，说明目标位是1，即当天缺勤
        return (absenceMap[byteIndex] & mask) != 0;
    }

    /**
     * 获取全年的总缺勤天数。
     * @return 缺勤天数
     */
    public int getAbsenceCount() {
        int count = 0;
        for (byte b : absenceMap) {
            // Integer.bitCount() 是一个非常高效的内置方法，用于计算一个整数的二进制表示中有多少个1
            // 我们需要将 byte 转为 int 来使用它，& 0xFF 是为了避免负数byte的符号扩展
            count += Integer.bitCount(b & 0xFF);
        }
        return count;
    }

    /**
     * 校验日期是否属于当前记录的年份。
     * @param date 要校验的日期
     */
    private void validateDate(LocalDate date) {
        if (date.getYear() != this.year) {
            throw new IllegalArgumentException("日期 " + date + " 不属于本考勤记录的年份 " + this.year);
        }
    }

    /**
     * 将位图数据以十六进制字符串格式返回，方便调试和查看。
     * @return 十六进制表示的位图字符串
     */
    public String toHexString() {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte b : absenceMap) {
            // 格式化为两位大写的十六进制数，例如 0A, 14
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // --- main方法，用于演示 ---
    public static void main(String[] args) {
        System.out.println("为Alice创建2025年的考勤记录...");
        int year = 2025;
        AttendanceBitmap alicesAttendance = new AttendanceBitmap(year);
        System.out.println("初始状态 (全勤): " + alicesAttendance.toHexString().substring(0, 10) + "..."); // 只显示前几个字节
        System.out.println("----------------------------------------");

        // 记录第一次缺勤
        LocalDate absenceDate1 = LocalDate.of(year, 1, 3);
        System.out.println("记录缺勤: " + absenceDate1);
        alicesAttendance.setAbsence(absenceDate1);
        System.out.println("更新后状态: " + alicesAttendance.toHexString().substring(0, 10) + "...");
        System.out.println("----------------------------------------");

        // 记录第二次缺勤
        LocalDate absenceDate2 = LocalDate.of(year, 1, 5);
        System.out.println("记录缺勤: " + absenceDate2);
        alicesAttendance.setAbsence(absenceDate2);
        System.out.println("更新后状态: " + alicesAttendance.toHexString().substring(0, 10) + "...");
        System.out.println("----------------------------------------");

        // 记录第三次缺勤
        LocalDate absenceDate3 = LocalDate.of(year, 2, 1);
        System.out.println("记录缺勤: " + absenceDate3);
        alicesAttendance.setAbsence(absenceDate3);
        // 这次我们打印更长的字符串，因为2月1日会影响到第4个字节
        System.out.println("更新后状态: " + alicesAttendance.toHexString().substring(0, 18) + "...");
        System.out.println("----------------------------------------");

        // 查询考勤状态
        System.out.println("开始查询考勤状态...");
        System.out.println("查询 " + absenceDate1 + " 是否缺勤? " + alicesAttendance.isAbsent(absenceDate1));
        System.out.println("查询 2025-01-04 是否缺勤? " + alicesAttendance.isAbsent(LocalDate.of(year, 1, 4)));
        System.out.println("查询 " + absenceDate3 + " 是否缺勤? " + alicesAttendance.isAbsent(absenceDate3));
        System.out.println("----------------------------------------");

        // 统计总缺勤天数
        System.out.println("统计总缺勤天数...");
        System.out.println("Alice在 " + year + " 年的总缺勤天数是: " + alicesAttendance.getAbsenceCount() + " 天。");
    }
}
