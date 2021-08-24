import com.company.OKTMOGroup;
import com.company.OKTMOData;
import com.company.OKTMOReader;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class OKTMOTest {

    @Test
    public void oktmoTest() {
        long startTime = System.currentTimeMillis();

        OKTMOData place = new OKTMOData();
        new OKTMOReader().readPlaces("data-20210701-structure-20150128.csv", place);

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

        long endTime = System.currentTimeMillis();
        System.out.println("Время обработки теста без регулярного выражения: " + (endTime-startTime) + "ms");
    }

    @Test
    public void oktmoRegTest() {
        long startTime = System.currentTimeMillis();

        OKTMOData place = new OKTMOData();
        new OKTMOReader().readPlacesReg("data-20210701-structure-20150128.csv", place);

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

        long endTime = System.currentTimeMillis();
        System.out.println("Время обработки теста с регулярным выражением: " + (endTime-startTime) + "ms");
    }

    @Test
    public void oktmoTwoMethodTest() {
        long startTime = System.currentTimeMillis();
        OKTMOData place = new OKTMOData();
        new OKTMOReader().readPlaces("data-20210701-structure-20150128.csv", place);
        long endTime = System.currentTimeMillis();

        long startTimeReg = System.currentTimeMillis();
        OKTMOData placeReg = new OKTMOData();
        new OKTMOReader().readPlacesReg("data-20210701-structure-20150128.csv", placeReg);
        long endTimeReg = System.currentTimeMillis();

        assertTrue(place.getPlaces().equals(placeReg.getPlaces()));
        System.out.println("Количество НП при чтении без регулярного выражения = " + place.getPlaces().size() +
                "\nколичество НП при чтении с регулярным выражением = " + placeReg.getPlaces().size());

        System.out.println("");
        System.out.println("Время чтения файл без регулярного выражения: " + (endTime-startTime) + "ms");
        System.out.println("Время чтения файл c регулярным выражением: " + (endTimeReg-startTimeReg) + "ms");
        System.out.println("");
    }

    @Test
    public void oktmoGroupTest() {
        OKTMOData placeGroup = new OKTMOData();
        new OKTMOReader().readPlacesGroup("data-20210701-structure-20150128.csv", placeGroup);

        // Проверка на количество всех регионов
        assertEquals(placeGroup.getOktmoGroupList().size(), 81);

        // Проверка на количество районов в регионе с кодом 1_601_000_000L
        assertEquals(placeGroup.getOktmoGroupMap().get(1_601_000_000L).getOktmoGroupInnerList().size(), 19);

        // Проверка на количество НП в группе с кодом 1_601_402_000L
        assertEquals(placeGroup.getOktmoGroupMap().get(1_601_402_000L).getPlaces().size(), 5);

        // Проверка на наличие группы с кодом 99_630_425_000L в карте
        assertTrue(placeGroup.getOktmoGroupMap().containsKey(99_630_425_000L));

        System.out.println("");

        // Проверка принадлежности группы 1_602_479_000L к группе 1_602_000_000L
        for (OKTMOGroup oktmoGroup : placeGroup.getOktmoGroupMap().get(1_602_000_000L).getOktmoGroupInnerList()) {
            if (oktmoGroup.getCode() == 1_602_479_000L) {
                System.out.println("Группа 1_602_479_000L принадлежит к группе 1_602_000_000L");
            }
        }

        // Вывод всго количества НП в группах
        System.out.println("Всего найдено НП в группах: " + placeGroup.getCountPlaceInGroup());
        System.out.println("");
    }
}
