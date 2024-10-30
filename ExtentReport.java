package Login;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport implements ITestListener {
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public void onStart(ITestContext context) {
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("./src/test/resources/Reports/Spark.html");
		extent.attachReporter(spark);
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("Automation report");
		spark.config().setTimeStampFormat("MM/dd/yyyy hh:mm:ss a");
		extent.setSystemInfo("Environment", "Live");
		extent.setSystemInfo("Browser", "Chrome Browser");
	}

	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
		extentTest.assignAuthor("sathyamoorthy");
	}

	public void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, result.getMethod().getMethodName() + " Test case is passed and it is verified.");

	}

	public void onTestFailure(ITestResult result) {
		test.get().log(Status.FAIL, result.getMethod().getMethodName() + " Test case is failed.");
		test.get().log(Status.FAIL, result.getThrowable());
	}

	public void onTestSkipped(ITestResult result) {

		test.get().log(Status.SKIP, result.getMethod().getMethodName() + "Test case is Skipped and it is verified");
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}

}