package by.intexsoft.model;

import by.intexsoft.model.base.BaseEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Description database table "store"
 */
@Entity
@Table
public class Store extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @Column
    private String details;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String skype;
    @Column
    private Boolean discounts;
    @Column
    private String mail;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Stock.class)
    @JoinTable(
            name = "stock_store",
            joinColumns = {@JoinColumn(name = "store_id")},
            inverseJoinColumns = {@JoinColumn(name = "stock_id")}
    )
    private List<Stock> stockList;

    /**
     * Store name
     */
    public String getName() {
        return name;
    }
    /**
     * Store details
     */
    public String getDetails() {
        return details;
    }
    /**
     * Store address
     */
    public String getAddress() {
        return address;
    }
    /**
     * Store contact phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Store contact skype
     */
    public String getSkype() {
        return skype;
    }
    /**
     * Store discounts system (consist or not)
     */
    public Boolean getDiscounts() {
        return discounts;
    }
    /**
     * Store contant mail
     */
    public String getMail() {
        return mail;
    }
    /**
     * ManyToMany relation to {@link Stock} entities
     */
    public List<Stock> getStockList() {
        return stockList;
    }
}
