package fr.theflogat.gearbox.api;

public enum GearsValue {
	WOOD(5,10,0.2,"wood"),
	STONE(1,20,0.8,"stone"),
	IRON(0.6F,40,1, "iron"),
	GOLD(0.4F,25,1.2,"gold"),
	TIN(0.5F,30,1.1,"tin"),
	COPPER(0.7F,50,0.8,"copper"),
	TITANIUM(0.3F,120,1,"titanium"),
	SILVER(0.4F,20,1.3,"silver"),
	LEAD(0.6F,80,0.7,"lead"),
	
	
	
	CUSTOM(0,0,0,"")
	;
	
	
	
	public float inst;
	public int out;
	public double eff;
	private String id;
	public static GearsValue[] all = {
		WOOD,STONE,IRON,GOLD,TIN,COPPER,TITANIUM,SILVER,LEAD
	};
	private static int lastValue = 0;
	
	GearsValue(float inst,int out,double eff,String id){
		this.inst = inst;
		this.out = out;
		this.eff = eff;
		this.id = id.toLowerCase();
	}
	
	public boolean isIden(String oreDict) {
		return oreDict.toLowerCase().contains(id);
	}
	
	public static GearsValue getCustom(float inst,int out,double eff,String id){
		return CUSTOM.setInst(inst).setOut(out).setEff(eff).setId(id);
	}
	
	public static GearsValue getValue(String id){
		for(GearsValue v : all){
			if(v.isIden(id)){
				if(id.toLowerCase().contains("polished"))
					return v.getPolished();
				if(id.toLowerCase().contains("efficient"))
					return v.getEff();
				
				return v;
			}
		}
		return null;
	}

	private GearsValue setId(String id2) {
		id = id2;
		return this;
	}

	private GearsValue setEff(double eff2) {
		eff = eff2;
		return this;
	}

	private GearsValue setOut(int out2) {
		out = out2;
		return this;
	}

	private GearsValue setInst(float inst2) {
		inst = inst2;
		return this;
	}
	
	public GearsValue getPolished(){
		return GearsValue.getCustom(inst/2, (int) (out*1.5), eff*1.2F, id);
	}
	
	public GearsValue getEff(){
		return GearsValue.getCustom(inst, out, eff*1.5, id);
	}
}
