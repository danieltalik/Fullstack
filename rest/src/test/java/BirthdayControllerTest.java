import com.danieltalik.fullStackApp.Controllers.BirthdayController;
import com.danieltalik.fullStackApp.Models.Person;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

public class BirthdayControllerTest {
    @Test
    public void addBirthdayTest(){
        Person testPerson = new Person("Daniel","Talik", LocalDate.parse("1988-09-20"),"Dino");
        BirthdayController bc = new BirthdayController();
        int a = bc.setAge(testPerson.getBirthday());
        Assert.assertEquals(33,a);
    }
}
