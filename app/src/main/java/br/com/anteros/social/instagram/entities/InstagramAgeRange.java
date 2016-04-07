package br.com.anteros.social.instagram.entities;

import br.com.anteros.social.AgeRange;

/**
 * Created by edson on 25/03/16.
 */
public class InstagramAgeRange implements AgeRange{


    private String min;

    private String max;

    public InstagramAgeRange(String min, String max) {
        this.min = min;
        this.max = max;
    }


    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    @Override
    public String toString() {
        return min+" to "+max;
    }
}

