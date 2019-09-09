package vn.topica.itlab4.excercise1;

import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		
		IODevice ioDevice = new IODevice();
		DeviceManager deviceManager = new DeviceManager();
		
		/*
		 * excercise 1.1 
		 */	
		// get list devices from input1.txt
		List<Device> devicesList = ioDevice.readDeviceFromFile(Constant.FILE_INPUT_PATH);
		
		// add all devices to device manager
		deviceManager.addAll(devicesList);
		
		// sort devices by warranty year
		deviceManager.sortByWarrantyYear();
		
		// write devices to ouput1.txt
		ioDevice.writeDeviceToFile(deviceManager, Constant.FILE_OUTPUT_PATH);
		
		
		/*
		 * excercise 1.2
		 */	
		// standardized Owner field in deviceManager
		deviceManager.standardizedOwner();
		
		// show all devices in deviceManager
		deviceManager.showAllDevice();
		
		// write all device after standardizing Owner to file
		ioDevice.writeDeviceToFile(deviceManager, Constant.FILE_OUTPUT_PATH);
		
		
		
		/*
		 * excercise 1.3
		 */
		// write all device with code have TOPICA and input date from 30/10/2018 to 31/10/2019
		List<Device> devicesList2 = deviceManager.findByCodeAndDate("TOPICA","31/10/2018", "31/10/2019");
		ioDevice.writeDeviceToFile(devicesList2, Constant.FILE_OUTPUT_PATH);
		
		
		/*
		 * excercise 1.4
		 */
		// write word in ower appear most
		List<String> wordsAppearMostList = deviceManager.findWordAppearMost();
		ioDevice.writeStringToFile(wordsAppearMostList, Constant.FILE_OUTPUT_PATH);
		
		
	}
	
	
}
