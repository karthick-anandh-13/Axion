package com.axion.scheduler;

public interface SchedulerService {

    /**
     * Generates EMI schedules for newly disbursed loans.
     */
    void generateEmiSchedules();

    /**
     * Marks pending installments as OVERDUE
     * when the due date has passed.
     */
    void updateOverdueInstallments();

    /**
     * Sends repayment reminders for upcoming EMIs.
     */
    void sendRepaymentReminders();

    /**
     * Executes all scheduled maintenance jobs.
     */
    void runDailyScheduler();
}