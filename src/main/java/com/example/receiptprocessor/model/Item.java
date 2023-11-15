package com.example.receiptprocessor.model;

import java.util.Objects;

public class Item {
    private String shortDescription;
    private String price;

    //Constructor
    public Item(String shortDescription, String price){
        this.shortDescription = shortDescription;
        this.price = price;
    }

    //Getters
    public String getShortDescription(){
        return shortDescription;
    }

    public String getPrice(){
        return price;
    }

    //Setters
    public void setShortDescription(String shortDescription){
        this.shortDescription = shortDescription;
    }

    public void setPrice(String price){
        this.price = price;
    }


    //Other methods that will be used
    @Override
    public String toString(){
        return "Item{"+
                "shortDescription= '"+ shortDescription + '\''+
                ", price= '"+price+'\''+"}";
    }

    public boolean equals(Object o){
        if (o==this) return true;
        if (o.getClass() != getClass() || o == null) return false;
        Item item = (Item) o;
        return Objects.equals(shortDescription , item.shortDescription) &&
                Objects.equals(price , item.price);
    }

    public int hashCode(){
        return Objects.hash(shortDescription, price);
    }
}

/*
 Using Lombok we can reduce the above code to:
 package com.yourcompany.receiptprocessor.model;

         import lombok.AllArgsConstructor;
         import lombok.Data;
         import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String shortDescription;
    private String price;
}
*/
