package com.multibank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.List;

/**
 * Page Object for the Trading Dashboard.
 * This class handles navigation and interaction with the main trading grid.
 */
public class TradingPage {
    private final Page page;
    private final Locator navMenu;
    private final Locator tradingPairsTable;

    public TradingPage(Page page) {
        this.page = page;
        // Locators based on standard web component practices
        this.navMenu = page.locator("nav >> ul"); 
        this.tradingPairsTable = page.locator("table.trading-pairs-table");
    }

    /**
     * Verifies that the navigation menu contains expected links.
     * @param expectedLinks List of menu items to check.
     */
    public boolean verifyNavigationMenu(List<String> expectedLinks) {
        List<String> actualLinks = navMenu.locator("li").allTextContents();
        return actualLinks.containsAll(expectedLinks);
    }

    /**
     * Retrieves all visible trading pairs from the table.
     */
    public List<String> getTradingPairs() {
        // Targets the row cells, typically found in a table body
        return tradingPairsTable.locator("tbody tr td.pair-name").allTextContents();
    }
}