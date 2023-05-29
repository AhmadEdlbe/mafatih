package com.ahmadedlbe.mafatih.utils;

import com.ahmadedlbe.mafatih.R;

import java.util.ArrayList;
import java.util.List;

public class Slide {

    private Slide(){}

    private static final List<Integer> slides = new ArrayList<>();

    static {
        slides.add(R.drawable.photo1);
        slides.add(R.drawable.photo2);
        slides.add(R.drawable.photo3);
        slides.add(R.drawable.photo4);
    }

    public static List<Integer> getSlides() {
        return slides;
    }
}
