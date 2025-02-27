class InsufficientFundsException extends Exception
{

    InsufficientFundsException(String message)
    {
        super(message);
    }
}

class Bank
{
    String bankName;
    String ifscCode;
    String BranchName;
    Bank(String bankName,String ifscCode,String BranchName)
    {
        this.bankName=bankName;
        this.ifscCode=ifscCode;
        this.BranchName=BranchName;
    }

}

class BankAccount
{

 private   String accountNumber;
 private  String accountHolderName;
 private   double balance;
 private   Bank bank;

        BankAccount(String accountNumber,String accountHolderName,double balance, Bank bank)
        {
            this.accountHolderName=new String(accountHolderName);
            this.accountNumber=new String(accountNumber);
            this.balance=balance;
            this.bank=bank;
        }

        void deposite(double amount)
        {
            
            this.balance+=amount;
            System.out.println("Amount added successfully");
        }
        void withdraw(double amount) throws InsufficientFundsException
        {
            if(this.balance < amount)
            {
                throw new  InsufficientFundsException("Insufficient Balance");
            }
            
                this.balance-=amount;    
            
            

        }
        double getBalance()
        {
            System.out.println("Available Balance:"+ (long)(this.balance) );
            return this.balance;
        }
        String getBranchName()
        {
            if(bank.equals(null))
            {
               System.out.println("please add information about BranchName");
               return null; 
            }
            else
            {

                System.out.println("Branch:"+ this.bank.BranchName);
                return this.bank.BranchName;
            }
        }

}



public class Practical1
{
 
    
    public static void main(String[] args) {
        
        String bankName="SBI";
        String ifscCode="SBIN7867";
        String BranchName="Ahmedabad";


        Bank bank=new Bank(bankName, ifscCode, BranchName);


           String accountNumber="98475984353";
            String accountHolderName="Jayesh";
           double balance=1298462342435d;


            BankAccount bankAccount=new BankAccount(accountNumber, accountHolderName, balance, bank);

            double amount=1322234d;

            //for deposite amount to bank account
            bankAccount.deposite(amount);

            //for withdraw cash from bank account
            amount=34985734986453453d;
            try {
                bankAccount.withdraw(amount);    
            } catch (Exception e) {
                // TODO: handle exception

                System.out.println(e);
            }
            

            //to get balance from bank account
            bankAccount.getBalance();

            //to get branch name
            bankAccount.getBranchName();




    
        

    }
}