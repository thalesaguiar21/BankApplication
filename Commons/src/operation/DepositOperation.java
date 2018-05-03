package operation;

import domain.Account;
import domain.AOperation;

public class DepositOperation extends AOperation {

	private static final long serialVersionUID = 1374543645447143994L;

	public DepositOperation() {
		originAccount = null;
		value = null;
		type = OperationType.DEPOSIT;
	}

	@Override
	public boolean isValid() {
		boolean valid = true;
		valid = valid && (originAccount != null);
		valid = valid && (originAccount > 0);
		valid = valid && (value != null);
		valid = valid && (value > 0 && value < Account.MAX_BALANCE);
		return valid;
	}
}
