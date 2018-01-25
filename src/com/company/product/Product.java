package com.company.product;



import com.company.csv.MyConst;

import java.text.DecimalFormat;

public class Product {

    private String Name;
    private Double PurchasePrice; //price
    private String Group;
    private String Amount;
    private String Composition;
    private double profit; // выручка
    private int Count;     // количество товара
    private int CountCopy; // количество товара для сравнения
    private int sales = 0;     // количество проданного товара за месяц
    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void i(int amount){
        i = i + amount;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public int getCountCopy() {
        return CountCopy;
    }

    public void setCount2(int count2) {
        CountCopy = count2;
    }

    public String getName() {
        return Name;
    }

    public Double getPurchasePrice() {
        return PurchasePrice;
    }

    public String getGroup() {
        return Group;
    }

    public String getAmount() {
        return Amount;
    }

    public String getComposition() {
        return Composition;
    }

    public int getCount() {
        return Count;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPurchasePrice(Double purchasePrice) {
        PurchasePrice = purchasePrice;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setComposition(String composition) {
        Composition = composition;
    }

    public void setCount(int count) {
        Count = count;
    }

    public int getSales() {
        return sales;
    }

    public void buy(int amount) {
        if (amount > getCount()) {
            System.out.println("not enough");
        } else if (getCount() == 0) {
            System.out.println("Товар " + getName() + " закончился");
        } else {
            sales = sales + amount;       //подсчитываем сколько данного товара было продано вообще
            setCount(getCount() - amount);
        }

    }

    // смотрим сколько товаров нужно докупить
    public void check() {
        int not_enough = getCountCopy() - getCount();
        if (not_enough != 0) {
            double waste = not_enough * getPurchasePrice();//дозакупаем
            String formattedDouble = new DecimalFormat(MyConst.CONST).format(waste);
            System.out.println(getName() + ". докуплено штук: " + not_enough + " " + " потрачено на дозакупку товара - " + formattedDouble);
            setCount(getCount() + not_enough);             //ложим на склад
        } else {
            System.out.println("Дозакупка товара: " + getName() + " - не требуется");
        }
    }
    // сравниваем запрашиваемое количество товара с количеством которое есть на складе
    public boolean compare(int request_amount) {
        boolean check;
        if (getCount() >= request_amount) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public void profit(double sum){
        profit = profit + sum;
        profit = result_operation(profit);
    }



    private double result_operation(double result){
        String formattedDouble = new DecimalFormat(MyConst.CONST).format(result);
        double double_result = Double.parseDouble(formattedDouble);
        return double_result;
    }
}
