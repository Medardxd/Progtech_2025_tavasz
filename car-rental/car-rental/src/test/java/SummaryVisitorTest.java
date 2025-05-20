import com.carrental.auth.User;
import com.carrental.model.*;
import com.carrental.patterns.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SummaryVisitorTest {

    @Test
    void visitorCollectsSingleLine() {
        Car  car = new Car(1,"demo", CarType.STANDARD, 100);
        User usr = new User(1,"bob",  false);
        LocalDate d = LocalDate.now();

        BasicRental r = new BasicRental(car, usr,
                d, d.plusDays(2),
                new StandardStrategy());

        SummaryVisitor vis = new SummaryVisitor();
        vis.visit(r);

        String txt = vis.report();
        assertTrue(txt.contains("bob renting"));
        assertTrue(txt.contains("$"));            // cost was appended
    }
}
