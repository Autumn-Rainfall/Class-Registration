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
		boolean is23456 = false;
		int byte_read = 0;

		byte[] course = { ' ', '2', '3', '4', '5', '6', ' ' };

		while (true) {
			while (byte_read != '\n' && byte_read != -1) {
				byte_read = in.read();
				if (byte_read == ' ')
					numOfBlank++;
				if (byte_read != -1)
					buffer[idx++] = (byte) byte_read;
				while (numOfBlank == checkBlank) {
					if (buffer[idx - 6] == '2' && buffer[idx - 5] == '3' && buffer[idx - 4] == '4'
							&& buffer[idx - 3] == '5' && buffer[idx - 2] == '6') {
						checkBlank++;
						is23456 = true;
					} else
						checkBlank++;
				}
			}
				if (!is23456) {
					for (int i = 0; i < idx - 2; i++)
						out.write(buffer[i]);
					out.write(course, 0, 7);
					out.write('\n');
					is23456 = false;
				} else if (is23456) {
					for (int i = 0; i < idx - 1; i++)
						out.write(buffer[i]);
					out.write('\n');
					is23456 = false;
				}
			if (byte_read == -1)
				return true;
			idx = 0;
			numOfBlank = 0;
			checkBlank = 5;
			byte_read = '\0';
		}
	}
}
