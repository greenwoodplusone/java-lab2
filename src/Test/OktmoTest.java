import com.company.OktmoData;
import com.company.OktmoReader;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class OktmoTest {

    @Test
    public void oktmoTest() {
        OktmoData place = new OktmoData();
        new OktmoReader().readPlaces("data-20210701-structure-20150128.csv", place);

        // Проверка на 10 населенный пункт
        assertEquals(place.getNamePlace(10), "Боровское");
        assertEquals(place.getCodePlace(10), 1_601_417_101L);
        assertEquals(place.getStatusPlace(10), "с");

        // Проверка на последний населенный пункт
        assertEquals(place.getNamePlace(place.getCountPlaces() - 1), "Биробиджан");
        assertEquals(place.getCodePlace(place.getCountPlaces() - 1), 99_701_000_001L);
        assertEquals(place.getStatusPlace(place.getCountPlaces() - 1), "г");

        Pattern reg = Pattern.compile("^[А-Я].?ово$", Pattern.CASE_INSENSITIVE);
        Matcher mather = reg.matcher("Жтово");
        assertTrue(mather.find());
    }

    @Test
    public void oktmoTestReg() {
        OktmoData place = new OktmoData();
        new OktmoReader().readPlacesReg("data-20210701-structure-20150128.csv", place);

        // Проверка на 10 населенный пункт
        assertEquals(place.getNamePlace(10), "Боровское");
        assertEquals(place.getCodePlace(10), 1_601_417_101L);
        assertEquals(place.getStatusPlace(10), "с");

        // Проверка на последний населенный пункт
        assertEquals(place.getNamePlace(place.getCountPlaces() - 1), "Биробиджан");
        assertEquals(place.getCodePlace(place.getCountPlaces() - 1), 99_701_000_001L);
        assertEquals(place.getStatusPlace(place.getCountPlaces() - 1), "г");

        Pattern reg = Pattern.compile("^[А-Я].?ово$", Pattern.CASE_INSENSITIVE);
        Matcher mather = reg.matcher("Жтово");
        assertTrue(mather.find());
    }

    @Test
    public void oktmoTestTwoMethod() {
        OktmoData place = new OktmoData();
        new OktmoReader().readPlaces("data-20210701-structure-20150128.csv", place);

        OktmoData placeReg = new OktmoData();
        new OktmoReader().readPlacesReg("data-20210701-structure-20150128.csv", placeReg);

        assertTrue(place.getPlaces().equals(placeReg.getPlaces()));
        System.out.println("Количество НП при чтении без регулярного выражения = " + place.getPlaces().size() +
                "\nколичество НП при чтении с регулярным выражением = " + placeReg.getPlaces().size());

        Pattern re1 = Pattern.compile(".+");
        String s = "dhgfg ghjgsj h jhwg wejkhgwe";
        Matcher m = re1.matcher(s);
        assertFalse(m.matches());

    }
}
