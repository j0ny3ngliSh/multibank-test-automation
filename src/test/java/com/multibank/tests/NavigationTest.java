package com.multibank.tests;

import com.multibank.pages.TradingPage;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationTest extends BaseTest {

    @Test
    public void testTopNavigationMenu() {
        // 1. Arrange: Define the expected navigation links
        List<String> expectedLinks = Arrays.asList("Home", "Markets", "Trade");
        
        // 2. Act: Open the page and initialize the Page Object
        // Note: Navigate to the landing page explicitly since we removed forced navigation
        getPage().navigate("https://trade.multibank.io/"); 
        TradingPage tradingPage = new TradingPage(getPage());

        // 3. Assert: Verify the menu items exist
        boolean isNavVisible = tradingPage.verifyNavigationMenu(expectedLinks);
        
        assertTrue(isNavVisible, "The top navigation menu should contain: " + expectedLinks);
    }
}