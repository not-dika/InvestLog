/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Portfolio implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<InvestmentEntry> entries = new ArrayList<>();

    public void addEntry(InvestmentEntry entry) {
        entries.add(entry);
    }

    public ArrayList<InvestmentEntry> getEntries() {
        return entries;
    }

    public void clear() {
        entries.clear();
    }

    public double getTotalInvested() {
        return entries.stream()
                .filter(e -> e.action.equals("Invest"))
                .mapToDouble(e -> e.amount * e.price)
                .sum();
    }

    public double getTotalWithdrawn() {
        return entries.stream()
                .filter(e -> e.action.equals("Withdraw"))
                .mapToDouble(e -> e.amount * e.price)
                .sum();
    }

    public double getNetGainLoss() {
        return getTotalWithdrawn() - getTotalInvested();
    }

    public int getOwnedShares(String stockName) {
        int invested = entries.stream()
                .filter(e -> e.stockName.equals(stockName) && e.action.equals("Invest"))
                .mapToInt(e -> e.amount)
                .sum();

        int withdrawn = entries.stream()
                .filter(e -> e.stockName.equals(stockName) && e.action.equals("Withdraw"))
                .mapToInt(e -> e.amount)
                .sum();

        return invested - withdrawn;
    }
}

class InvestmentEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    public LocalDateTime timestamp;
    public String stockName;
    public int amount;
    public double price;
    public String action;

    public InvestmentEntry(String stockName, int amount, double price, String action) {
        this.timestamp = LocalDateTime.now();
        this.stockName = stockName;
        this.amount = amount;
        this.price = price;
        this.action = action;
    }

    public Object[] toRow() {
        return new Object[]{
            timestamp.toString(),
            stockName,
            amount,
            price,
            action
        };
    }
}
