package com.danieltalik.fullStackApp.DAL;

import com.danieltalik.fullStackApp.config.H2Config;
import com.danieltalik.fullStackApp.Models.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BirthdayDAL implements IDao {
    private H2Config config;
    private String addPersonBirthday = "INSERT INTO BIRTHDAY(last_name,age,birthday) VALUES(?, ?, ?);";
    public BirthdayDAL(H2Config config){
        this.config = config;
    }

    public List<Person> getAll(){
        List<Person> birthdayList = new ArrayList<>();
        Person person = new Person();

        try {
            config.connection();
            Statement st = config.connection().createStatement();
            ResultSet rs = st.executeQuery("SELECT n.first_name, n.last_name, b.birthday, b.age, n.nickname\n" +
                    "FROM birthday b\n" +
                    "JOIN nickname n ON b.id = n.id;");
            while(rs.next()){
                person = mapRowToPerson(rs);
                birthdayList.add(person);
            }
        }catch (SQLException ex){
            Logger lg = Logger.getLogger(BirthdayDAL.class.getName());
            lg.log(Level.SEVERE, ex.getMessage(),ex);
        }

        return birthdayList;
    }

    public boolean insertPerson(String first, String last, String nickname, int age, LocalDate birthday){
        boolean addedPerson = false;
        Date dbDate = Date.valueOf(birthday);

        try {
            config.connection();
            PreparedStatement ps = config.connection().prepareStatement(addPersonBirthday);
            ps.setString(1,last);
            ps.setInt(2,age);
            ps.setDate(3,dbDate);
            ps.executeUpdate();
            addedPerson = true;

        }catch (SQLException e){
            e.getMessage();
        }
        return addedPerson;
    }
    public Person getName(String input){
        Person person = new Person();

        try{
            config.connection();
            PreparedStatement ps = config.connection().prepareStatement("SELECT n.first_name, n.last_name, b.birthday, b.age, n.nickname\n" +
                    "FROM birthday b\n" +
                    "JOIN nickname n ON n.id = b.id\n" +
                    "WHERE b.birthday = ?;");
            ps.setDate(1, Date.valueOf(input));
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                person = mapRowToPerson(rs);
            }
        }
        catch (SQLException ex){
            Logger lg = Logger.getLogger(NicknameDAL.class.getName());
            lg.log(Level.SEVERE, ex.getMessage(),ex);
        }
        return person;
    }

    public Person getDetails(String first, String last){
        Person person = new Person();

        try{
            config.connection();
            PreparedStatement ps = config.connection().prepareStatement("SELECT n.first_name, n.last_name, b.birthday, b.age, n.nickname\n" +
                    "FROM birthday b\n" +
                    "JOIN nickname n ON n.id = b.id\n" +
                    "WHERE n.first_name = ? AND n.last_name = ?;");
            ps.setString(1, first);
            ps.setString(2, last);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                person = mapRowToPerson(rs);
            }
        }
        catch (SQLException ex){
            Logger lg = Logger.getLogger(NicknameDAL.class.getName());
            lg.log(Level.SEVERE, ex.getMessage(),ex);
        }
        return person;
    }

    private Person mapRowToPerson(ResultSet rs){
        Person person = new Person();
        try{
            person.setFirstName(rs.getString("first_name"));
            person.setLastName(rs.getString("last_name"));
            person.setAge(rs.getInt("age"));
            person.setBirthday(rs.getDate("birthday").toLocalDate());
            person.setNickName(rs.getString("nickname"));

        }
        catch (Exception ex){
            ex.getMessage();
        }
        return person;
    }


}
