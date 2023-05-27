# Project
Kinara Capital Software developer Screening (Student API)
package lib.copy;

public class StudentAPI {
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.List;

	import com.google.gson.Gson;
	import com.google.gson.JsonObject;
	import com.google.gson.JsonParser;
	import spark.Request;
	import spark.Response;
	import spark.Route;
	import spark.Spark;

	
	    private static final String FILE_PATH = "student_data.csv";
	    private static final int DEFAULT_PAGE_SIZE = 10;

	    public static void main(String[] args) {
	        Spark.port(8080);
	        
	        // Load Student Details API
	        Spark.get("/api/students", (req, res) -> getStudents(req, res));

	        // Server-side Filtering API
	        Spark.post("/api/students/filter", (req, res) -> filterStudents(req, res));
	    }

	    private static String getStudents(Request req, Response res) {
	        // Read CSV file and extract student details
	        List<Student> studentDetails = readStudentDetails();

	        // Pagination parameters
	        int pageNumber = Integer.parseInt(req.queryParamOrDefault("page_number", "1"));
	        int pageSize = Integer.parseInt(req.queryParamOrDefault("page_size", String.valueOf(DEFAULT_PAGE_SIZE)));

	        // Calculate start and end indices for pagination
	        int startIndex = (pageNumber - 1) * pageSize;
	        int endIndex = Math.min(startIndex + pageSize, studentDetails.size());

	        // Paginate student details
	        List<Student> paginatedStudents = studentDetails.subList(startIndex, endIndex);

	        // Prepare API response
	        JsonObject response = new JsonObject();
	        response.addProperty("students", new Gson().toJson(paginatedStudents));
	        response.addProperty("total_students", studentDetails.size());
	        response.addProperty("page_number", pageNumber);
	        response.addProperty("page_size", pageSize);

	        return response.toString();
	    }

	    private static String filterStudents(Request req, Response res) {
	        // Read CSV file and extract student details
	        List<Student> studentDetails = readStudentDetails();

	        // Get filter criteria from request body
	        JsonObject filterCriteria = new JsonParser().parse(req.body()).getAsJsonObject();

	        // Apply filters to student details
	        List<Student> filteredStudents = new ArrayList<>();
	        for (Student student : studentDetails) {
	            // Apply filter conditions based on filter criteria
	            if (filterCriteria.has("name") && !filterCriteria.get("name").getAsString().equals(student.getName())) {
	                continue;
	            }
	            if (filterCriteria.has("id") && !filterCriteria.get("id").getAsString().equals(student.getId())) {
	                continue;
	            }
	            if (filterCriteria.has("total_marks") && !filterCriteria.get("total_marks").getAsString().equals(student.getTotalMarks())) {
	                continue;
	            }

	            filteredStudents.add(student);
	        }

	        // Prepare API response
	        JsonObject response = new JsonObject();
	        response.addProperty("filtered_students", new Gson().toJson(filteredStudents));

	        return response.toString();
	    }

	    private static List<Student> readStudentDetails() {
	        List<Student> studentDetails = new ArrayList<>();

	        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(",");
	                String id = data[0];
	                String name = data[1];
	                String totalMarks = data[2];

	                Student student = new Student(id, name, totalMarks);
	                studentDetails.add(student);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return studentDetails;
	    }
}

	   


