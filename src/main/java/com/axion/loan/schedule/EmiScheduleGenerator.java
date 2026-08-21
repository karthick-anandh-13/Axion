package com.axion.loan.schedule;
import java.util.List;
import com.axion.loan.entity.Loan;
public interface EmiScheduleGenerator { List<EmiInstallment> generateSchedule(Loan loan); }
