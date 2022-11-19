package Model;

public enum TicketType {

	 TRAVEL(1),
	  LODGING(2),
	  FOOD(3),
	  OTHER(4),
	  DEFAULT(5);
	
	 private final int value;
	    private TicketType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    public int getValue(String v)
	    {
	    	if(v.equalsIgnoreCase("travel")) return 1;
	    	if(v.equalsIgnoreCase("lodging")) return 2;
	    	if(v.equalsIgnoreCase("food"))return 3;
	    	if(v.equalsIgnoreCase("other")) return 4;
			return 5;
	    }
	    
	    public TicketType getValue(int v)
	    {
	    	if(v==1) return TicketType.TRAVEL;
	    	if(v==2) return TicketType.LODGING;
	    	if(v==3) return TicketType.FOOD;
	    	if(v==4) return TicketType.OTHER;
			return TicketType.DEFAULT;
	    }
	   
}
