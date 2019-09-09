package vn.topica.itlab4.excercise1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IODevice {
	
	public List<Device> readDeviceFromFile(String path) {
		List<Device> devices = new ArrayList<Device>();
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				
				String[] fields = line.split(",");
				
				String code = fields[0];
				String name = fields[1];
				String owner = fields[2];
				Date inputDate = Utils.stringToDate(fields[3]);
				int warrantyYear = Integer.parseInt(fields[4]);
				
				Device device = new Device(code, name, owner, inputDate, warrantyYear);
				devices.add(device);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return devices;
	}
	
	public void writeDeviceToFile(List<Device> devices, String filePath) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
			bufferedWriter.append("###");
			bufferedWriter.newLine();
			for (int i = 0; i < devices.size(); i++) {
				bufferedWriter.append(devices.get(i).toString());
				bufferedWriter.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void writeStringToFile(List<String> listString, String filePath) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
			bufferedWriter.append("###");
			bufferedWriter.newLine();
			for (int i = 0; i < listString.size(); i++) {
				bufferedWriter.append(listString.get(i).toString());
				bufferedWriter.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
