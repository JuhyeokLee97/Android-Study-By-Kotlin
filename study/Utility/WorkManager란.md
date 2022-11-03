# WorkManger란

## 개요
> WorkManager is the recommended solution for persistent work. 
> Work is persistent when it remains scheduled through app restarts and system reboots. 
> Because most background processing is best accomplished through persistent work, WorkManager is the primary recommended API for background processing.


## 지속적인 작업 유형

WorkManager handles three types of persistent work:

- Immediate: Tasks that must begin immediately and complete soon. May be expedited.
- Long Running: Tasks which might run for longer, potentially longer than 10 minutes.
- Deferrable: Scheduled tasks that start at a later time and can run periodically.

## 특징
> In addition to providing a simpler and more consistent API, WorkManager has a number of other key benefits:

### 작업에 대한 제한사항
> Declaratively define the optimal conditions for your work to run using work constraints. 
> For example, run only when the device is on an unmetered network, when the device is idle, or when it has sufficient battery.

### 스케줄링
> WorkManager allows you to schedule work to run one-time or repeatedly using flexible scheduling windows. 
> Work can be tagged and named as well, allowing you to schedule unique, replaceable work and monitor or cancel groups of work together.
> 
> Scheduled work is stored in an internally managed SQLite database and WorkManager takes care of ensuring that this work persists and is rescheduled across device reboots.
> 
> In addition, WorkManager adheres to power-saving features and best practices like Doze mode, so you don't have to worry about it.
