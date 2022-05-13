package com.danieltalik.fullStackApp.DAL;

import com.danieltalik.fullStackApp.config.H2Config;
import com.danieltalik.fullStackApp.Models.Person;
import org.springframework.beans.factory.annotation.Value;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NicknameDAL implements IDao {

    private H2Config config;
    public NicknameDAL(H2Config config){
        this.config = config;
    }
    private String addPersonNickname = "INSERT INTO NICKNAME(first_name,last_name,nickname) VALUES(?, ?, ?);";

    public List<Person> getAll(){

        List<Person> nicknames = new ArrayList<>();
        Person nickname = new Person();

        try{
            config.connection();
            Statement st = config.connection().createStatement();
            ResultSet rs = st.executeQuery("SELECT  n.first_name, n.nickname, n.last_name,b.birthday,b.age\n" +
                    "FROM nickname n\n" +
                    "JOIN birthday b ON n.id = b.id;");
            while (rs.next()){
                nickname = mapRowToPerson(rs);
                nicknames.add(nickname);
            }
        } catch (SQLException ex){
            Logger lg = Logger.getLogger(NicknameDAL.class.getName());
            lg.log(Level.SEVERE, ex.getMessage(),ex);
        }

        return nicknames;

    }
    public boolean insertPerson(String first, String last, String nickname, int age, LocalDate birthday){

        boolean addedPerson = false;

        try {
            config.connection();
            PreparedStatement ps = config.connection().prepareStatement(addPersonNickname);
            ps.setString(1,first);
            ps.setString(2,last);
            ps.setString(3,nickname);
            ps.executeUpdate();
            addedPerson = true;

        }catch (SQLException e){
            e.getMessage();
        }
        return addedPerson;
    }

    public Person getDetails(String first, String last){

        Person nickname = new Person();

        try{
            config.connection();
            PreparedStatement ps = config.connection().prepareStatement("SELECT n.first_name, n.nickname, n.last_name, b.birthday, b.age \n" +
                    "FROM nickname n\n" +
                    "JOIN birthday b ON n.id = b.id\n" +
                    "WHERE n.first_name = ? AND n.last_name = ? ;");
            ps.setString(1,first);
            ps.setString(2,last);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                nickname = mapRowToPerson(rs);
            }
        } catch (SQLException ex){
            Logger lg = Logger.getLogger(NicknameDAL.class.getName());
            lg.log(Level.SEVERE, ex.getMessage(),ex);
        }

        return nickname;

    }

    public Person getName(String input){

        Person person = new Person();

        try{
            config.connection();
            PreparedStatement ps = config.connection().prepareStatement("SELECT n.first_name, n.nickname, n.last_name, b.birthday, b.age\n" +
                    "FROM nickname n\n" +
                    "JOIN birthday b ON n.id = b.id\n" +
                    "WHERE nickname = ?;");
            ps.setString(1,input);
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
