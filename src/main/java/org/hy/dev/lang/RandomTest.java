package org.hy.dev.lang;

import org.junit.Test;

import java.util.Random;

/**
 * @author edwin
 * @since 27 Apr 2017
 */
public class RandomTest {

    private final Random rand1 = new Random(2);
    private final Random rand2 = new Random(3);
    @Test
    public void test(){
        for(int i = 0; i < 10; i++){
            System.out.println(rand1.nextInt(i+10));
            System.out.println(rand2.nextInt(i+10));
            System.out.println("==============");
        }
    }
}
