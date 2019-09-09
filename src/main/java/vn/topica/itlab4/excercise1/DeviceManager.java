package vn.topica.itlab4.excercise1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DeviceManager extends ArrayList<Device> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void sortByWarrantyYear() {
		Collections.sort(this, new Comparator<Device>() {

			public int compare(Device o1, Device o2) {
				// TODO Auto-generated method stub
				return o1.getWarrantyYear() - o2.getWarrantyYear();
			}
		});
	}

	public void standardizedOwner() {

		for (int i = 0; i < this.size(); i++) {
			String standardOwner = Utils.standardizedString(this.get(i).getOwner());
			this.get(i).setOwner(standardOwner);
		}

		System.out.println("Standardized Owner sucessfully");

	}

	public List<Device> findByCodeAndDate(String code, String date1, String date2) {
		List<Device> devices = new ArrayList<Device>();

		Date inputDate1 = Utils.stringToDate(date1);
		Date inputDate2 = Utils.stringToDate(date2);

		for (Device device : this) {
			if (device.getCode().contains(code) && device.getInputDate().compareTo(inputDate1) >= 0
					&& device.getInputDate().compareTo(inputDate2) <= 0) {
				devices.add(device);
			}
		}

		Collections.sort(devices, new Comparator<Device>() {

			public int compare(Device o1, Device o2) {
				// TODO Auto-generated method stub
				Date x1 = o1.getInputDate();
				Date x2 = o2.getInputDate();
				int sComp = x1.compareTo(x2);

				if (sComp != 0) {
					return sComp;
				}

				
				return o1.getWarrantyYear() - o2.getWarrantyYear();
			}
		});

		return devices;
	}
	
	public List<String> findWordAppearMost() {
		
		List<String> wordAppearMostList = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for (Device device : this) {
			String[] words = device.getOwner().split(" ");
			for (String word : words) {
				if(map.containsKey(word)) {
					map.put(word, map.get(word) + 1);
				}else {
					map.put(word, 1);
				}
			}	
		}
		
		int maxValueInMap = Collections.max(map.values());
		for (Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == maxValueInMap) {
				wordAppearMostList.add(entry.getKey());
				System.out.println(entry.getKey());
			}
		}
		
		return wordAppearMostList;
	}

	public void showAllDevice() {
		for (Device device : this) {
			System.out.println(device.toString());
		}
	}

}
