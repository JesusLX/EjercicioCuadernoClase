package com.limox.jesus.ejerciciocuadernoclase.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by usuario on 21/02/17.
 */

public class Student implements Serializable, Parcelable{
    //apellidos, nombre, dirección, ciudad, código postal, teléfono y email
    private long id;
    private String name;
    private String surname;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String email;

    public Student(){

    }

    public Student(String name, String surname, String address, String city, String postalCode, String phone, String email) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
    }

    protected Student(Parcel in) {
        id = in.readLong();
        name = in.readString();
        surname = in.readString();
        address = in.readString();
        city = in.readString();
        postalCode = in.readString();
        phone = in.readString();
        email = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(postalCode);
        parcel.writeString(phone);
        parcel.writeString(email);
    }

    @Override
    public String toString() {
        return this.getSurname()+", "+this.getName();
    }
}
