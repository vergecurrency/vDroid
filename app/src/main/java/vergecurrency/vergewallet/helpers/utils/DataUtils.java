package vergecurrency.vergewallet.helpers.utils;



public final class DataUtils {


	public static byte[] reverse(byte[] array) {

		for(int i=0; i<array.length/2; i++){
			byte temp = array[i];
			array[i] = array[array.length -i -1];
			array[array.length -i -1] = temp;
		}
		return array;
	}
}
