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
		boolean isFirst = false;
		boolean isSecond = false;
		int byte_read = 0;

		byte[] firstCourse = { ' ', '1', '2', '3', '4', '5', ' ' };
		byte[] secondCourse = { ' ', '2', '3', '4', '5', '6', ' ' };
		byte[] blank = { ' ' };

		while (true) {
			while (byte_read != '\n' && byte_read != -1) {
				byte_read = in.read();
				if (byte_read == ' ')
					numOfBlank++;
				if (byte_read != -1)
					buffer[idx++] = (byte) byte_read;
				while (numOfBlank == checkBlank) {
					if (buffer[idx - 6] == '1' && buffer[idx - 5] == '2' && buffer[idx - 4] == '3'
							&& buffer[idx - 3] == '4' && buffer[idx - 2] == '5') {
						checkBlank++;
						isFirst = true;
					} else if (buffer[idx - 6] == '2' && buffer[idx - 5] == '3' && buffer[idx - 4] == '4'
							&& buffer[idx - 3] == '5' && buffer[idx - 2] == '6') {
						checkBlank++;
						isSecond = true;
					} else
						checkBlank++;
				}
			}
			if (byte_read != -1) {
				if (isFirst == true && isSecond == true) {
					for (int i = 0; i < idx - 2; i++)
						out.write(buffer[i]);
					out.write(blank, 0, 1);
					out.write('\n');
					isFirst = false;
					isSecond = false;
				} else if (isFirst == true && isSecond == false) {
					for (int i = 0; i < idx - 2; i++)
						out.write(buffer[i]);
					out.write(secondCourse, 0, 7);
					out.write('\n');
					isFirst = false;
				} else if (isFirst == false && isSecond == true) {
					for (int i = 0; i < idx - 2; i++)
						out.write(buffer[i]);
					out.write(firstCourse, 0, 7);
					out.write('\n');
					isSecond = false;
				} else if (isFirst == false && isSecond == false) {
					for (int i = 0; i < idx - 2; i++)
						out.write(buffer[i]);
					out.write(firstCourse, 0, 7);
					out.write(secondCourse, 0, 7);
					out.write('\n');
				}
			} else if (byte_read == -1)
				return true;
			idx = 0;
			numOfBlank = 0;
			checkBlank = 5;
			byte_read = '\0';
		}
	}
}
