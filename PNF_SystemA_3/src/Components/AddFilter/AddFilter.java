package Components.AddFilter;

import java.io.IOException;

import Framework.CommonFilterImpl;

public class AddFilter extends CommonFilterImpl {
	@Override
	public boolean specificComputationForFilter() throws IOException {
		int checkBlank = 5;
		int numOfBlank = 0;
		int idx = 0;
		byte[] buffer = new byte[64];
		boolean isFirst = true;
		boolean isSecond = true;
		int byte_read = 0;
		int cutPoint1 = 0;
		int cutPoint2 = 0;

		byte[] blank = { ' ' };

		while (true) {
			while (byte_read != '\n' && byte_read != -1) {
				byte_read = in.read();
				if (byte_read == ' ')
					numOfBlank++;
				if (byte_read != -1)
					buffer[idx++] = (byte) byte_read;
				while (numOfBlank == checkBlank) {
					if (buffer[idx - 6] == '1' && buffer[idx - 5] == '7' && buffer[idx - 4] == '6'
							&& buffer[idx - 3] == '5' && buffer[idx - 2] == '1') {
						cutPoint1 = idx - 6;
						checkBlank++;
						isFirst = false;
					} else if (buffer[idx - 6] == '1' && buffer[idx - 5] == '7' && buffer[idx - 4] == '6'
							&& buffer[idx - 3] == '5' && buffer[idx - 2] == '2') {
						cutPoint2 = idx - 6;
						checkBlank++;
						isSecond = false;
					} else
						checkBlank++;
				}
			}
			if (isFirst == true && isSecond == true) {
				writeByte(idx, buffer);
			} else if (isFirst == false || isSecond == false) {
				cutByte(cutPoint1, cutPoint2, idx, buffer);
			}
			if (byte_read == -1)
				return true;
			idx = 0;
			numOfBlank = 0;
			checkBlank = 5;
			byte_read = '\0';
		}
	}

	private void writeByte(int idx, byte[] buffer) throws IOException {
		for (int i = 0; i < idx; i++)
			out.write(buffer[i]);
		out.write('\n');		
	}
	private void cutByte(int cutPoint1, int cutPoint2, int idx, byte[] buffer) throws IOException {
		for (int i = 0; i < cutPoint1; i++)
			out.write(buffer[i]);
		for (int j = cutPoint1 + 6; j < cutPoint2; j++)
			out.write(buffer[j]);
		for (int k = cutPoint2 + 6; k < idx; k++)
			out.write(buffer[k]);
		out.write('\n');		
	}
}