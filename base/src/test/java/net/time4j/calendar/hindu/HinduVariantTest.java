package net.time4j.calendar.hindu;

import net.time4j.Weekday;
import net.time4j.calendar.IndianMonth;
import net.time4j.calendar.astro.SolarTime;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.EpochDays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class HinduVariantTest {

    @Test
    public void isSolar() {
        for (HinduRule rule : HinduRule.values()) {
            switch (rule) {
                case ORISSA:
                case TAMIL:
                case MALAYALI:
                case MADRAS:
                case BENGAL:
                    assertThat(
                        rule.variant().isSolar(),
                        is(true));
                    break;
                default:
                    assertThat(
                        rule.variant().isSolar(),
                        is(false));
            }
        }
        assertThat(
            AryaSiddhanta.SOLAR.variant().isSolar(),
            is(true));
        assertThat(
            AryaSiddhanta.LUNAR.variant().isSolar(),
            is(false));
    }

    @Test
    public void isLunisolar() {
        assertThat(
            HinduRule.ORISSA.variant().isLunisolar(),
            is(false));
        assertThat(
            HinduRule.AMANTA.variant().isLunisolar(),
            is(true));
        assertThat(
            HinduRule.AMANTA_ASHADHA.variant().isLunisolar(),
            is(true));
        assertThat(
            HinduRule.AMANTA_KARTIKA.variant().isLunisolar(),
            is(true));
        assertThat(
            HinduRule.PURNIMANTA.variant().isLunisolar(),
            is(true));
        assertThat(
            AryaSiddhanta.SOLAR.variant().isLunisolar(),
            is(false));
        assertThat(
            AryaSiddhanta.LUNAR.variant().isLunisolar(),
            is(true));
    }

    @Test
    public void isAmanta() {
        assertThat(
            HinduRule.ORISSA.variant().isAmanta(),
            is(false));
        assertThat(
            HinduRule.AMANTA.variant().isAmanta(),
            is(true));
        assertThat(
            HinduRule.AMANTA_ASHADHA.variant().isAmanta(),
            is(true));
        assertThat(
            HinduRule.AMANTA_KARTIKA.variant().isAmanta(),
            is(true));
        assertThat(
            HinduRule.PURNIMANTA.variant().isAmanta(),
            is(false));
        assertThat(
            AryaSiddhanta.SOLAR.variant().isAmanta(),
            is(false));
        assertThat(
            AryaSiddhanta.LUNAR.variant().isAmanta(),
            is(true));
    }

    @Test
    public void isPurnimanta() {
        assertThat(
            HinduRule.ORISSA.variant().isPurnimanta(),
            is(false));
        assertThat(
            HinduRule.AMANTA.variant().isPurnimanta(),
            is(false));
        assertThat(
            HinduRule.AMANTA_ASHADHA.variant().isPurnimanta(),
            is(false));
        assertThat(
            HinduRule.AMANTA_KARTIKA.variant().isPurnimanta(),
            is(false));
        assertThat(
            HinduRule.PURNIMANTA.variant().isPurnimanta(),
            is(true));
        assertThat(
            AryaSiddhanta.SOLAR.variant().isPurnimanta(),
            is(false));
        assertThat(
            AryaSiddhanta.LUNAR.variant().isPurnimanta(),
            is(false));
    }

    @Test
    public void isOld() {
        assertThat(
            HinduRule.ORISSA.variant().isOld(),
            is(false));
        assertThat(
            HinduRule.AMANTA.variant().isOld(),
            is(false));
        assertThat(
            HinduRule.AMANTA_ASHADHA.variant().isOld(),
            is(false));
        assertThat(
            HinduRule.AMANTA_KARTIKA.variant().isOld(),
            is(false));
        assertThat(
            HinduRule.PURNIMANTA.variant().isOld(),
            is(false));
        assertThat(
            AryaSiddhanta.SOLAR.variant().isOld(),
            is(true));
        assertThat(
            AryaSiddhanta.LUNAR.variant().isOld(),
            is(true));
    }

    @Test
    public void isUsingElapsedYears() {
        assertThat(
            HinduRule.ORISSA.variant().isUsingElapsedYears(),
            is(true));
        assertThat(
            HinduRule.ORISSA.variant().withElapsedYears().isUsingElapsedYears(),
            is(true));
        assertThat(
            HinduRule.ORISSA.variant().withCurrentYears().isUsingElapsedYears(),
            is(false));
        assertThat(
            HinduRule.TAMIL.variant().isUsingElapsedYears(),
            is(false));
        assertThat(
            HinduRule.TAMIL.variant().withElapsedYears().isUsingElapsedYears(),
            is(true));
        assertThat(
            HinduRule.TAMIL.variant().withCurrentYears().isUsingElapsedYears(),
            is(false));
        assertThat(
            HinduRule.MALAYALI.variant().isUsingElapsedYears(),
            is(false));
        assertThat(
            AryaSiddhanta.SOLAR.variant().isUsingElapsedYears(),
            is(true));
    }

    @Test
    public void getDefaultEra() {
        HinduVariant v1 = HinduRule.ORISSA.variant();
        HinduVariant v2 = v1.with(HinduEra.NEPALESE);
        assertThat(
            v1.getDefaultEra(),
            is(HinduEra.SAKA));
        assertThat(
            v2.getDefaultEra(),
            is(HinduEra.NEPALESE));
    }

    @Test
    public void variant() {
        HinduVariant v1 = HinduRule.ORISSA.variant();
        HinduVariant v2 = HinduRule.AMANTA.variant();
        HinduVariant v3 = HinduRule.PURNIMANTA.variant().withCurrentYears();
        HinduVariant v4 = HinduRule.PURNIMANTA.variant().with(HinduEra.KALI_YUGA);
        HinduVariant v5 = HinduRule.AMANTA_ASHADHA.variant();
        HinduVariant v6 = HinduRule.AMANTA_KARTIKA.variant();
        HinduVariant v7 = AryaSiddhanta.SOLAR.variant();
        HinduVariant v8 = AryaSiddhanta.LUNAR.variant();
        HinduVariant v9 = HinduRule.MALAYALI.variant().withAlternativeHinduSunrise();
        HinduVariant v10 = HinduRule.MALAYALI.variant().withAlternativeLocation(SolarTime.ofMecca());
        assertThat(
            HinduVariant.from(v1.getVariant()),
            is(v1));
        assertThat(
            HinduVariant.from(v2.getVariant()),
            is(v2));
        assertThat(
            HinduVariant.from(v3.getVariant()),
            is(v3));
        assertThat(
            HinduVariant.from(v4.getVariant()),
            is(v4));
        assertThat(
            HinduVariant.from(v5.getVariant()),
            is(v5));
        assertThat(
            HinduVariant.from(v6.getVariant()),
            is(v6));
        assertThat(
            HinduVariant.from(v7.getVariant()),
            is(v7));
        assertThat(
            HinduVariant.from(v8.getVariant()),
            is(v8));
        assertThat(
            HinduVariant.from(v9.getVariant()),
            is(v9));
        assertThat(
            HinduVariant.from(v10.getVariant()),
            is(v10));
    }

    @Test
    public void oldSolarCS() {
        HinduVariant hv = AryaSiddhanta.SOLAR.variant();
        CalendarSystem<HinduCalendar> cs = hv.getCalendarSystem();
        HinduCalendar cal = cs.transform(EpochDays.UTC.transform(0, EpochDays.RATA_DIE));

        // see Calendrical Calculations (ultimate edition), p158
        assertThat(cal.getEra(), is(HinduEra.KALI_YUGA));
        assertThat(cal.getYear(), is(3101));
        assertThat(cal.getMonth().getValue(), is(IndianMonth.MAGHA));
        assertThat(cal.getMonth().getRasi(), is(10));
        assertThat(cal.getMonth().getRasi(Locale.ROOT), is("Makara"));
        assertThat(cal.getMonth().isLeap(), is(false));
        assertThat(cal.getDayOfMonth(), is(HinduDay.valueOf(19)));
        assertThat(cal.getDayOfWeek(), is(Weekday.SUNDAY));
        assertThat(EpochDays.RATA_DIE.transform(cs.transform(cal), EpochDays.UTC), is(0L));

        cal = cs.transform(cs.getMinimumSinceUTC());
        assertThat(cal.getEra(), is(HinduEra.KALI_YUGA));
        assertThat(cal.getYear(), is(0));
        assertThat(cal.getMonth().getValue(), is(IndianMonth.VAISHAKHA));
        assertThat(cal.getMonth().isLeap(), is(false));
        assertThat(cal.getDayOfMonth(), is(HinduDay.valueOf(1)));

        cal = cs.transform(cs.getMaximumSinceUTC());
        assertThat(cal.getEra(), is(HinduEra.KALI_YUGA));
        assertThat(cal.getYear(), is(5999));
        assertThat(cal.getMonth().getValue(), is(IndianMonth.CHAITRA));
        assertThat(cal.getMonth().isLeap(), is(false));
        assertThat(cal.getDayOfMonth(), is(HinduDay.valueOf(30)));
    }

    @Test
    public void oldLunarCS() {
        HinduVariant hv = AryaSiddhanta.LUNAR.variant();
        CalendarSystem<HinduCalendar> cs = hv.getCalendarSystem();
        HinduCalendar cal = cs.transform(EpochDays.UTC.transform(0, EpochDays.RATA_DIE));

        // see Calendrical Calculations (ultimate edition), p162
        assertThat(cal.getEra(), is(HinduEra.KALI_YUGA));
        assertThat(cal.getYear(), is(3101));
        assertThat(cal.getMonth().getValue(), is(IndianMonth.PAUSHA));
        assertThat(cal.getMonth().isLeap(), is(false));
        assertThat(cal.getDayOfMonth(), is(HinduDay.valueOf(19)));
        assertThat(cal.getDayOfWeek(), is(Weekday.SUNDAY));
        assertThat(EpochDays.RATA_DIE.transform(cs.transform(cal), EpochDays.UTC), is(0L));

        cal = cs.transform(cs.getMinimumSinceUTC());
        assertThat(cal.getEra(), is(HinduEra.KALI_YUGA));
        assertThat(cal.getYear(), is(0));
        assertThat(cal.getMonth().getValue(), is(IndianMonth.CHAITRA));
        assertThat(cal.getMonth().isLeap(), is(true));
        assertThat(cal.getDayOfMonth(), is(HinduDay.valueOf(1)));

        cal = cs.transform(cs.getMaximumSinceUTC());
        assertThat(cal.getEra(), is(HinduEra.KALI_YUGA));
        assertThat(cal.getYear(), is(5999));
        assertThat(cal.getMonth().getValue(), is(IndianMonth.PHALGUNA));
        assertThat(cal.getMonth().isLeap(), is(false));
        assertThat(cal.getDayOfMonth(), is(HinduDay.valueOf(30)));
    }

    @Test
    public void oldLunarInvalid() {
        HinduCS cs = AryaSiddhanta.LUNAR.variant().getCalendarSystem();
        HinduMonth m = HinduMonth.ofLunisolar(1).withLeap();
        assertThat(
            cs.isValid(0, m, HinduDay.valueOf(14)),
            is(true));
        assertThat(
            cs.isValid(0, m, HinduDay.valueOf(15)), // expunged day
            is(false));
        assertThat(
            cs.isValid(0, m, HinduDay.valueOf(16)),
            is(true));
    }

}
