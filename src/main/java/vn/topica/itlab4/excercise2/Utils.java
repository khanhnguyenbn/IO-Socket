package vn.topica.itlab4.excercise2;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

	// read data from DataInputStream and return information package
	public static InformationPackage readInformationPackage(DataInputStream is) throws IOException, EOFException {
		
				int lengOfMessage = is.readInt();

				short cmdCode = is.readShort();

				short version = is.readShort();

				Map<Short, TLV> tLVMap = new HashMap<Short, TLV>();

				while (is.available() > 0) {
					

					short tag = is.readShort();

					short lenth = is.readShort();

					// get String(value) of TLV
					byte[] valueByte = new byte[lenth];
					is.read(valueByte);
					String value = "";
					for (byte b : valueByte) {
						value = value + (char) b;
					}
					

					TLV tlv = new TLV(tag, value);
					tLVMap.put(tlv.getTag(), tlv);

				}

				InformationPackage informationPackage = new InformationPackage(cmdCode, tLVMap);
				return informationPackage;
	}
}
