/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package org.ibeyondy.fuzzy.io;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * @author dongshi
 * @version : FileChannelTest.java, v 0.1 2020-10-18 13:36 Exp $$
 */
public class FileChannelTest {

//    private void openFile(Path path) {
//        try {
//            FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
//            int byteRead;
//            while ((byteRead = fileChannel.read(buffer)) != -1) {
//                System.out.println("");
//                buffer.rewind();
//            }
//        } catch (IOException e) {
//
//        }
//    }

    @Test
    public void test() throws Exception {
        LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
                .maximumSize(100).expireAfterAccess(10, TimeUnit.MINUTES).removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> removalNotification) {
                        System.out.println("remove" + removalNotification.getKey());
                    }
                })
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        Thread.sleep(1000);
                        System.out.println("load " + key);
                        throw new Exception("load error");
                    }
                });
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
//                graphs.put("hello" + i, "world" + i);
                try {
                    System.out.println("thread: hello" + i + graphs.get("hello" + i));;
                } catch (Exception e) {
                }
            }
        }).start();

        Thread.sleep(1000);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
//                System.out.println(graphs.size());
//                System.out.println(graphs.get("hello" + i, new Callable<String>() {
//                    @Override
//                    public String call() throws Exception {
//                        return null;
//                    }
//                }));


                System.out.println("master = hello" + i + graphs.get("hello" + i));
            } catch (Exception e) {
                System.out.println("miss");
            }
        }

    }
}
