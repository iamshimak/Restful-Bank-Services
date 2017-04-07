package model;

import java.lang.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ShimaK on 08-Apr-17.
 */
public class Customer {
    private String name;
    private Date dob;
    private String address;
    private String mobile;
    private String email;

    private String accountType;
    private String accountNum;
    private String sortCode;
    private int balance;
    private ArrayList<Card> cards;

    public Customer(String name, Date dob, String address, String mobile, String email) {
        this(name, dob, address, mobile, email, null, null, null, 0, null);
    }

    public Customer(String name, Date dob, String address, String mobile, String email, String accountType, String accountNum, String sortCode, int balance, ArrayList<Card> cards) {
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.accountType = accountType;
        this.accountNum = accountNum;
        this.sortCode = sortCode;
        this.balance = balance;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountNum='" + accountNum + '\'' +
                ", sortCode='" + sortCode + '\'' +
                ", balance=" + balance +
                ", cards=" + cards.toString() +
                '}';
    }
}
