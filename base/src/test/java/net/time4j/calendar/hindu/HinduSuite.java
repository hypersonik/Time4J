package net.time4j.calendar.hindu;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses(
    {
        HinduElementTest.class,
        HinduEraTest.class,
        HinduFormatTest.class,
        HinduVariantTest.class,
        OldHinduSolarCalendarTest.class,
        OldHinduLunarCalendarTest.class,
        SerializationTest.class
    }
)
public class HinduSuite {

}
