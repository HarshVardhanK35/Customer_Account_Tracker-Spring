package com.wipro.CustomerAccountTracker.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wipro.CustomerAccountTracker.Bean.AccountBean;
import com.wipro.CustomerAccountTracker.Dao.AccountDao;
import com.wipro.CustomerAccountTracker.Exception.RecordNotFoundException;

@Service
public class TransactionService {

	@Autowired
	private AccountDao accountDao;

	// Method to transfer funds between two accounts
	public String transferAmount(long fromAccount, long toAccount, double amount) throws RecordNotFoundException {
		// Find the payer's account
		Optional<AccountBean> payerAccountOpt = accountDao.findByAccountNumber(fromAccount);
		if (!payerAccountOpt.isPresent()) {
			return "ID MISMATCH";  // Return "ID MISMATCH" instead of throwing an exception
		}

		// Find the beneficiary's account
		Optional<AccountBean> beneficiaryAccountOpt = accountDao.findByAccountNumber(toAccount);
		if (!beneficiaryAccountOpt.isPresent()) {
			return "ID MISMATCH";  // Return "ID MISMATCH" for invalid toAccount
		}

		AccountBean payerAccount = payerAccountOpt.get();
		AccountBean beneficiaryAccount = beneficiaryAccountOpt.get();

		// Check if the payer has enough funds
		if (payerAccount.getBalanceAmount() < amount) {
			return "Insufficient funds. Max amount available for transaction is " + payerAccount.getBalanceAmount();
		}

		// Perform the fund transfer
		payerAccount.setBalanceAmount(payerAccount.getBalanceAmount() - amount);  // Subtract the amount
		beneficiaryAccount.setBalanceAmount(beneficiaryAccount.getBalanceAmount() + amount);  // Add the amount

		// Save updated accounts
		accountDao.save(payerAccount);
		accountDao.save(beneficiaryAccount);

		return "Transaction Successful";
	}
}
