package operation;

import domain.AOperation;

public class PrintLogOperation extends AOperation {

	private static final long serialVersionUID = 3890567956316645852L;

	public PrintLogOperation() {
		originAccount = null;
		value = null;
		type = OperationType.GET_LOG;
	}

	@Override
	public boolean isValid() {
		return (originAccount != null && originAccount > 0);
	}
	
}
