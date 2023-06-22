package org.example;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }


        long startTs = System.currentTimeMillis(); // start time
        List<Thread> threads = new ArrayList<>();
        List<Future<Integer>> tasks = new ArrayList<>();
        final ExecutorService executorService = Executors.newFixedThreadPool(texts.length);
        for (String text : texts) {
            MyCallable callable = new MyCallable(text);
            Future<Integer> task = executorService.submit(callable);
            tasks.add(task);

//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    int maxSize = 0;
//                    for (int i = 0; i < text.length(); i++) {
//                        for (int j = 0; j < text.length(); j++) {
//                            if (i >= j) {
//                                continue;
//                            }
//                            boolean bFound = false;
//                            for (int k = i; k < j; k++) {
//                                if (text.charAt(k) == 'b') {
//                                    bFound = true;
//                                    break;
//                                }
//                            }
//                            if (!bFound && maxSize < j - i) {
//                                maxSize = j - i;
//                            }
//                        }
//                    }
//                    System.out.println(text.substring(0, 100) + " -> " + maxSize);
//                }
//            });
//
//            threads.add(thread);
//            thread.start();

        }
        List<Integer> results = new ArrayList<>();
        for (Future<Integer> future : tasks) {
            results.add(future.get());
        }
        int max = Collections.max(results);
        System.out.println("Max number is " + max);
        executorService.shutdown();

//        for (Thread thread : threads) {
//            thread.join();
//        }
        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}