import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {

        System.setProperty("webdriver.chrome.driver", "ChromeDriver/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.manage().window().minimize();

        // https://www.fflogs.com/reports/HLJTkcP7RY3znXNF
        // https://www.fflogs.com/reports/mf84twZHjxhvzb7q
        driver.get("https://www.fflogs.com/reports/mf84twZHjxhvzb7q");

        List<WebElement> getFights = driver.findElements(By.className("report-overview-boss-box"));
        List<Fight> allFights = new ArrayList<>();

        for (int i = 0; i < getFights.size(); i++) {
            String currentFightName;
            int currentDamage = 0;

            // skip trash fights and all encounter element
            if (getFights.get(i).getText().contains("Encounters") ||
                    getFights.get(i).getText().contains("Trash Fights")) {
                continue;
            }
            if (getFights.get(i).getText().contains("Show All")) {
                getFights.get(i).findElement(By.className("fight-details-toggle-text")).click();
            }

            // driver.findElements(By.className("wipes-table"));
            List<WebElement> getClearWipeTable = getFights.get(i).findElements(By.className("wipes-table"));

            // getClearWipeTable = getFights.get(i).findElements(By.className("wipes-table"));
            System.out.println(getClearWipeTable.size());
            if (getClearWipeTable.size() == 0) {
                // means its a dungeon or 1 pull clear. ignore.
                continue;
            }
            // there will only be a max of 2 tables. if size == 1, check if its only clears or only wipes.
            if (getClearWipeTable.size() == 1 && (getFights.get(i).getText().contains("Wipe") || getFights.get(i).getText().contains("Wipes"))) {
                System.out.println("this is a all Wipe run");
            }



            currentFightName = getFights.get(i).findElement(By.className("report-overview-boss-text")).getText();
            // //*[@id="report-overview-fights-contents"]/div[3]/a


            // System.out.println(currentURL);
            // format: All Encounters (1 Kill, 5 Wipes)
            // WebDriver driver, WebElement element, int delay
            // waitUntilElementVisible(driver, getFights.get(i).findElement(By.xpath("//div[@class='all-fights-entry wipe']")), 30);
            // String temp = getFights.get(i).findElement(By.xpath("//div[@class='all-fights-entry wipe']")).getText();
            // System.out.println("Loop " + i + " " + getFights.get(i).getText() + "\n");
            // System.out.println("the clear/wipe is " + temp);

            Fight currentFight = new Fight(currentFightName, currentDamage);
            allFights.add(currentFight);
            getClearWipeTable.clear();
        }

        // debug, printing all fights
        for (Fight allFight : allFights) {
            System.out.println(allFight.toString());
        }

        driver.quit();


    }

    public static WebElement waitUntilElementVisible(WebDriver driver, WebElement element, int delay) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, delay);
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Web element not visible within given time" + element + " Time " + delay);
        }
    }


}



