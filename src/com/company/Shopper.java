package com.company;



import com.company.csv.CSV;
import com.company.csv.MyConst;
import com.company.product.Product;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Shopper {

    List<Integer> kinds = new ArrayList<>();

    //покупаем
    public void make_a_purchase() throws IOException {
        int p_amount = products_amount();
        if (p_amount != 0) {
            for (int i = 0; i < p_amount; i++) {
                int _kind = kind();
                int amount = products_amount();
                Product product = CSV.products.get(_kind);

                if (amount != 0 && checkRepeatKinds(_kind) == true) {
                    if (product.compare(amount) == true) {
                        product.buy(amount);
                        //markup
                        if (amount > 2) {                                           // если покупает больше 2х ед. 1 типа товара меняем наценку на последующие ед. товара
                            int standart_markup = Emulate.markup_universal;         // запоминаем наценку
                            Emulate.markup_universal = MyConst.MARKUP_7;             // меняем наценку на временнную
                            double price2 = markup(MyConst.MARKUP_7, product.getPurchasePrice(), (amount-2));    // нацениваем с 7% наценкой
                            Emulate.markup_universal = standart_markup;             // возвращаем cтарую наценку
                            double price1 = markup(Emulate.markup_universal, product.getPurchasePrice(), 2); //Первых 2 товара продаем по обычной наценке
                            double result = result_operation(price1 + price2);                        // добавляем обе операции
                            product.profit(result);
                            product.i(amount);
                            System.out.println("Купил: " + product.getName()+", "+
                                                           product.getAmount().trim()+"л" +"," + " штук: " +
                                                           2 + "," + " с наценкой: " +
                                                           Emulate.markup_universal + "%" + "," + " и " + " штук: " +
                                                           (amount - 2) + "," + " с наценкой: " +
                                                           MyConst.MARKUP_7 + "%" + "," + " общее к-во: " +
                                                           amount + "," + " сума грн: " +
                                                           result_operation(result));
                        } else {
                            double price = markup(Emulate.markup_universal, product.getPurchasePrice(), amount);
                            product.profit(price);
                            product.i(amount);
                            System.out.println("Купил: " + product.getName() +", "+
                                                           product.getAmount().trim()+"л" +","+ " штук: " +
                                                           amount + " с наценкой: " +
                                                           Emulate.markup_universal + "%"  + "," + " сума грн: " + result_operation(price));
                        }
                    }
                    //else if (product.getCount() != 0) {
//                        /*если количество покупаемого товара больше чем есть в наличии -
//                         то просто докупаем тот что остался, что бы не уходить с пустыми руками...*/
//                        int mark = Emulate.markup_universal;                   // запоминаем наценку
//                        Emulate.markup_universal = MyConst.MARKUP_7;             // меняем наценку на временнную
//                        double price2 = (((amount - 2) * MyConst.MARKUP_7));     // нацениваем
//                        Emulate.markup_universal = mark;                        // возвращаем cтарую наценку
//                        double price1 = ((Emulate.markup_universal * product.getPurchasePrice()) * 2);
//                        double result = price1 + price2;
//                        String formattedDouble = new DecimalFormat(MyConst.CONST).format(result);
//                        double double_result = Double.parseDouble(formattedDouble);
//                        System.out.println("Купил: " + product.getName() +", "+ product.getAmount().trim()+"л" +"," + " штук: " + 2 + "," + " с наценкой: " + Emulate.markup_universal + "%" + "," + " и " + " штук: " + (amount - 2) + "," + " с наценкой: " + MyConst.MARKUP_7 + "%" + "," + " общее к-во: " + amount + "," + " сума грн: " + double_result);

                }
            }
        }
    }
    private double result_operation(double result){
        String formattedDouble = new DecimalFormat(MyConst.CONST).format(result);
        double double_result = Double.parseDouble(formattedDouble);
        return double_result;
    }

    // рандомное к-во товаров
    private int products_amount() {
        int amount = 0 + (int) (Math.random() * 10);
        return amount;
    }

    // рандомный товар из БД
    private int kind() {
        int kind = 0 + (int) (Math.random() * 8);
        return kind;
    }

    private double markup(int mark_up, double price, int amount){
        double koeficient = price / 100;
        double eqval = mark_up * koeficient;
        double mark = price + result_operation(eqval);
        double result = mark * amount;
        return result;
    }

    // не покупаем 1 и тот же товар по нескоько раз
    private boolean checkRepeatKinds(int current_kind) {
        int kind = current_kind;
        if (kinds.isEmpty()) {
            kinds.add(kind);
            return true;
        } else if (kinds.contains(kind)) {
            for (int i = 0; i < kinds.size(); i++) {
                if (kinds.get(i).equals(kind)) {
                    return false;
                }
            }
            return false;
        } else {
            kinds.add(kind);
        }
        return true;
    }

}
