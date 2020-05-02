package com.example.collegeautomationsystem.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

//@Entity
@Embeddable
public class Address {

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;*/

    private char block;

    private int plotNumber;

    private int sectorNumber;

    private String streetName;

    @NotEmpty(message = "Please enter your city")
    private String city;

    @NotEmpty(message = "Please enter your district")
    private String district;

    @NotEmpty(message = "Please enter your state")
    private String state;

    @NotEmpty(message = "Please enter your country")
    private String country;

    @NotEmpty(message = "Please enter your label")
    private String label;

    private int zipCode;
    public Address() {
    }

    public char getBlock() {
        return block;
    }

    public void setBlock(char block) {
        this.block = block;
    }

    public int getPlotNumber() {
        return plotNumber;
    }

    public void setPlotNumber(int plotNumber) {
        this.plotNumber = plotNumber;
    }

    public int getSectorNumber() {
        return sectorNumber;
    }

    public void setSectorNumber(int sectorNumber) {
        this.sectorNumber = sectorNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
              //  "addressId=" + addressId +
                ", block=" + block +
                ", plotNumber=" + plotNumber +
                ", sectorNumber=" + sectorNumber +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", label='" + label + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }

    /*public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }*/

}
