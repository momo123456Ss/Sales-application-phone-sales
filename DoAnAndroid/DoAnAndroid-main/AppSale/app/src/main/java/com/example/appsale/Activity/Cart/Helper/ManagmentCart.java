package com.example.appsale.Activity.Cart.Helper;

import android.content.Context;
import android.widget.Toast;
import com.example.appsale.ObjectClass.Product;

import java.util.ArrayList;

public class ManagmentCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    public void insertFood(Product item){
       ArrayList<Product> listPop = getListCart();
       boolean existAlready = false;
       int n =0;
       for(int i =0;i<listPop.size();i++){
           if(listPop.get(i).getName().equals(item.getName())){
               existAlready=true;
               n=i;
               break;
           }
       }
       if(existAlready)
       {
           listPop.get(n).setNumberInCart(item.getNumberInCart());
       }
       else{
           listPop.add(item);
       }
       tinyDB.putListObject("CartList",listPop);
       Toast.makeText(context,"Added to your cart",Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Product> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberItem(ArrayList<Product>listItem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if (listItem.get(position).getNumberInCart() == 1){
            listItem.remove(position);
        }else {
            listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CartList", listItem);
        changeNumberItemsListener.change();
    }

    public void plusNumberItem(ArrayList<Product>listItem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()+1);
        tinyDB.putListObject("CartList", listItem);
        changeNumberItemsListener.change();
    }

    public double getTotalFee(){
        ArrayList<Product> listItem = getListCart();
        double fee = 0;
        for (int i = 0; i <listItem.size();i++){
            fee = fee + (listItem.get(i).getPrice() * listItem.get(i).getNumberInCart());
        }
        return fee;
    }
}
