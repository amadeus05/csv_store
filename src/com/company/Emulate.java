package com.company;



import com.company.csv.CSV;
import com.company.csv.MyConst;
import com.company.product.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;


public class Emulate {
    public static  int markup_universal = 10;
    private double profit;
    private double waste;
    public void start_emulate_days() throws IOException {
        int days = 30;
        int Saturday = 6;
        int Sunday = 7;



        for (int cur_day = 1; cur_day < days + 1; cur_day++) {

            if (cur_day == Saturday)//check today is saturday
            {
                System.out.println(cur_day + " weekend(Saturday) - markup: 15%");
                markup_universal = MyConst.MARKUP_15;//change markup
                System.out.println("murkup is: " + markup_universal + "%");
                emulate_hours();
                Saturday = cur_day + Sunday;

            } else if (cur_day == Sunday)//check today is sunday
            {
                System.out.println(cur_day + "  weekend(Sunday) - markup: 15%");
                markup_universal = MyConst.MARKUP_15;//change markup
                System.out.println("murkup is: " + markup_universal+ "%");
                emulate_hours();
                Sunday = cur_day + Sunday;
            } else {
                System.out.println(cur_day + " weekday - markup: 10%");
                markup_universal = MyConst.MARKUP_10;//change markup
                System.out.println("murkup is: " + markup_universal+ "%");
                emulate_hours();
            }

            //общее количество проданого товара для каждой категории за месяц
            if (cur_day == days) {
                double sum = 0;
                double sum_2 = 0;
                System.out.println("ЕЖЕМЕСЯЧНЫЕ РЕЗУЛЬТАТЫ ПРОДАЖ:");
                for (int i = 0; i < CSV.products.size(); i++) {
                    Product product = CSV.products.get(i);

                    double waste = product.getSales() * product.getPurchasePrice();
                    String formattedDouble = new DecimalFormat(MyConst.CONST).format(waste);


                    System.out.println("\"" +
                                       product.getName() + "\""+" потрачено на закупку: " +
                                       formattedDouble + " товара продано: " +
                                       product.getSales() + " штук" + " продано на сумму: " +
                                       product.getProfit() + " грн");
                    sum = sum + Double.parseDouble(formattedDouble);// на закупку
                    sum = result_operation(sum);
                    sum_2 = sum_2 + product.getProfit();
                    sum_2 = result_operation(sum_2);
                    this.profit = result_operation(sum_2-sum);
                    this.waste = result_operation(sum);

                    if (i == CSV.products.size()-1){

                        System.out.println("Потрачено на закупку товаров " + sum + " выручка " + sum_2 +  " Грн" + " прибыль " +""+ result_operation(sum_2-sum));
                    }
                    writer();
                }
            }
        }
    }

    public void emulate_hours() throws IOException {
        int day = 24;
        int store_start = 8;
        int store_end = 21;
        for (int time_now = 0; time_now < day; time_now++) {
            if (store_start <= time_now && store_end >= time_now) {
                System.out.println("Время на часах " + time_now + " Магазин работает!");
                if (time_now == 18 || time_now == 19 || time_now == 20 || time_now == 21) {
                    markup_universal = MyConst.MARKUP_8; //change markup on 8%
                    System.out.println("mark up is: " + markup_universal);
                }

                create_shoppers();

                if (time_now == 21) {
                    buyProducts(); // дозакупаем товар
                }
            }
            if (time_now == 22) {
                System.out.println("Store close");
            }
        }
    }


    public void create_shoppers() throws IOException {

        int random_number = 1 + (int) (Math.random() * 10); //запускаем рандомное число покупателей от 1 до 10
        for (int i = 0; i < random_number; i++) {
            Shopper shopper = new Shopper();
            System.out.println("Покупатель " + (i + 1));
            shopper.make_a_purchase();

        }
    }

    private void buyProducts() {
        for (int i = 0; i < CSV.products.size(); i++) {
            Product product = CSV.products.get(i);
            product.check();
        }
    }
    private double result_operation(double result){
        String formattedDouble = new DecimalFormat(MyConst.CONST).format(result);
        double double_result = Double.parseDouble(formattedDouble);
        return double_result;
    }

    private void writer(){
        Writer writer = null;
        try {
            writer = new FileWriter("OTCHET.txt");
            for (int i = 0; i < CSV.products.size(); i++) {
                if (i==0){
                    writer.write("ОТЧЕТ");
                    writer.write(System.getProperty("line.separator"));
                }
                String s = CSV.products.get(i).getName().toString() + " - " +
                           CSV.products.get(i).getSales()+ " штук продано; " +  CSV.products.get(i).getSales()+" штук докуплено;";
                writer.write(s);
                writer.write(System.getProperty("line.separator"));
                if (i == CSV.products.size()-1){
                    String s2 = "Прибыль магазина от продаж: " + this.profit + " грн.; " +
                                "Затраченные средства на дозакупку товара " + this.waste+" грн.; ";
                    writer.write(s2);
                }
            }


            writer.flush();
        } catch (Exception e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}
