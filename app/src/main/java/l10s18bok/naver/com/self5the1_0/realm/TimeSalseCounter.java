package l10s18bok.naver.com.self5the1_0.realm;

/**
 * Created by pp on 2018-02-20.
 */

public class TimeSalseCounter {
        private String time;
        private long amount ;

        public TimeSalseCounter(String time, long amount) {
            this.time = time;
            this.amount = amount;
        }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}

