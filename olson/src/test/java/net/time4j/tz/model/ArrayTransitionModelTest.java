package net.time4j.tz.model;

import net.time4j.SystemClock;
import net.time4j.base.UnixTime;
import net.time4j.tz.OffsetSign;
import net.time4j.tz.TransitionHistory;
import net.time4j.tz.ZonalOffset;
import net.time4j.tz.ZonalTransition;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class ArrayTransitionModelTest {

    private static final ZonalTransition FIRST =
        new ZonalTransition(0L, 1800, 7200, 3600);
    private static final ZonalTransition SECOND =
        new ZonalTransition(365 * 86400L, 7200, 3600, 3600);
    private static final ZonalTransition THIRD =
        new ZonalTransition(730 * 86400L, 3600, -23 * 3600, 0);
    private static final ZonalTransition FOURTH =
        new ZonalTransition(
            SystemClock.INSTANCE.currentTime().getPosixTime() + 730 * 86400L,
            -23 * 3600,
            -23 * 3600 + 3600,
            3600);
    private static final TransitionHistory MODEL =
        new ArrayTransitionModel(Arrays.asList(THIRD, FIRST, FOURTH, SECOND));

    @Test(expected=IllegalArgumentException.class)
    public void createInconsistentModel() {
        ZonalTransition zt =
            new ZonalTransition(86400L, 3600, 7200, 3600);
        TransitionHistory model =
            new ArrayTransitionModel(Arrays.asList(FIRST, zt));
        assertThat(model, notNullValue());
    }

    @Test
    public void getInitialOffset() {
        assertThat(
            MODEL.getInitialOffset(),
            is(ZonalOffset.ofHoursMinutes(OffsetSign.AHEAD_OF_UTC, 0, 30)));
    }

    @Test
    public void getStartTransition1() {
        assertThat(
            MODEL.getStartTransition(new UT(-1)),
            nullValue());
    }

    @Test
    public void getStartTransition2() {
        assertThat(
            MODEL.getStartTransition(new UT(0)),
            is(FIRST));
    }

    @Test
    public void getStartTransition3() {
        assertThat(
            MODEL.getStartTransition(new UT(365 * 86400L - 1)),
            is(FIRST));
    }

    @Test
    public void getStartTransition4() {
        assertThat(
            MODEL.getStartTransition(new UT(365 * 86400L)),
            is(SECOND));
    }

    @Test
    public void getNextTransition1() {
        assertThat(
            MODEL.getNextTransition(new UT(-1)),
            is(FIRST));
    }

    @Test
    public void getNextTransition2() {
        assertThat(
            MODEL.getNextTransition(new UT(0)),
            is(SECOND));
    }

    @Test
    public void getNextTransition3() {
        assertThat(
            MODEL.getNextTransition(new UT(FOURTH.getPosixTime() - 1)),
            is(FOURTH));
    }

    @Test
    public void getNextTransition4() {
        assertThat(
            MODEL.getNextTransition(new UT(FOURTH.getPosixTime())),
            nullValue());
    }

    @Test
    public void getStdTransitions() {
        assertThat(
            MODEL.getStdTransitions(),
            is(Arrays.asList(FIRST, SECOND, THIRD)));
    }

    @Test
    public void getTransitions1() {
        assertThat(
            MODEL.getTransitions(new UT(0), new UT(2 * 365 * 86400L + 1)),
            is(Arrays.asList(FIRST, SECOND, THIRD)));
    }

    @Test
    public void getTransitions2() {
        assertThat(
            MODEL.getTransitions(new UT(1), new UT(2 * 365 * 86400L)),
            is(Arrays.asList(SECOND)));
    }

    @Test
    public void isEmpty() {
        assertThat(MODEL.isEmpty(), is(false));
    }

    // Hilfsklasse
    private static class UT implements UnixTime {

        private final long ut;

        UT(long ut) {
            super();
            this.ut = ut;
        }

        @Override
        public long getPosixTime() {
            return this.ut;
        }

        @Override
        public int getNanosecond() {
            return 0;
        }

    }

}