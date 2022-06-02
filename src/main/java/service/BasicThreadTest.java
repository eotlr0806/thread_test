package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 기본 스레드 테스트
 */
public class BasicThreadTest {

    private final Logger logger = LoggerFactory.getLogger(BasicThreadTest.class);
    private final String key    = "value";
    /**
     * 스레드 경합발생으로 인한 문제가 생기는 메서드
     */
    public void conflictRun(){
        List<Thread> threads     = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put(key,10);

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                updateMap(map);
            });
            thread.setName("Thread_" + i);
            threads.add(thread);
        }
        threads.forEach(Thread::start);
        lastLog(threads, map);
    }

    /**
     * 스레드 경합발생으로 인한 문제가 생기지않는 메서드
     */
    public void nonConflictRun(){
        List<Thread> threads     = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put(key,10);

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                replace(map);
            });
            thread.setName("Thread_" + i);
            threads.add(thread);
        }
        threads.forEach(Thread::start);
        lastLog(threads, map);
    }

    // Lock method
    private synchronized void replace(Map<String, Integer> map){
        updateMap(map);
    }

    // update map
    private void updateMap(Map<String, Integer> map){
        String name = Thread.currentThread().getName();
        int val = map.get(key);
        logger.info("[{}] before value:{}", name, val);
        val += 10;
        map.replace(key, val);
        logger.info("[{}] after value:{}", name, val);
    }




    /**
     * 스레드 종료시 로그를 찍기위해 사용
     */
    private void lastLog(List<Thread> threads, Map<String, Integer> map){
        boolean isRun = true;
        while(isRun){
            for(Thread thread : threads){
                if(thread.isAlive()){
                    isRun = true;
                    break;
                }
                isRun = false;
            }
            if(!isRun){
                logger.info("Thread is end.");
                logger.info("value:{}", map.toString());
            }
        }
    }



}
