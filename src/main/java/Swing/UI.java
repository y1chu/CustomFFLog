package Swing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class UI extends JFrame {
    private JTextField logLink;
    private JButton submitButton;
    private JPanel main;
    private JLabel midText;
    private String inputLogLink;

    public UI() {
        setContentPane(main);
        setTitle("FFLog Graph v1.0.0");
        setSize(600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centerWindow();
        setVisible(true);

        submitButton.addActionListener(e -> {
            inputLogLink = logLink.getText();
            if (!checkIfValidLink(inputLogLink)) {
                midText.setText("Link is invalid! Please try again.");
            } else {
                midText.setText("Connection success! Graphing now...");
            }

        });
    }

    private boolean checkIfValidLink(String link) {
        System.setProperty("webdriver.chrome.driver", "ChromeDriver/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().minimize();
        try {
            driver.get(link);
        } catch (Exception e) {
            return false;
        } finally {
            driver.quit();
        }
        return true;
    }

    public static void main(String[] args) {
        UI ui = new UI();
    }

    public void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

}
