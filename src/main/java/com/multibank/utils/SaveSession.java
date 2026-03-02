package com.multibank.utils;

import com.microsoft.playwright.*;
import java.nio.file.Paths;

public class SaveSession {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            // Launch a standard browser
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            
            // Create a new context
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            
            // Navigate to the site
            page.navigate("https://trade.multibank.io/login");
            
            System.out.println(">>> LOGIN NOW in the browser that just opened.");
            System.out.println(">>> Once logged in and on the dashboard, PRESS ENTER here...");
            System.in.read();
            
            // Snapshot the state
            context.storageState(new BrowserContext.StorageStateOptions()
                    .setPath(Paths.get("src/test/resources/auth.json")));
            
            System.out.println("SUCCESS: auth.json created. You are now set for life.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}