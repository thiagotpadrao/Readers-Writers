# Readers-Writers Synchronization

A Java-based implementation of the Readers-Writers problem, demonstrating thread synchronization and safe access to shared resources using semaphores and locks.

## Overview

This project simulates multiple reader and writer threads accessing a shared data structure. It ensures:

- Multiple readers can read simultaneously.
- Writers have exclusive access.
- No data corruption or race conditions.
- Optional fairness between readers and writers.

## Technologies

- Java Threads (`java.lang.Thread`)
- Synchronization primitives:
  - `ReentrantLock`
  - `Semaphore`
  - `Condition`
