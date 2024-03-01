package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread {
    private Lock leftFork;
    private Lock rightFork;
    private int mealsEaten;

    public Philosopher(Lock leftFork, Lock rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.mealsEaten = 0;
    }

    @Override
    public void run() {
        try {
            while (mealsEaten < 3) {
                think();
                eat();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }

    private void think() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is thinking");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void eat() throws InterruptedException {
        leftFork.lock();
        try {
            rightFork.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " is eating");
                Thread.sleep((long) (Math.random() * 1000));
                mealsEaten++;
            } finally {
                rightFork.unlock();
            }
        } finally {
            leftFork.unlock();
        }
    }
}