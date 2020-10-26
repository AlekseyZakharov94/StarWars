package com.mystarwars.math;

import java.util.Random;

public class Rnd {

    private static Random random = new Random();

    public static float nextFloat (float min, float max){
        return random.nextFloat() * (max - min) + min;
    }
}
