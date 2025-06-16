package org.pt;

/**
 * @ClassName TickCalculator
 * @Author pt
 * @Description
 * @Date 2025/6/6 11:02
 **/
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TickCalculator {

    public static int getTickBetweenPrice(String priceTickSizeRules, BigDecimal first, BigDecimal second) {
        // 提取price和tick
        String[] parts = priceTickSizeRules.split(",");
        int n = Integer.parseInt(parts[0]);
        BigDecimal defaultPriceTickSize = new BigDecimal(parts[1]);
        List<BigDecimal> prices = new ArrayList<>();
        List<BigDecimal> tickSizes = new ArrayList<>();
        tickSizes.add(defaultPriceTickSize);
        for (int i = 0; i < n; i++) {
            prices.add(new BigDecimal(parts[2 + 2 * i]));
            tickSizes.add(new BigDecimal(parts[3 + 2 * i]));
        }
        System.out.println("prices:"+prices);
        System.out.println("tickSizes:"+tickSizes);
        // 确定计算顺序
        BigDecimal startPrice = first.min(second);
        BigDecimal endPrice = first.max(second);
        if (startPrice.equals(endPrice)) {
            return 0;
        }

        BigDecimal totalTicks = BigDecimal.ZERO;
        BigDecimal currentPrice = startPrice;

        //分段计算 Tick 数
        //处理小于 price1 的部分
        BigDecimal firstBoundary = (n > 0) ? prices.get(0) : endPrice.add(BigDecimal.ONE); // 如果没有分界点，则此段一直延伸
        if (currentPrice.compareTo(firstBoundary) < 0) {
            BigDecimal effectiveEnd = endPrice.min(firstBoundary);
            BigDecimal range = effectiveEnd.subtract(currentPrice);
            if (range.compareTo(BigDecimal.ZERO) > 0) {
                totalTicks = totalTicks.add(range.divide(defaultPriceTickSize, 10, RoundingMode.HALF_UP));
            }
            currentPrice = effectiveEnd;
        }

        if (currentPrice.compareTo(endPrice) >= 0) {
            return totalTicks.intValue();
        }

        // 处理 price1 到 priceN 的中间部分
        for (int i = 0; i < n - 1; i++) {
            BigDecimal lowerBound = prices.get(i);
            BigDecimal upperBound = prices.get(i + 1);
            BigDecimal currentTickSize = tickSizes.get(i + 1);

            if (currentPrice.compareTo(upperBound) < 0) {
                BigDecimal effectiveEnd = endPrice.min(upperBound);
                BigDecimal range = effectiveEnd.subtract(currentPrice);
                if (range.compareTo(BigDecimal.ZERO) > 0) {
                    totalTicks = totalTicks.add(range.divide(currentTickSize, 10, RoundingMode.HALF_UP));
                }
                currentPrice = effectiveEnd;
            }

            if (currentPrice.compareTo(endPrice) >= 0) {
                return totalTicks.intValue();
            }
        }

        if (n > 0) {
            BigDecimal lastBoundary = prices.get(n - 1);
            BigDecimal lastTickSize = tickSizes.get(n);
            if (currentPrice.compareTo(endPrice) < 0) {
                BigDecimal range = endPrice.subtract(currentPrice);
                totalTicks = totalTicks.add(range.divide(lastTickSize, 10, RoundingMode.HALF_UP));
            }
        }
        return totalTicks.setScale(0, RoundingMode.HALF_UP).intValue();
    }


    /**
     * 测试
     */
    public static void main(String[] args) {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        String rules1 = "1,0.25,5,0.5,7,1";
        BigDecimal first1 = new BigDecimal("3.75");
        BigDecimal second1 = new BigDecimal("9");
        int result1 = getTickBetweenPrice(rules1, first1, second1);
        System.out.println("输入 getTickBetweenPrice(\"1,0.25,5,0.5,7,1\", new BigDecimal(\"3.75\"), new BigDecimal(\"9\")), 返回 " + result1);

        String rules2 = "1,0.25,5,0.5";
        BigDecimal first2 = new BigDecimal("4.75");
        BigDecimal second2 = new BigDecimal("6");
        int result2 = getTickBetweenPrice(rules2, first2, second2);
        System.out.println("输入 getTickBetweenPrice(\"1,0.25,5,0.5\", new BigDecimal(\"4.75\"), new BigDecimal(\"6\")), 返回 " + result2);
    }
}