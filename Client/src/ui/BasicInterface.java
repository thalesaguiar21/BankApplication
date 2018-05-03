package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import domain.AOperation;
import exceptions.BankException;
import operation.CreateAccOperation;
import operation.DepositOperation;
import operation.OperationType;
import operation.PrintLogOperation;
import operation.TransferenceOperation;
import operation.WithdrawOperation;
import utils.CpfUtils;
import utils.NameUtils;

public class BasicInterface {
	
	private final String INVALID_NUM = "[ERROR] Please, insert a valid value!";
	
	private Scanner reader;
	private AOperation operation;
	
	public BasicInterface() {
		reader = new Scanner(System.in);
		operation = null;
	}

	public OperationType selectOp() {
		System.out.println("1) Create an account");
		System.out.println("2) Withdraw");
		System.out.println("3) Deposit");
		System.out.println("4) Transference");
		System.out.println("5) Search for account number");
		System.out.println("6) Print statement");
		System.out.println("7) Exit");
		System.out.print("Enter the desired operation number: ");
		
		try {
			Integer op = reader.nextInt() - 1;
			if(op >= 0 && op < OperationType.values().length)
				return OperationType.values()[op];
		} catch (InputMismatchException inpExc) {
			System.out.println(this.INVALID_NUM);
			return null;
		} finally {
			reader.nextLine();
		}
		return null;
	}
	
	public AOperation redirectOp(OperationType op) {
		System.out.println(header(op));
		switch (op) {
			case CREATE:
				createOpUI();
				break;
				
			case WITHDRAW:
				withdrawOpUI();
				break;
				
			case DEPOSIT:
				depositOpUI();
				break;
				
			case TRANSFERENCE:
				transferenceOpUI();
				break;
	
			case FIND_ACCOUNTS:
				System.out.println("[FIND] NOT IMPLEMENTED!");
				break;
				
			case GET_LOG:
				logOpUI();
				break;
				
			case EXIT:
				System.out.println("Closing operations... Finished");
				break;
				
			default:
				System.err.println("[ERROR] Invalid operation! " + op);
				break;
		}
		System.out.println("\n\n");
		return operation;
	}
	
	private String header(OperationType op) {
		if(op != OperationType.EXIT) {
			String head = "";
			head += "-----------------------------------------";
			head += "             " + op + "            ";
			head += "-----------------------------------------";
			return head;
		} else {
			return "";
		}
	}
	
	private void createOpUI() {
		CreateAccOperation op = new CreateAccOperation();
		try {
			System.out.print("Owner's name: ");
			op.setOwnerName(reader.next());
			
			if(!NameUtils.isValidName(op.getOwnerName()))
				throw new BankException("[ERROR] A name at leat 4 and at most 30 characters, and only letters!");
			
			System.out.print("Owner's cpf: ");
			op.setCpf(reader.next());
			
			if(!CpfUtils.isValid(op.getCpf()))
				throw new InputMismatchException();
			
			System.out.print("Initial balance: ");
			op.setValue(reader.nextDouble());
			operation = op;
		} catch (InputMismatchException inpEx) {
			operation = null;
			System.out.println(this.INVALID_NUM);
		} catch(BankException bExc) {
			operation = null;
			System.out.println(bExc.getMessage());
		} finally {
			reader.nextLine();
		}
	}
	
	private void accountOpUI() {
		try {
			System.out.print("Account number: ");
			operation.setOriginAccount(reader.nextInt());
			System.out.print("Value: ");
			operation.setValue(reader.nextDouble());
		} catch(InputMismatchException inpE) {
			operation = null;
			System.out.println(this.INVALID_NUM);
		} finally {
			reader.nextLine();
		}
	}
	
	private void withdrawOpUI() {
		WithdrawOperation withdraw = new WithdrawOperation();
		operation = withdraw;
		accountOpUI();
	}
	
	private void depositOpUI() {
		DepositOperation deposit = new DepositOperation();
		operation = deposit;
		accountOpUI();
	}
	
	private void transferenceOpUI() {
		TransferenceOperation transference = new TransferenceOperation();
		operation = transference;		
		try {
			System.out.print("Origin account number: ");
			transference.setOriginAccount(reader.nextInt());
			System.out.print("Target account number: ");
			transference.setTargetAccount(reader.nextInt());
			System.out.print("Value: ");
			transference.setValue(reader.nextDouble());			
		} catch(InputMismatchException inpExc) {
			operation = null;
			System.out.println(this.INVALID_NUM);
		} finally {
			reader.nextLine();
		}
	}
	

	private void logOpUI() {
		PrintLogOperation printLog = new PrintLogOperation();
		operation = printLog;
		try {
			System.out.print("Account number: ");
			operation.setOriginAccount(reader.nextInt());
		} catch(InputMismatchException inpEx) {
			operation = null;
			System.out.println(this.INVALID_NUM);
		} finally {
			reader.nextLine();
		}
	}
}
