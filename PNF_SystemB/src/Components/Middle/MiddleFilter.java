package Components.Middle;

import java.io.IOException;
import java.util.ArrayList;

import Components.Utililty.Course;
import Components.Utililty.Student;
import Framework.CommonFilterImpl;

public class MiddleFilter extends CommonFilterImpl {
	@Override
	public boolean specificComputationForFilter() throws IOException {
		int checkBlank = 5;
		int numOfBlank = 0;
		int idx0 = 0;
		int idx1 = 0;
		byte[] buffer = new byte[64];
		boolean satisfy = false;
		boolean dissatisfaction = false;
		
		int byte_read = 0;
		
//		ArrayList<Student> student;
//		ArrayList<Course> course;

		byte[] blank = { ' ' };

		while (true) {
			while (byte_read != '\n' && byte_read != -1) {
				in.get(byte_read);
				if (byte_read == ' ')
					numOfBlank++;
				if (byte_read != -1)
					buffer[idx0++] = (byte) byte_read;
				while (numOfBlank == checkBlank) {
					if (buffer[idx0 - 6] == buffer[idx1] && buffer[idx0 - 5] == buffer[idx1 + 1] && buffer[idx0 - 4] == buffer[idx1 + 2]
							&& buffer[idx0 - 3] == buffer[idx1 + 3] && buffer[idx0 - 2] == buffer[idx1 + 4]) {
						if (checkCourse())
							satisfy = true;
						else dissatisfaction = true;
						checkBlank++;
					} else
						checkBlank++;
				}
			}
			if (byte_read != -1) {
				if (satisfy == true) {
					for (int i = 0; i < idx0 - 2; i++)
						out.get(buffer[i]);
//					out.write(blank, 0, 1);
//					out.write('\n');
					satisfy = false;
				}
			} else if (byte_read == -1)
				return true;
			idx0 = 0;
			idx1 = 0;
			numOfBlank = 0;
			checkBlank = 5;
			byte_read = '\0';
		}
	}

	private boolean checkCourse() {
		// TODO Auto-generated method stub
		return false;
	}

}
