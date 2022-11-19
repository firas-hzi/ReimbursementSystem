package Model;

public enum Role {

	 MANAGER(1), EMPLOYEE(2), DEFAULT(3);
	
	 private final int value;
	 
	    private Role(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    public int getValue(String v)
	    {
	    	if(v.equalsIgnoreCase("manager")) return 1;
	    	if(v.equalsIgnoreCase("employee")) return 2;
	    return 3;
	    }
}
