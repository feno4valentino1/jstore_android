package com.example.jstore_android_feno;

public class Location
{
    private String province;
    private String city;
    private String description;

    /**
     * Constructor for objects of class Location
     */
    public Location(String province, String city, String description)
    {
        this.province = province;
        this.city = city;
        this.description = description;
    }
    /**
     * Method getProvince
     *
     * @param  -
     * @return province
     */
    public String getProvince()
    {
        return province;
    }
    /**
     * Method getCity
     *
     * @param  -
     * @return city
     */
    public String getCity()
    {
        return city;
    }
    /**
     * Method getDescription
     *
     * @param  -
     * @return description
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * Method setProvince
     *
     * @param  province
     * @return -
     */
    public void setProvince(String province)
    {
        this.province = province;
    }
    /**
     * Method setCity
     *
     * @param  city
     * @return -
     */
    public void setCity(String city)
    {
        this.city = city;
    }
    /**
     * Method setDescription
     *
     * @param  description
     * @return -
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
}