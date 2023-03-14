package com.tks.utils;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class Assertions {

    public static <T, EX extends Exception> void assertTrue(Predicate<T> condition, T valueToTest , EX toThrowIfFails) throws EX {
        if(!condition.test(valueToTest)){
            throw toThrowIfFails;
        }
    }

    public static <T, R, EX extends Exception> void assertTrue(Function<T, R> func , T val, EX toThrowIfFails) throws EX {
        if(func.apply(val).equals(false)){
            throw toThrowIfFails;
        }
    }

    public static <T, U, EX extends Exception> void assertTrue(BiPredicate<T,U> condition, T val1, U val2 , EX toThrowIfFails) throws EX {
        if(!condition.test(val1,val2)){
            throw toThrowIfFails;
        }
    }

}
