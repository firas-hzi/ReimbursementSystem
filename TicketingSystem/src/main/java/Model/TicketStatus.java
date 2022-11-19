package Model;

public enum TicketStatus {

	PENDING(1), 
	APPROVED(2),
	DENIED(3),
	DEFAULT(4);
	
	 private final int value;
	 
    private TicketStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public int getValue(String v)
    {
    	if(v.equalsIgnoreCase("pending")) return 1;
    	if(v.equalsIgnoreCase("approved")) return 2;
    	if(v.equalsIgnoreCase("denied"))return 3;
    	
		return 4;
    }
}
