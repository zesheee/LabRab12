package com.company;

class Main{
    public static void main(String[] args) {
        methodOne();
        methodTwo();
    }

    private static void methodOne(){
        final int SIZE = 10000000;
        final int HALF = SIZE / 2;
        float [] arr = new float [SIZE];

        long a = System.currentTimeMillis();
        for (int i=0; i<SIZE; i++){
            arr[i]=(float)(Math.sin(0.2f + i/5) * Math.cos(0.2f+i/5) * Math.cos(0.4f + i/2));
        }
        System.out.println("Время работы MethodOne :" + (System.currentTimeMillis()-a));
    }

    private static void methodTwo(){
        final int SIZE = 10000000;
        final int HALF = SIZE / 2;
        float [] arr = new float [SIZE];
        float [] arrCopy_1 = new float [HALF];
        float [] arrCopy_2 = new float [HALF];

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, arrCopy_1, 0, HALF);
        System.arraycopy(arr, HALF, arrCopy_2, 0, HALF);

        Thread myThready = new Thread(() -> {
            for (int i=0; i<HALF; i++){
                arrCopy_1[i]=(float)(Math.sin(0.2f + i/5) * Math.cos(0.2f+i/5) * Math.cos(0.4f + i/2));
            }
            System.arraycopy(arrCopy_1, 0, arr, 0, HALF);
        });

        Thread myThready2 = new Thread(() -> {
            for (int i=0; i<HALF; i++){
                arrCopy_2[i]=(float)(Math.sin(0.2f + i/5) * Math.cos(0.2f+i/5) * Math.cos(0.4f + i/2));
            }
            System.arraycopy(arrCopy_2, 0, arr, HALF, HALF);
        });

        myThready.start();
        myThready2.start();

        try{
            myThready.join();
            myThready2.join();
        }catch (InterruptedException ignored) {}
        System.out.println("Время работы MethodTwo :" + (System.currentTimeMillis()-a));
    }
}