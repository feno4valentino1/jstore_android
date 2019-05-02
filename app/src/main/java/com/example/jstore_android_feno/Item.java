package com.example.jstore_android_feno;

public class Item
{
    private int id;
    private String name;
    private int price;
    private String category;
    private String status;
    private Supplier supplier;

    /**
     * Constructor for objects of class Item
     */
    public Item(int id, String name, int price, String category, String status, Supplier supplier)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.status = status;
        this.supplier = supplier;
    }
    /**
     * Method getId
     *
     * @param  -
     * @return id
     */
    public int getId()
    {
        return id;
    }
    /**
     * Method getName
     *
     * @param  -
     * @return name
     */
    public String getName()
    {
        return name;
    }
    /**
     * Method getPrice
     *
     * @param  -
     * @return price
     */
    public int getPrice()
    {
        return price;
    }
    /**
     * Method getCategory
     *
     * @param  -
     * @return category
     */
    public String getCategory()
    {
        return category;
    }
    /**
     * Method getStatus
     *
     * @param  -
     * @return status
     */
    public String getStatus()
    {
        return status;
    }
    /**
     * Method getSupplier
     *
     * @param  -
     * @return supplier
     */
    public Supplier getSupplier()
    {
        return supplier;
    }
    /**
     * Method setId
     *
     * @param  id
     * @return -
     */
    public void setId(int id)
    {
        this.id = id;
    }
    /**
     * Method setName
     *
     * @param  name
     * @return -
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * Method setPrice
     *
     * @param  price
     * @return -
     */
    public void setPrice(int price)
    {
        this.price = price;
    }
    /**
     * Method setCategory
     *
     * @param  category
     * @return -
     */
    public void setCategory(String category)
    {
        this.category = category;
    }
    /**
     * Method setStatus
     *
     * @param  status
     * @return -
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
    /**
     * Method setSupplier
     *
     * @param  supplier
     * @return -
     */
    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }
}
