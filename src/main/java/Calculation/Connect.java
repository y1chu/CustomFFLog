package Calculation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Connect {

    public static List<Fight> connectAndRead(String log) {
        System.setProperty("webdriver.chrome.driver", "ChromeDriver/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().minimize();

        driver.get(log);

        List<WebElement> getFights = driver.findElements(By.className("report-overview-boss-box"));
        List<Fight> allFights = new ArrayList<>();

        for (WebElement getFight : getFights) {
            String currentFightName;
            // skip trash fights and all encounter element
            if (getFight.getText().contains("Encounters") ||
                    getFight.getText().contains("Trash Fights")) {
                continue;
            }
            if (getFight.getText().contains("Show All")) {
                getFight.findElement(By.className("fight-details-toggle-text")).click();
            }

            List<WebElement> getClearWipeTable = getFight.findElements(By.className("wipes-table"));
            if (getClearWipeTable.size() == 0) {
                // means its a dungeon or 1 pull clear. ignore.
                continue;
            }
            // there will only be a max of 2 tables. if size == 1, check if its only clears or only wipes.
            if (getClearWipeTable.size() == 1 && (getFight.getText().contains("Kill") ||
                    getFight.getText().contains("Kills"))) {
                getClearWipeTable.clear();
                continue;
            }

            // the first table is clear, second is wipe. process clears first.
            // this if is for only wipes
            currentFightName = getFight.findElement(By.className("report-overview-boss-text")).getText();
            if (getClearWipeTable.size() == 1 && (getFight.getText().contains("Wipe") || getFight.getText().contains("Wipes"))) {
                allFights.addAll(processFight(currentFightName, getClearWipeTable.get(0), false));


            } else {
                // clears
                allFights.addAll(processFight(currentFightName, getClearWipeTable.get(0), true));
                // wipes
                allFights.addAll(processFight(currentFightName, getClearWipeTable.get(1), false));

            }

            getClearWipeTable.clear();
        }

        // debug, printing all fights
//        for (Fight allFight : allFights) {
//            System.out.println(allFight.toString());
//        }

        driver.quit();
        return allFights;
    }


    private static ArrayList<Fight> processFight(String fightName, WebElement getClearWipeTable, boolean hasCleared) {
        ArrayList<Fight> toReturn = new ArrayList<>();
        List<WebElement> processBlock = getClearWipeTable.findElements(By.className("wipes-entry"));

        for (WebElement webElement : processBlock) {
            String timeLength = webElement.findElement(By.className("fight-grid-duration")).getText();

            if (hasCleared) {
                Fight currentFight = new Fight(fightName, true, timeLength, 100);
                toReturn.add(currentFight);
            } else {
                WebElement progressElement = webElement.findElement(By.className("wipes-percent-bg"));
                String progressBar = progressElement.findElement(By.className("wipes-percent-fg")).getAttribute("style");
                int progressPercent = Integer.parseInt(progressBar.substring(7, progressBar.indexOf("%")));
                Fight currentFight = new Fight(fightName, false, timeLength, progressPercent);
                toReturn.add(currentFight);
            }

        }

        return toReturn;
    }


}



