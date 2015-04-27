package fr.theflogat.gearbox.lib;

public class ModLib {
    public static final String ModId = "gearbox";
    public static final String ModName = "Gearbox Mod";
    public static final String Version = "0.1.0";
    public static final String Author = "theflogat";
	public static final String ProxyLocation = "fr.theflogat.gearbox.proxies";
    
    public static String getId(String str) {
		return Author.toLowerCase() + ModId.toLowerCase() + str;
	}
    
    public static String getAsset(String str){
    	return ModId.toLowerCase() + ":" + str;
    }
    
    public static String getTileId(String str){
		return ModId.toLowerCase() + ":" + str;
    }
}