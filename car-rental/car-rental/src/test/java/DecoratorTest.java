import com.carrental.auth.User;
import com.carrental.model.*;
import com.carrental.patterns.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorTest {

    @Test
    void decoratorsAddFixedFees() {
        Car   car = new Car(1, "test", CarType.STANDARD, 100);
        User usr = new User(1, "u", false);
        LocalDate d = LocalDate.now();

        Rental r = new BasicRental(car, usr, d, d.plusDays(6),
                new StandardStrategy());   // $700
        r = new GPS(r);                                       // +10
        r = new Insurance(r);                                 // +15

        assertEquals(655, r.cost(), 1e-6);

    }
}
