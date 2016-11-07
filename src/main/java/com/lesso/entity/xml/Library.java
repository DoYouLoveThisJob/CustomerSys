package com.lesso.entity.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class Library {
    private String name;
    private String address;
    private String tname;
    private String taddress;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTaddress() {
        return taddress;
    }

    public void setTaddress(String taddress) {
        this.taddress = taddress;
    }

    private List<Book> bookList = new ArrayList<Book>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }
}
