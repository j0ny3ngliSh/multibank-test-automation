package com.multibank.tests;

import com.microsoft.playwright.*;
import com.multibank.utils.ConfigReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class BaseTest {

    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    @BeforeEach
    public void setUp() {
        Playwright playwright = Playwright.create();
        playwrightThreadLocal.set(playwright);

        // 1. Dynamic Configuration from properties file
        String browserName = ConfigReader.get("browser", "chromium");
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("headless", "true"));
        
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(isHeadless);
        Browser browser;

        switch (browserName.toLowerCase()) {
            case "firefox": browser = playwright.firefox().launch(launchOptions); break;
            case "webkit": browser = playwright.webkit().launch(launchOptions); break;
            default: browser = playwright.chromium().launch(launchOptions);
        }
        browserThreadLocal.set(browser);

        // 2. Setup Context with Failure Diagnostics (Tracing)
        BrowserContext context = browser.newContext();
        contextThreadLocal.set(context);

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        // 3. Initialize Page
        Page page = context.newPage();
        pageThreadLocal.set(page);
    }

    @AfterEach
    public void tearDown() {
        // Save Trace file for failure diagnostics
        BrowserContext context = contextThreadLocal.get();
        if (context != null) {
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("target/traces/trace_" + System.currentTimeMillis() + ".zip")));
            context.close();
        }

        if (browserThreadLocal.get() != null) browserThreadLocal.get().close();
        if (playwrightThreadLocal.get() != null) playwrightThreadLocal.get().close();

        // Cleanup ThreadLocals
        pageThreadLocal.remove();
        contextThreadLocal.remove();
        browserThreadLocal.remove();
        playwrightThreadLocal.remove();
    }

    protected Page getPage() { return pageThreadLocal.get(); }
}