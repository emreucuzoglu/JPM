package com.jpm.tech.util;

import com.jpm.tech.entity.DailyRanking;
import com.jpm.tech.entity.DailyReport;

import java.util.Collection;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.jpm.tech.Application.LOG_LEVEL;
import static com.jpm.tech.util.Util.format;

public final class OutputUtil {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    static {
        Handler consoleHandler = new ConsoleHandler();
        LOGGER.addHandler(consoleHandler);
        consoleHandler.setLevel(LOG_LEVEL);
        LOGGER.setLevel(LOG_LEVEL);
    }

    private OutputUtil() {
    }

    public static void printDaily(List<DailyReport> dailyData) {
        System.out.println("Amount in USD settled everyday");
        System.out.printf("%11s - %10s %10s%n", "Date", "Buy", "Sell");
        dailyData.stream()
                .forEachOrdered(data ->
                        System.out.printf("%11s - %10.2f %10.2f%n", format(data.getSettlementDate()), data.getTotalBuy(), data.getTotalSell())
                );
    }

    public static void printRanking(String header, List<DailyRanking> dailyData) {
        System.out.println(header);
        System.out.printf("%11s - %14s %5s %10s %n", "Date", "Name", "Rank", "Amount");
        dailyData.stream()
                .forEachOrdered(data ->
                        System.out.printf("%11s - %14s %5s %10.2f%n", format(data.getSettlementDate()), data.getEntityName(), data.getRank(), data.getAmount()));
    }

    public static void logData(String header, Level headerLevel, Level itemLevel, Collection<?> items) {
        LOGGER.log(headerLevel, header);
        items.stream().sequential().forEach(item -> LOGGER.log(itemLevel, item.toString()));
    }
}
