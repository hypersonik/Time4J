package net.time4j;

import net.time4j.engine.RuleNotFoundException;

import java.text.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class ComponentElementTest {

    @Test
    public void dateComponent() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 21, 14, 30);
        tsp = tsp.with(PlainDate.COMPONENT, PlainDate.of(2015, 1, 1));
        assertThat(tsp, is(PlainTimestamp.of(2015, 1, 1, 14, 30)));
    }

    @Test
    public void timeComponent() throws ParseException {
        PlainTimestamp tsp =
          PlainTimestamp.localFormatter("uuuu-MM-dd", PatternType.CLDR)
            .withDefault(PlainTime.COMPONENT, PlainTime.midnightAtEndOfDay())
            .parse("2014-08-20");
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 21, 0, 0)));
    }

    @Test(expected=RuleNotFoundException.class)
    public void dateFromMoment() {
        Moment moment = Moment.UNIX_EPOCH;
        moment.get(PlainDate.COMPONENT);
    }

    @Test(expected=RuleNotFoundException.class)
    public void timeFromMoment() {
        Moment moment = Moment.UNIX_EPOCH;
        moment.get(PlainTime.COMPONENT);
    }

    @Test
    public void setToNextTimeOnSameDay() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp = tsp.with(PlainTime.COMPONENT.setToNext(PlainTime.of(21, 45)));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 19, 21, 45)));
    }

    @Test
    public void setToNextTimeOnNextDay1() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp = tsp.with(PlainTime.COMPONENT.setToNext(PlainTime.of(13, 45)));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 20, 13, 45)));
    }

    @Test
    public void setToNextTimeOnNextDay2() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp = tsp.with(PlainTime.COMPONENT.setToNext(PlainTime.of(14, 30)));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 20, 14, 30)));
    }

    @Test
    public void setToNextOrSameTimeOnSameDay() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp =
            tsp.with(PlainTime.COMPONENT.setToNextOrSame(PlainTime.of(14, 30)));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 19, 14, 30)));
    }

    @Test
    public void setToNextOrSameTimeOnNextDay() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp =
            tsp.with(PlainTime.COMPONENT.setToNextOrSame(PlainTime.of(13, 45)));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 20, 13, 45)));
    }

    @Test
    public void setToNextTime2400() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp =
            tsp.with(
                PlainTime.COMPONENT.setToNext(PlainTime.midnightAtEndOfDay()));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 20, 0, 0)));
    }

    @Test
    public void setToPreviousOrSameTimeOnSameDay() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp =
            tsp.with(
                PlainTime.COMPONENT.setToPreviousOrSame(PlainTime.of(14, 30)));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 19, 14, 30)));
        tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp =
            tsp.with(
                PlainTime.COMPONENT.setToPreviousOrSame(PlainTime.of(12, 30)));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 19, 12, 30)));
    }

    @Test
    public void setToPreviousOrSameTimeOnPreviousDay() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp =
            tsp.with(
                PlainTime.COMPONENT.setToPreviousOrSame(PlainTime.of(16, 45)));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 18, 16, 45)));
    }

    @Test
    public void setToPreviousTimeOnPreviousDay() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 8, 19, 14, 30);
        tsp =
            tsp.with(
                PlainTime.COMPONENT.setToPrevious(PlainTime.of(14, 30)));
        assertThat(tsp, is(PlainTimestamp.of(2014, 8, 18, 14, 30)));
    }

}