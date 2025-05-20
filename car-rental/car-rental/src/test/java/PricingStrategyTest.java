import com.carrental.model.CarType;
import com.carrental.patterns.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PricingStrategyTest {

    private static final double DELTA = 1e-6;      // floating-point tolerance
    private final StandardStrategy std   = new StandardStrategy();
    private final PremiumStrategy  prem  = new PremiumStrategy();

    /* ---------- Standard plan --------------------------------------- */

    @Test
    void standardNoDiscountBefore7Days() {
        double cost = std.apply(600, 6, CarType.STANDARD);   // any base/length
        assertEquals(600, cost, DELTA);
    }

    @Test
    void standardGets10PercentAfter7Days() {
        double cost = std.apply(700, 7, CarType.STANDARD);
        assertEquals(630, cost, DELTA);          // 700 × 0.9
    }

    @Test
    void standardGets20PercentAfter14Days() {
        double cost = std.apply(1400, 14, CarType.STANDARD);
        assertEquals(1120, cost, DELTA);         // 1400 × 0.8
    }

    /* ---------- Premium plan ---------------------------------------- */

    @Test
    void premiumAdds20PercentThenNormalDiscounts() {
        // 7-day rental: base 700 → +20 % = 840 → 10 % off = 756
        double cost = prem.apply(700, 7, CarType.PREMIUM);
        assertEquals(756, cost, DELTA);
    }
}
