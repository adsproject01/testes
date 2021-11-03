package ads.better_code;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;

public class ReadIni {
	private String file_name;
	private Ini file;
	
	public ReadIni(String file_name) {
		this.file_name = file_name;
		prep_file();
	}
	
	private void prep_file() {
		try {
			file = new Ini( new File(file_name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getFromIni(String key) {
        return file.get("github", key);
	}
	
	
	public String getOwner() {
		return getFromIni("owner");
	}
	
	public String getRepository() {
		return getFromIni("repository");
	}
	
	public String getBaseUrl() {
		return "https://api.github.com/repos/" + getOwner()+"/"+getRepository();
	}
	
	public String getMainBranch() {
		return getFromIni("main_branch");
	}
	
	public String getCurator() {
		return "Bearer " + getFromIni("curator");
	}
	
	public String getNonCurator() {
		return "Bearer " + getFromIni("others");
	}
	
	public static void main(String[] args) {
		ReadIni ini = new ReadIni("resources/config.ini");
		System.out.println(ini.getOwner());
		System.out.println(ini.getRepository());
		System.out.println(ini.getMainBranch());
		System.out.println(ini.getCurator());
		System.out.println(ini.getNonCurator());
	}
	
}
